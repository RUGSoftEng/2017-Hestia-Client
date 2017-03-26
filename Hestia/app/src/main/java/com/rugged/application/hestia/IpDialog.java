package com.rugged.application.hestia;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IpDialog extends Dialog implements android.view.View.OnClickListener{
    private EditText ipField,portField;
    private Button confirm,cancel;
    private String ip,port;
    public Activity c;
    public IpDialog(Activity a) {
        super(a);
        this.c = a;
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

    }

    @Override
    public void onClick(View v) {
        ip = ipField.getText().toString();
        port = portField.getText().toString();

        switch (v.getId()) {
            case R.id.confirm_button:
                Toast.makeText(getContext(),"IP Address: " + ip,Toast.LENGTH_SHORT).show();
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
