package com.example.prm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.prm.databinding.ActivityTaskBinding;
import com.example.prm.utils.sharing.ShareMessageBuilder;
import com.example.prm.utils.StringUtils;
import com.example.prm.utils.TaskDataUtils;

public class TaskActivity extends AppCompatActivity {
    private ActivityTaskBinding binding;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupSelectedTaskData();

        setupComponents();
    }

    private void setupComponents() {
        binding.saveTaskButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
            TaskDataUtils.passSelectedTaskDataToIntent(intent);
            startActivity(intent);
        });

        binding.backButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });

        binding.shareButton.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = ShareMessageBuilder.createShareMessage();
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }

    private void setupSelectedTaskData() {
        bundle = getIntent().getExtras();
        binding.taskNameTextViewValue.setText(bundle.getString(TaskDataUtils.TASK_NAME));
        binding.taskPriorityTextViewValue.setText(bundle.getString(TaskDataUtils.TASK_PRIORITY));
        binding.taskDeadlineTextViewValue.setText(bundle.getString(TaskDataUtils.TASK_DEADLINE));
        String taskProgress = bundle.getString(TaskDataUtils.TASK_PROGRESS);
        binding.taskProgressTextViewValue.setText(taskProgress);
        binding.taskProgressTextViewBarValue.setText(taskProgress);

        ProgressBar circularProgressBar = binding.circularProgressBar;
        circularProgressBar.setProgress(Integer.parseInt(
                StringUtils.removePercentageSign(
                        bundle.getString(TaskDataUtils.TASK_PROGRESS))));
    }
}