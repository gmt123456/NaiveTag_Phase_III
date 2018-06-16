package top.minecode.dao.requester.task;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.ImageLists;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.task.requester.RequesterSubTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.po.auto.TaskVectorPO;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.WorkerPO;
import top.minecode.service.util.HttpHelper;
import top.minecode.service.util.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/24.
 * Description:
 *
 * @author Liao
 */

@Repository("requesterTaskDaoImpl")
public class RequesterTaskDaoImpl implements RequesterTaskDao {

    private static Logger log = LoggerFactory.getLogger(RequesterTaskDaoImpl.class);

    private CommonOperation<WorkerPO> workerOperation = new CommonOperation<>(WorkerPO.class);
    private CommonOperation<TaskPO> taskOperation = new CommonOperation<>(TaskPO.class);
    private CommonOperation<SpecificTaskPO> specificTaskOperation =
            new CommonOperation<>(SpecificTaskPO.class);

    @Override
    public String getReadme(int taskId) {
        TaskPO taskPO = taskOperation.getBySingleField("id", taskId);
        return taskPO.getReadme();
    }

    @Override
    public String getResultFilePath(int taskId) {
        TaskPO taskPO = taskOperation.getBySingleField("id", taskId);
        return taskPO.getResultFilePath();
    }

    @Override
    public boolean updateReadme(int taskId, String readme) {
        TaskPO taskPO = taskOperation.getBySingleField("id", taskId);
        taskPO.setReadme(readme);

        return taskOperation.update(taskPO);
    }

    @Override
    public RequesterTaskDetails getTaskDetails(int taskId) {
        TaskPO task = taskOperation.getBySingleField("id", taskId);
        String hql = "select t.subTaskState from SubTaskPO t where t.taskId=?";

        //noinspection unchecked
        List<SubTaskState> states = CommonOperation.template(s ->
                (List<SubTaskState>) s.createQuery(hql).setParameter(0, taskId).list());

        return new RequesterTaskDetails(task, getTaskProcess(states));
    }

    @Override
    public List<RequesterSubTaskItem> getSubTaskItem(int taskId) {
        TaskPO task = taskOperation.getBySingleField("id", taskId);
        Map<TaskType, Integer> specificTasksMap = task.getSpecificTasks();

        // Get specific tasks
        List<SpecificTaskPO> specificTasks = specificTaskOperation
                .getValuesInSpecificSet(new ArrayList<>(specificTasksMap.values()), "id");

        String hql = "select t.subTaskState from SubTaskPO t where t.id in :se";
        String participantsHql = "select new map (p.email as email , sum(p.picAmount) as picNum)" +
                " from SubTaskParticipationPO p where p.subTaskId in :se" +
                " group by p.email";

        return specificTasks.stream().map(e -> {
            List<Integer> subTasks = e.getSubTasks();

            // Get process
            //noinspection unchecked
            List<SubTaskState> states = CommonOperation.template(s ->
                    (List<SubTaskState>) s.createQuery(hql).setParameterList("se", subTasks).list());
            log.info(states.toString());
            double process = getTaskProcess(states);

            // Get sub-task participants
            //noinspection unchecked
            List<Map> participantsPics = CommonOperation.template(session ->
                    (List<Map>) session.createQuery(participantsHql)
                            .setParameterList("se", subTasks)
                            .list());

            int participantsNum = (int) getParticipantsNum(subTasks);
            return new RequesterSubTaskItem(e.getTaskType(), process,
                    participantsNum, getParticipants(participantsPics), e.getTaskDescription());
        }).collect(Collectors.toList());
    }

    @Override
    public List<TaskParticipant> getTaskParticipants(int taskId) {
        // Get all participants and their completed pictures number
        String hql = "select new map (p.email as email , sum(p.picAmount) as picNum)" +
                " from SubTaskParticipationPO p where p.taskId=?" +
                " group by p.email";
        List<Map> participantsPics = CommonOperation.executeSQL(Map.class, hql, taskId);

        return getParticipants(participantsPics);
    }

    @Override
    public List<RequesterTaskItem> getTaskItems(String email, TaskState state) {
        String hql = "from TaskPO t where t.ownerEmail= :e and t.taskState= :s";
        //noinspection unchecked
        List<TaskPO> taskPOS = CommonOperation.template(s ->
                (List<TaskPO>) s.createQuery(hql)
                        .setParameter("e", email)
                        .setParameter("s", state).list());
        return taskPOS.stream().map(RequesterTaskItem::new).collect(Collectors.toList());
    }

    @Override
    public boolean changeRequirement(int taskId, TaskRequirement requirement) {
        TaskPO target = taskOperation.getBySingleField("id", taskId);
        target.setRequirement(requirement);
        return taskOperation.update(target);
    }

