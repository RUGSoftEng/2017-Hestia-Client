package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import hestia.UI.activities.home.DeviceListFragment;
import hestia.UI.activities.home.HomeActivity;

abstract class HestiaDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())

                // Set Dialog Title
                .setTitle("Alert DialogFragment")

                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        pressConfirm();
                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        pressCancel();
                    }

                }).create();
    }

    protected void refreshUserInterface(){
        ((HomeActivity)this.getActivity()).refreshUserInterface();
    }

    abstract void pressConfirm();
    abstract void pressCancel();

}
