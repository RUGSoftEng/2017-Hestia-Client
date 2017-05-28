package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.rugged.application.hestia.R;

abstract class HestiaDialog extends AlertDialog{
    protected Context context;
    private int layoutReference;
    private Builder builder;

    public HestiaDialog(Context context, int reference, String title) {
        super(context);
        this.context = context;
        this.layoutReference = reference;
        this.builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pressConfirm();
                dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pressCancel();
                dismiss();
            }
        });
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(layoutReference, null);
        builder.setView(view);
//        addSpecificFunctionality(view);
        AlertDialog d = builder.create();
        d.show();
    }

    abstract void pressCancel();

    abstract void pressConfirm();

//    abstract void addSpecificFunctionality(View v);
}
