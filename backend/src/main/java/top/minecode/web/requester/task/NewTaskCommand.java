package top.minecode.web.requester.task;

import org.springframework.web.multipart.MultipartFile;
import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.user.worker.Division;

import java.time.LocalDate;
import java.util.List;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
public class NewTaskCommand {

    private String cover;
    private String backgroundImage;
    private String title;
    private List<TaskTag> tags;
    private String description;
    private MultipartFile dataset;
    private List<NewSpecificTask> tasks;
    private String readme;
    private LocalDate deadline;
    private Division lowestDivision;
    private String taskRequirement = TaskRequirement.COMMON.toString();

    public String getTaskRequirement() {
        return taskRequirement;
    }

    public void setTaskRequirement(String taskRequirement) {
        this.taskRequirement = taskRequirement;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTags(List<TaskTag> tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDataset(MultipartFile dataset) {
        this.dataset = dataset;
    }

    public void setTasks(List<NewSpecificTask> tasks) {
        this.tasks = tasks;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setLowestDivision(Division lowestDivision) {
        this.lowestDivision = lowestDivision;
    }

    public String getCover() {
        return cover;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getTitle() {
        return title;
    }

    public List<TaskTag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public MultipartFile getDataset() {
        return dataset;
    }

    public List<NewSpecificTask> getTasks() {
        return tasks;
    }

    public String getReadme() {
        return readme;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Division getLowestDivision() {
        return lowestDivision;
    }

    @Override
    public String toString() {
        return "NewTaskCommand{" +
                "cover='" + cover + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", description='" + description + '\'' +
                ", dataset=" + dataset +
                ", tasks=" + tasks +
                ", readme='" + readme + '\'' +
                ", deadline=" + deadline +
                ", lowestDivision=" + lowestDivision +
                '}';
    }

    public static class NewSpecificTask {
        private int type;
        private String description;
        private List<String> labels;

        public void setType(int type) {
            this.type = type;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getLabels() {
            return labels;
        }

        public int getType() {
            return type;
        }

        public double getPrizeLowerBound(int pictureNum) {
            return type * pictureNum / 100;
        }
    }
}
