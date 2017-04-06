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

public class AddBridgeDialog extends Dialog implements android.view.View.OnClickListener{
    private EditText bridgeField;
    private Button confirm,cancel;
    private String bridge;
    private ClientInteractionController cic;
    private Activity c;

    public AddBridgeDialog(Activity a) {
        super(a);
        this.c = a;
        this.cic = ClientInteractionController.getInstance();;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_bridge_ip);
        bridgeField = (EditText) findViewById(R.id.bridge_ip);

        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        bridge = bridgeField.getText().toString();
        switch (v.getId()) {
            case R.id.confirm_button:
//                Toast.makeText(getContext(),"Bridge: " + bridge ,Toast.LENGTH_SHORT).show();
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
