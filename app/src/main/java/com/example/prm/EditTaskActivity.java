package com.example.prm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.prm.databinding.ActivityEditTaskBinding;
import com.example.prm.sql.DBHandler;
import com.example.prm.sql.TaskEntity;
import com.example.prm.utils.DateUtils;
import com.example.prm.utils.StringUtils;
import com.example.prm.utils.TaskDataUtils;
import com.example.prm.utils.ui.AlertDialogHandler;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {
    private final DBHandler dbHandler = new DBHandler(this);
    private Bundle bundle;
    private ActivityEditTaskBinding binding;
    private final Calendar cal = Calendar.getInstance();
    private EditText taskDeadlineTextField;
    private EditText taskNameTextField;
    private String taskPriority;
    private SeekBar taskProgressSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            binding.taskNameTextField.setText(bundle.getString(TaskDataUtils.TASK_NAME));
            binding.priorityDropdown.post(() -> binding.priorityDropdown.setSelection(getSelectedPriorityPosition()));
            binding.taskDeadlineTextField.setText(bundle.getString(TaskDataUtils.TASK_DEADLINE));
            binding.taskProgressSeekBar.setProgress(Integer.parseInt(
                    StringUtils.removePercentageSign(
                            bundle.getString(TaskDataUtils.TASK_PROGRESS))));
        }

        setupBackButton();
        setupPriorityDropdown();
        setupDeadlineDatePicker();
        taskProgressSeekBar = binding.taskProgressSeekBar;
        taskProgressSeekBar.setMax(100);

        Button saveTaskButton = binding.saveTaskButton;
        saveTaskButton.setOnClickListener(view -> {
            String taskName = binding.taskNameTextField.getText().toString();
            if (validateTaskName(taskName)) {
                return;
            }
            String taskDeadline = taskDeadlineTextField.getText().toString();
            if (validateTaskDeadline(taskDeadline)) {
                return;
            }
            String taskProgress = String.valueOf(taskProgressSeekBar.getProgress());
            if (bundle != null) {
                dbHandler.saveTask(bundle.get("id"), taskName, taskPriority, taskDeadline, taskProgress);
            } else {
                dbHandler.addTask(new TaskEntity(taskName, taskPriority, taskDeadline, taskProgress));
            }
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateTaskDeadline(String taskDeadline) {
        if (!DateUtils.validateDate(taskDeadline)) {
            AlertDialogHandler.displayWarningDialog(binding, getString(R.string.invalid_date_warning));
            return true;
        }
        return false;
    }

    private boolean validateTaskName(String taskName) {
        if (StringUtils.isNullOrEmpty(taskName)) {
            AlertDialogHandler.displayWarningDialog(binding, getString(R.string.task_name_empty_warning));
            return true;
        }
        if (taskName.length() > 50) {
            AlertDialogHandler.displayWarningDialog(binding, getString(R.string.task_name_too_long_warning));
            return true;
        }
        return false;
    }

    private int getSelectedPriorityPosition() {
        String selectedPriority = bundle.getString(TaskDataUtils.TASK_PRIORITY);
        switch (selectedPriority) {
            case "LOW":
                return 0;
            case "MEDIUM":
                return 1;
            case "HIGH":
                return 2;
        }
        return 0;
    }

    private void setupDeadlineDatePicker() {
        taskDeadlineTextField = binding.taskDeadlineTextField;
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            DateUtils.prepareCalendar(cal, year, month, day);
            taskDeadlineTextField.setText(DateUtils.formatDate(cal.getTime()));
        };
        taskDeadlineTextField.setOnClickListener(view ->
                new DatePickerDialog(EditTaskActivity.this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
                .show());
    }

    private void setupBackButton() {
        binding.backButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    private void setupPriorityDropdown() {
        Spinner priorityDropdown = binding.priorityDropdown;
        String[] items = TaskDataUtils.Priority.getPriorityList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        priorityDropdown.setAdapter(adapter);
        priorityDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                taskPriority = String.valueOf(adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}