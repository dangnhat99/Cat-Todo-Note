package android.training.recyclerviewdemo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/*
    created by Dmnhat
       23/10/2020
*/

public class EditTextDialog {
    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;
    OnEditTextDialogListener editTextDialogListener;

    public void setOnEditTextDialogListener(OnEditTextDialogListener editTextDialogListener)  {
        this.editTextDialogListener = editTextDialogListener;
    }

    public EditTextDialog(Context context,String dialogTitle, String dialogMessage, String acceptTitle, String cancelTitle) {
        final EditText edittext = new EditText(context);
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(dialogTitle);
        dialogBuilder.setMessage(dialogMessage);
        dialogBuilder.setView(edittext);
        edittext.requestFocus();
        dialogBuilder.setPositiveButton(acceptTitle, (dialogInterface, i) -> {
            if (editTextDialogListener != null) {
                editTextDialogListener.onAccept(edittext.getText().toString());
            }
        });

        dialogBuilder.setNegativeButton(cancelTitle, (dialogInterface, i) -> {
           if (editTextDialogListener != null ) {
               editTextDialogListener.onCancel();
           }
        });

        dialog = dialogBuilder.create();
    }

    public void show() {
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void dismiss() { dialog.dismiss(); }

    public interface OnEditTextDialogListener {
        void onAccept(String editText);
        void onCancel();
    }
}
