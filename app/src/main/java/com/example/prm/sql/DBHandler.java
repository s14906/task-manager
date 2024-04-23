package com.example.prm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prm.utils.TaskDataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "taskManager";
    private static final String TABLE_TASKS = "task";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = TaskDataUtils.TASK_NAME;
    private static final String KEY_TASK_PRIORITY = TaskDataUtils.TASK_PRIORITY;
    private static final String KEY_TASK_DATE = TaskDataUtils.TASK_DEADLINE;
    private static final String KEY_TASK_PROGRESS = TaskDataUtils.TASK_PROGRESS;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK_NAME + " TEXT,"
                + KEY_TASK_PRIORITY + " TEXT,"
                + KEY_TASK_DATE + " TEXT,"
                + KEY_TASK_PROGRESS + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(sqLiteDatabase);
    }
    public List<Map<String, String>> getAllTasks() {
        List<Map<String, String>> taskEntityList = new ArrayList<>();
        String select = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                Map<String, String> taskData = new HashMap<>();
                taskData.put("id", cursor.getString(0));
                taskData.put(TaskDataUtils.TASK_NAME, cursor.getString(1));
                taskData.put(TaskDataUtils.TASK_PRIORITY, cursor.getString(2));
                taskData.put(TaskDataUtils.TASK_DEADLINE,cursor.getString(3));
                taskData.put(TaskDataUtils.TASK_PROGRESS, cursor.getString(4) + "%");
                taskEntityList.add(taskData);
            } while (cursor.moveToNext());
        }
        return taskEntityList;
    }

    public void addTask(TaskEntity taskEntity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, taskEntity.getTaskName());
        values.put(KEY_TASK_PRIORITY, taskEntity.taskPriority);
        values.put(KEY_TASK_DATE, taskEntity.getTaskDate());
        values.put(KEY_TASK_PROGRESS, taskEntity.getTaskProgress());
        sqLiteDatabase.insert(TABLE_TASKS, null, values);
        sqLiteDatabase.close();
    }

    public void eraseDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        onUpgrade(sqLiteDatabase,1 ,1);
    }

    public void deleteTask(Object id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    public void saveTask(Object id, String taskName, String taskPriority, String taskDeadline, String taskProgress) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, taskName);
        values.put(KEY_TASK_PRIORITY, taskPriority);
        values.put(KEY_TASK_DATE, taskDeadline);
        values.put(KEY_TASK_PROGRESS, taskProgress);
        sqLiteDatabase.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
