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

public class IpDialog extends Dialog implements android.view.View.OnClickListener{
    private EditText ipField,portField;
    private Button confirm,cancel;
    private String ip,port;
    private Activity c;
    private ClientInteractionController cic;

    public IpDialog(Activity a, ClientInteractionController c) {
        super(a);
        this.c = a;
        this.cic = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ip_dialog);
        ipField = (EditText) findViewById(R.id.ip);
        portField = (EditText) findViewById(R.id.port);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Toast.makeText(getContext(),"IP Address: " + cic.getIp() + ":" + cic.getPort()
                ,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {

        ip = ipField.getText().toString();
        port = portField.getText().toString();

        switch (v.getId()) {
            case R.id.confirm_button:
                if(ip!=null) {
                    cic.setIp(ip);
                }
                if(port!=null){
                    cic.setPort(Integer.parseInt(port));
                }
                Toast.makeText(getContext(),"IP Address: " + cic.getIp() + ":" + cic.getPort()
                        ,Toast.LENGTH_SHORT).show();
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
