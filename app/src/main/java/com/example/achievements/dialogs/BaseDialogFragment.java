package com.example.achievements.dialogs;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {

    private DialogInterface.OnDismissListener dismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener)
    {
        this.dismissListener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
}
