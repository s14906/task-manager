package com.example.prm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.prm.databinding.ActivityMainBinding;
import com.example.prm.sql.DBHandler;
import com.example.prm.sql.TaskEntity;
import com.example.prm.utils.DateUtils;
import com.example.prm.utils.TaskDataUtils;
import com.example.prm.utils.ui.AlertDialogHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final DBHandler dbHandler = new DBHandler(this);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createTaskListView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createTaskListView() {
        ListView taskListView = populateTaskListView();
        setupTaskOnClickListener(taskListView);
        setupTaskOnLongClickListener(taskListView);
        setupAddTaskButtonListener();

        //for testing purposes
        setupAddTaskButtonLongClickListener();
    }

    private void setupAddTaskButtonLongClickListener() {
        FloatingActionButton addTaskButton = binding.addTaskButton;
        addTaskButton.setOnLongClickListener(view -> {
            dbHandler.eraseDatabase();
            dbHandler.addTask(new TaskEntity("Wypicie browara", "MEDIUM", "31/12/2022", "95"));
            dbHandler.addTask(new TaskEntity("Stluczenie telefonu", "LOW", "27/04/2022", "0"));
            dbHandler.addTask(new TaskEntity("Przeproszenie mamy", "HIGH", "01/07/2022", "10"));
            dbHandler.addTask(new TaskEntity("Stanie sie okropnym dyktatorem", "LOW", "01/06/2022", "45"));
            dbHandler.addTask(new TaskEntity("Zepsucie samochodu sasiadowi", "MEDIUM", "10/07/2022", "60"));
            dbHandler.addTask(new TaskEntity("Zrobienie, zeby jakis strasznie przydlugawy tekst jak na taka apke nie rozjezdzal sie dziwnie i nie wlazil na inne elementy",
                    "HIGH", "29/04/2022", "100"));
            dbHandler.addTask(new TaskEntity("Zbudowanie farmy Dogecoinow", "HIGH", "21/03/2025", "20"));
            dbHandler.addTask(new TaskEntity("Pogranie w Tibie", "LOW", "30/11/2023", "76"));

            AlertDialogHandler.refreshActivity(MainActivity.this);
            return true;
        });
    }

    private void setupAddTaskButtonListener() {
        FloatingActionButton addTaskButton = binding.addTaskButton;
        addTaskButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
            startActivity(intent);
        });
    }

    private void setupTaskOnLongClickListener(ListView taskListView) {
        taskListView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            TaskDataUtils.selectedTask = adapterView.getItemAtPosition(position);
            AlertDialogHandler.displayDeleteTaskConfirmationDialog(binding, MainActivity.this);
            return true;
        });
    }

    private void setupTaskOnClickListener(ListView taskListView) {
        taskListView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(view.getContext(), TaskActivity.class);
            TaskDataUtils.selectedTask = adapterView.getItemAtPosition(position);
            TaskDataUtils.passSelectedTaskDataToIntent(intent);
            startActivity(intent);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    private ListView populateTaskListView() {
        ListView taskListView = binding.deadlineList;
        SimpleAdapter adapter = new SimpleAdapter(this, getAllTasks(),
                R.layout.activity_element_text,
                new String[] { TaskDataUtils.TASK_NAME, TaskDataUtils.TASK_PRIORITY, TaskDataUtils.TASK_DEADLINE, TaskDataUtils.TASK_PROGRESS },
                new int[] {R.id.taskName, R.id.taskPriority, R.id.taskDate, R.id.taskProgress});
        taskListView.setAdapter(adapter);
        return taskListView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    private List<Map<String, String>> getAllTasks() {
        return dbHandler.getAllTasks().stream()
                .filter(DateUtils::filterPastDates)
                .sorted(DateUtils::compareDatesFromMaps)
                .collect(Collectors.toList());
    }
}