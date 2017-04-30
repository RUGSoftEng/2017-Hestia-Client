package hestia.UI;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import hestia.backend.ClientInteractionController;

/**
 * This class represents the dialog screen with which the IP-address of the server is asked from the
 * user.
 */
public class IpDialog extends Dialog implements android.view.View.OnClickListener{
    private EditText ipField;
    private Button confirm,cancel;
    private String ip;
    private ClientInteractionController cic;

    public IpDialog(Activity a) {
        super(a);
        this.cic = ClientInteractionController.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ip_dialog);
        ipField = (EditText) findViewById(R.id.ip);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ip = ipField.getText().toString();
        switch (v.getId()) {
            case R.id.confirm_button:
                if(ip!=null) {
                    cic.setIp(ip);
                    Toast.makeText(getContext(),"IP Address set to: " + cic.getIp() + ":"
                                    + cic.getPort(),Toast.LENGTH_SHORT).show();
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
