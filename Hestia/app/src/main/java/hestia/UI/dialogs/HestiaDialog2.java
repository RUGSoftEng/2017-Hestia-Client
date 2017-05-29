package hestia.UI.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
abstract class HestiaDialog2 extends DialogFragment {

//    public static HestiaDialog2 newInstance(int title) {
//        HestiaDialog2 frag = new HestiaDialog2();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
//        return frag;
//    }



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

    abstract void pressConfirm();
    abstract void pressCancel();


}
