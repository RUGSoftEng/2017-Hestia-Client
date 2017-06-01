package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.rugged.application.hestia.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hestia.backend.ServerCollectionsInteractor;

/**
 * This class represents the dialog screen with which the IP-address of the server is asked from the
 * user.
 */

public class ChangeIpDialog extends HestiaDialog {
    private final static String TAG = "ChangeIpDialog";
    private String ip;
    private EditText ipField;
    private Pattern pattern;
    private Matcher matcher;
    private static final String IPADDRESS_PATTERN =
                        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private ServerCollectionsInteractor serverCollectionsInteractor;

    public static ChangeIpDialog newInstance(String ip) {
        ChangeIpDialog fragment = new ChangeIpDialog();
        Bundle bundle = new Bundle();
        bundle.putString("IP_ADDRESS", ip);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setInteractor(ServerCollectionsInteractor interactor) {
        serverCollectionsInteractor = interactor;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            ip = savedInstanceState.getString("IP_ADDRESS");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set Dialog Title
        builder.setTitle("Change IP")

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
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }

                });
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.ip_dialog, null);

        ipField = (EditText) view.findViewById(R.id.ip);
        ipField.setRawInputType(Configuration.KEYBOARD_12KEY);

        if (ip != null) {
            ipField.setText(ip);
        }
        builder.setView(view);

        AlertDialog dlg = builder.create();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dlg;
    }

    @Override
    void pressConfirm() {
        ip = ipField.getText().toString();
        if(checkIp(ip)) {
            serverCollectionsInteractor.getHandler().setIp(ip);
            Toast.makeText(getContext(), serverCollectionsInteractor.getHandler().getIp(),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),getString(R.string.incorr_ip),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    void pressCancel() {
        Toast.makeText(getContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
    }

    private boolean checkIp(String ip) {
                pattern = Pattern.compile(IPADDRESS_PATTERN);
                matcher = pattern.matcher(ip);
                return matcher.matches();
            }
}
