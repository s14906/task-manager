package com.example.prm.utils;

import android.content.Intent;
import android.os.Bundle;

import com.example.prm.sql.TaskEntity;

import java.util.HashMap;

public class TaskDataUtils {
    public enum Priority {
        LOW("LOW"),
        MEDIUM("MEDIUM"),
        HIGH("HIGH");
        public final String priority;

        Priority(String priority) {
            this.priority = priority;
        }

        public static String[] getPriorityList() {
            return new String[]{LOW.priority, MEDIUM.priority, HIGH.priority};
        }
    }

    public static final String TASK_NAME = "taskName";
    public static final String TASK_PRIORITY = "taskPriority";
    public static final String TASK_DEADLINE = "taskDate";
    public static final String TASK_PROGRESS = "taskProgress";
    public static Object selectedTask;

    public static void passSelectedTaskDataToIntent(Intent intent) {
        Bundle bundle = new Bundle();
        TaskEntity taskEntity = getSelectedTaskAsTaskEntity();
        bundle.putString("id", String.valueOf(taskEntity.getId()));
        bundle.putString(TaskDataUtils.TASK_NAME, taskEntity.getTaskName());
        bundle.putString(TaskDataUtils.TASK_PRIORITY, taskEntity.getTaskPriority());
        bundle.putString(TaskDataUtils.TASK_DEADLINE, taskEntity.getTaskDate());
        bundle.putString(TaskDataUtils.TASK_PROGRESS, taskEntity.getTaskProgress());
        intent.putExtras(bundle);
    }

    public static TaskEntity getSelectedTaskAsTaskEntity() {
        if (selectedTask != null) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(Integer.parseInt(String.valueOf(((HashMap) selectedTask).get("id"))));
            taskEntity.setTaskName(String.valueOf(((HashMap) selectedTask).get(TaskDataUtils.TASK_NAME)));
            taskEntity.setTaskPriority(String.valueOf(((HashMap) selectedTask).get(TaskDataUtils.TASK_PRIORITY)));
            taskEntity.setTaskDate(String.valueOf(((HashMap) selectedTask).get(TaskDataUtils.TASK_DEADLINE)));
            taskEntity.setTaskProgress(String.valueOf(((HashMap) selectedTask).get(TaskDataUtils.TASK_PROGRESS)));
            return taskEntity;
        }
        throw new NullPointerException("No selected task detected.");
    }
}
