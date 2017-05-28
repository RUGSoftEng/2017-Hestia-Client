package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.rugged.application.hestia.R;

import java.io.IOException;

import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.Device;

public class ChangeNameDialog extends HestiaDialog2 {
    private EditText editText;
    private Device device;

//    public ChangeNameDialog(Context context, Device device) {
//        super(context, R.layout.set_name, "Change name of your device");
//        this.device = device;
//    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        editText = (EditText)findViewById(R.id.change_name_device);
//        editText.setText(device.getName());
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//    }

    public static ChangeNameDialog newInstance() {
        ChangeNameDialog fragment = new ChangeNameDialog();
        return fragment;
    }

    public void setDevice(Device d) {
        device = d;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set Dialog Title
        builder.setTitle("Change name")

                // Positive button
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        pressConfirm();
                        dismiss();

                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        pressCancel();

                    }

                });
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.set_name, null);


        editText = (EditText) view.findViewById(R.id.change_name_device);
        editText.setText(device.getName());
        builder.setView(view);

        AlertDialog dlg = builder.create();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dlg;
    }

    @Override
    void pressCancel() {
        Toast.makeText(getContext(), "pressed cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    void pressConfirm() {
        final String result = editText.getText().toString();
        new AsyncTask<Object, Object, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                //TODO: handle try-catch properly
                try {
                    device.setName(result);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ComFaultException e) {
                    Log.d("comFault",e.getError()+":" +e.getMessage());
                    e.printStackTrace();
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                //UPDATE THE GUI
            }
        }.execute();
    }

}
