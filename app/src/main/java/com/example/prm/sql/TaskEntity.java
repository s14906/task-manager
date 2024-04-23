package com.example.prm.sql;

import java.util.Date;

public class TaskEntity {
    int id;
    String taskName;
    String taskPriority;
    String taskDate;
    String taskProgress;

    public TaskEntity(int id, String taskName, String taskPriority, String taskDate, String taskProgress) {
        this.id = id;
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskDate = taskDate;
        this.taskProgress = taskProgress;
    }

    public TaskEntity(String taskName, String taskPriority, String taskDate, String taskProgress) {
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.taskDate = taskDate;
        this.taskProgress = taskProgress;
    }

    public TaskEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(String taskProgress) {
        this.taskProgress = taskProgress;
    }
}
