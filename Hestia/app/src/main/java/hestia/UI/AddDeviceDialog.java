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

/**
* This class opens the dialog to enter the organization name and plugin name.
* It then sends this to the networkHandler which tries to get the required info.
* If this works it consecutively opens a new dialog for the other info.
* @see hestia.UI.AddDeviceInfo
 */

public class AddDeviceDialog extends Dialog implements android.view.View.OnClickListener {
    private EditText organizationField, pluginField;
    private Button confirm, cancel;
    private NetworkHandler networkHandler;
    private Activity context;

    public AddDeviceDialog(Activity activity) {
        super(activity);
        this.context = activity;
        this.networkHandler = NetworkHandler.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_device_dialog);
        organizationField = (EditText) findViewById(R.id.organization);
        organizationField.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        pluginField = (EditText) findViewById(R.id.pluginName);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String organization = organizationField.getText().toString();
        String pluginName = pluginField.getText().toString();

        switch (view.getId()) {
            case R.id.confirm_button:
                networkHandler.addDevice(organization, pluginName, context);
                //TODO give correct response from server after adding device
                Toast.makeText(context, "Added device x. Server should let us what response " +
                                "it gave", Toast.LENGTH_SHORT).show();
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
