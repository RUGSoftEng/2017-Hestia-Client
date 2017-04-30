package hestia.UI;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.rugged.application.hestia.R;

import hestia.backend.BackendInteractor;

/**
* This class opens the dialog to enter the organization name and plugin name.
* It then sends this to the backendInteractor which tries to get the required info.
* If this works it consecutively opens a new dialog for the other info.
* @see hestia.UI.AddDeviceInfo
 */

public class AddDeviceDialog extends Dialog implements android.view.View.OnClickListener {
    private EditText organizationField, pluginField;
    private Button confirm, cancel;
    private BackendInteractor backendInteractor;
    private Activity context;

    public AddDeviceDialog(Activity activity) {
        super(activity);
        this.context = activity;
        this.backendInteractor = BackendInteractor.getInstance();;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_device_dialog);
        organizationField = (EditText) findViewById(R.id.organization);
        pluginField = (EditText) findViewById(R.id.pluginName);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String organization = organizationField.getText().toString();
        String pluginName = pluginField.getText().toString();

        switch (v.getId()) {
            case R.id.confirm_button:
                backendInteractor.addDevice(organization, pluginName, context);
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
