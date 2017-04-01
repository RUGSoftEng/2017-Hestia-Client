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

public class AddDeviceDialog extends Dialog implements android.view.View.OnClickListener{
    private EditText organizationField,pluginField;
    private Button confirm,cancel;
    private String organization,pluginName;
    public Activity c;
    public AddDeviceDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ip_dialog);
        organizationField = (EditText) findViewById(R.id.ip);
        pluginField = (EditText) findViewById(R.id.port);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        organization = organizationField.getText().toString();
        pluginName = pluginField.getText().toString();

        switch (v.getId()) {
            case R.id.confirm_button:
                Toast.makeText(getContext(),"PluginName " + pluginName,Toast.LENGTH_SHORT).show();
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
