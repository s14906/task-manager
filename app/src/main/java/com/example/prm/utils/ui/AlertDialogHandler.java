package com.example.prm.utils.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.prm.R;
import com.example.prm.sql.DBHandler;
import com.example.prm.utils.TaskDataUtils;

import java.util.HashMap;

public class AlertDialogHandler {
    public static void displayDeleteTaskConfirmationDialog(ViewBinding binding, AppCompatActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
        builder.setCancelable(true);
        builder.setTitle(R.string.delete_popup_title);
        builder.setMessage(R.string.delete_popup_message);
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHandler dbHandler = new DBHandler(binding.getRoot().getContext());
                        dbHandler.deleteTask(((HashMap) TaskDataUtils.selectedTask).get("id"));
                        refreshActivity(activity);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            //do nothing, close the dialog
        });
        displayAlert(builder);
    }

    private static void displayAlert(AlertDialog.Builder builder) {
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void displayWarningDialog(ViewBinding binding, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {});
        displayAlert(builder);
    }

    public static void refreshActivity(AppCompatActivity activity) {
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(activity.getIntent());
        activity.overridePendingTransition(0, 0);
    }
}
