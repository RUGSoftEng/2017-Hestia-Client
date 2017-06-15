package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;
import com.rugged.application.hestia.R;
import hestia.UI.activities.home.HomeActivity;

public abstract class HestiaDialog extends DialogFragment {
    private HomeActivity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(buildTitle());
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                pressConfirm();
                dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                pressCancel();
                dismiss();
            }
        });
        builder.setView(buildView());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void setActivity(HomeActivity activity){
        this.activity = activity;
    }

    //Access the home activity to refresh the GUI, for this functionality the activity is
    //passed around between the dialogs
    protected void refreshUserInterface() {
        activity.refreshUserInterface();
    }

    public HomeActivity getHomeActivity(){
        return activity;
    }

    abstract String buildTitle();

    abstract View buildView();

    abstract void pressConfirm();

    abstract void pressCancel();
}
