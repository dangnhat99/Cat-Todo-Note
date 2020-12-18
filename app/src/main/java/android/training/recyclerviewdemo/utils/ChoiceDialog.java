package android.training.recyclerviewdemo.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

public class ChoiceDialog {
    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;
    OnChoiceDialogListener choiceDialogListener;

    public ChoiceDialog(Context context, String dialogMessage, String dialogTitle, String acceptTitle, String cancelTitle, OnChoiceDialogListener choiceDialogListener) {
        dialogBuilder = new AlertDialog.Builder(context);
        this.choiceDialogListener = choiceDialogListener;
        dialogBuilder.setTitle(dialogTitle);
        dialogBuilder.setMessage(dialogMessage);
        dialogBuilder.setPositiveButton(acceptTitle, (dialogInterface, i) -> {
            if (choiceDialogListener != null) {
                choiceDialogListener.onAccept();
            }
        });

        dialogBuilder.setNegativeButton(cancelTitle, (dialogInterface, i) -> {
        });

        dialog = dialogBuilder.create();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface OnChoiceDialogListener {
        void onAccept();
    }
}
