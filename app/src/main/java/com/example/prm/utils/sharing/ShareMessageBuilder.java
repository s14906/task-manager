package com.example.prm.utils.sharing;

import com.example.prm.sql.TaskEntity;
import com.example.prm.utils.TaskDataUtils;

public class ShareMessageBuilder {
    public static String createShareMessage() {
        TaskEntity taskEntity = TaskDataUtils.getSelectedTaskAsTaskEntity();
        return "Task name: " + taskEntity.getTaskName() + "\n"
                + "Task priority: " + taskEntity.getTaskPriority() + "\n"
                + "Task deadline: " + taskEntity.getTaskDate() + "\n"
                + "Task progress: " + taskEntity.getTaskProgress();
    }
}
