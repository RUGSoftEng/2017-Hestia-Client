package hestia.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.application.hestia.R;

public class ChangeNameDialog extends HestiaDialog {
    private EditText t;
    private String deviceTitle;

    public ChangeNameDialog(Context context, String deviceTitle) {
        super(context, R.layout.set_name, "Change name of your device");
        this.deviceTitle = deviceTitle;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        t = (EditText)findViewById(R.id.change_name_device);
        t.setText(deviceTitle);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    void pressCancel() {
        Toast.makeText(context, "pressed cancel", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    void pressConfirm() {
        String result = t.getText().toString();
        Toast.makeText(context, "Something should be done with result: " + result, Toast.LENGTH_SHORT)
                .show();
        dismiss();
    }
}
