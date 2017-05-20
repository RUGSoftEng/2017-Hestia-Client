package hestia.UI;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import hestia.backend.NetworkHandler;
import hestia.backend.Cache;

/**
 * This class represents the dialog screen with which the IP-address of the server is asked from the
 * user.
 */

public class IpDialog extends Dialog implements android.view.View.OnClickListener{
    private EditText ipField;
    private Button confirm,cancel;
    private String ip;
    private NetworkHandler networkHandler;
    private Cache cache;

    public IpDialog(Activity a) {
        super(a);
        this.networkHandler = NetworkHandler.getInstance();
        this.cache = Cache.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ip_dialog);
        ipField = (EditText) findViewById(R.id.ip);
        ipField.setText(this.cache.getIp());
        ipField.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ip = ipField.getText().toString();
        Toast.makeText(getContext(), "ip is: " + ip, Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case R.id.confirm_button:
                if(ip!=null) {
                    cache.setIp(ip);
                    networkHandler.updateDevices();
                    Toast.makeText(getContext(),R.string.ipSetTo + cache.getIp() + ":"
                                    + cache.getPort(),Toast.LENGTH_SHORT).show();

                    //TODO give correct response from server after changing ip
                    Toast.makeText(getContext(), "Server returned message: + serverMessage",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.back_button:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