    @Override
    public boolean addTask(TaskPO taskPO, List<SpecificTaskPO> specificTaskPOS, String pictureDir) {

        // Assign specific tasks
        if (!CommonOperation.template(forEachAdd(specificTaskPOS)))
            return false;

        Map<TaskType, Integer> specificTasks = specificTaskPOS.stream()
                .collect(Collectors.toMap(SpecificTaskPO::getTaskType, SpecificTaskPO::getId));
        taskPO.setSpecificTasks(specificTasks);

        // Now, taskPO's information is completed, add it to database
        taskOperation.add(taskPO);
        int taskId = taskPO.getId();

        // Assign sub-tasks for each specificTasks
        ImageLists imageLists = new ImageLists(pictureDir, PathUtil.getRequesterDataFileRoot());
        for (SpecificTaskPO specificTask : specificTaskPOS) {
            List<SubTaskPO> subTaskPOS = new ArrayList<>();

            // Create SubTaskPOS
            for (ImageLists.ImageList images : imageLists) {
                SubTaskPO subTaskPO = new SubTaskPO(specificTask.getTaskType(),
                        specificTask.getTaskDescription(), images.getImages(),
                        specificTask.getId(), taskId);
                subTaskPOS.add(subTaskPO);
            }

            if (!CommonOperation.template(forEachAdd(subTaskPOS)))
                return false;

            // Set specific task po's subTasks
            specificTask.setSubTasks(subTaskPOS.stream().map(SubTaskPO::getId).collect(Collectors.toList()));
        }

        insertVectors(taskPO);

        if (!CommonOperation.template(forEachUpdate(specificTaskPOS)))
            return false;

        // If it's global mark task, write images' path to
        // a file so that a python process can access them
        if (specificTasks.keySet().contains(TaskType.t_100)) {
            String imagePathFile = writeImagesPath(imageLists, taskId);
            String labelFile = writeLabels(specificTaskPOS, taskId);
            Map<String, String> params = new HashMap<>();
            params.put("task_id", taskId + "");
            params.put("pic_path_file", imagePathFile);
            params.put("target_labels_file_path", labelFile);

            HttpHelper.send(PathUtil.getPythonServerPath(), HttpHelper.urlEncode(params));
        }

        return true;
    }

    private String writeImagesPath(ImageLists imageLists, int taskId) {
        List<String> images = new ArrayList<>();
        for (ImageLists.ImageList imageList : imageLists)
            imageList.getImages().stream()
                    .map(PathUtil::convertToAbsolutePath)
                    .forEach(images::add);

        String fileName = taskId + "_images.txt";
        return writeFile(images, fileName);
    }

    private String writeLabels(List<SpecificTaskPO> specificTaskPOS, int taskId) {
        List<String> labels = specificTaskPOS.stream()
                .flatMap(p -> p.getLabels().stream())
                .collect(Collectors.toList());

        String fileName = taskId + "_label.txt";
        return writeFile(labels, fileName);
    }

    private String writeFile(List<String> content, String fileName) {
        File dir = new File(PathUtil.getBasePath() + PathUtil.getTaskSpecificationPath());
        if (!dir.exists() && !dir.mkdirs())
            log.error("Failed to make specification directory when write image's path");
        File target = new File(dir, fileName);

        try (PrintWriter writer = new PrintWriter(target)) {
            content.forEach(writer::println);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return target.getPath();
    }

    /**
     * Insert task vector for intelligent module
     *
     * @param target target taskPO
     */
    private void insertVectors(TaskPO target) {
        TaskVectorPO vectorPO = TaskVectorPO.fromTaskPO(target);
        CommonOperation.template(session -> {
            session.persist(vectorPO);
            session.flush();
        });
    }

    private double getTaskProcess(List<SubTaskState> states) {
        if (states.size() == 0) {
            log.error("No states present");
            return 0.;
        }
        long finishedNum = states.stream().filter(e -> e == SubTaskState.FINISHED).count();

        return finishedNum * 1. / states.size();
    }

    private long getParticipantsNum(Collection collection) {
        String participantsHql = "select count(distinct t.email) from SubTaskParticipationPO t where t.subTaskId in :se";

        return CommonOperation.template(s -> (long) s.createQuery(participantsHql)
                .setParameterList("se", collection)
                .iterate().next());
    }

    // Get sub-task's participants
    private List<TaskParticipant> getParticipants(List<Map> participantsPics) {
        // Transfer it to a hashMap
        Map<String, Long> participantsPicsMap = participantsPics.stream()
                .collect(Collectors.toMap(e -> (String) e.get("email"), e -> (Long) e.get("picNum")));

        // Get WorkerPO by email in participantsPics
        List<WorkerPO> workers = workerOperation
                .getValuesInSpecificSet(new ArrayList<>(participantsPicsMap.keySet()), "email");

        // Combine them into TaskParticipant
        return workers.stream()
                .map(e -> new TaskParticipant(e.getName(), e.getAvatar(),
                        e.getScore(), participantsPicsMap.get(e.getEmail()).intValue()))
                .collect(Collectors.toList());
    }

    private <T> Consumer<Session> forEachAdd(List<T> list) {
        return session -> {
            list.forEach(session::persist);
            session.flush();
        };
    }

    private <T> Consumer<Session> forEachUpdate(List<T> list) {
        return session -> {
            list.forEach(session::update);
            session.flush();
        };
    }
}
