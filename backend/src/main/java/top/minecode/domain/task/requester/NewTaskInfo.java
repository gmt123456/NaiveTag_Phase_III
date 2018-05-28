package top.minecode.domain.task.requester;

import org.springframework.web.multipart.MultipartFile;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.service.util.RandomUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
public class NewTaskInfo {

    private MultipartFile cover;
    private MultipartFile dataset;
    private String title;
    private List<TaskTag> tags;
    private String description;
    private List<NewSpecificTask> tasks;
    private String details;
    private LocalDate deadline;

    public String transferCoverTo(String path) {
        // If cover is null, return a random cover instead
        if (cover != null) {
            try {
                cover.transferTo(new File(path));
                return path;
            } catch (IOException e) {
                e.printStackTrace();
                // Assign a cover randomly
                return RandomUtil.getRandomTaskAvatar();
            }
        }

        return RandomUtil.getRandomTaskAvatar();
    }

    public void transferDataSet(String path) throws IOException {
        dataset.transferTo(new File(path));
    }

    public void setCover(MultipartFile cover) {
        this.cover = cover;
    }

    public void setDataset(MultipartFile dataset) {
        this.dataset = dataset;
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

    public void setTasks(List<NewSpecificTask> tasks) {
        this.tasks = tasks;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
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

    public List<NewSpecificTask> getTasks() {
        return tasks;
    }

    public String getDetails() {
        return details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    private static class NewSpecificTask {
        private TaskType type;
        private String description;
        private List<String> labels;

        public TaskType getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setType(TaskType type) {
            this.type = type;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }
    }
}
