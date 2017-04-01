package hestia.UI;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.util.HashMap;

import hestia.backend.ClientInteractionController;

public class AddDeviceInfo extends Dialog implements android.view.View.OnClickListener{
    private Button confirm,cancel;
    private ClientInteractionController cic;
    private HashMap<String,String> fields;
    public Activity c;
    public AddDeviceInfo(Activity a, HashMap<String,String> fields) {
        super(a);
        this.c = a;
        this.cic = ClientInteractionController.getInstance();
        this.fields = fields;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.enter_device_info);

        final RelativeLayout rm = (RelativeLayout) findViewById(R.id.linearMain);

        for (String key : fields.keySet()){
            // Add all the different fields...
            // Create LinearLayout
            RelativeLayout rl = new RelativeLayout(getContext());

            // Create TextView
            TextView product = new TextView(getContext());
            product.setText(key);
            rl.addView(product);

            //Create EditText

            EditText field = new EditText(getContext());
            field.setText(key);
            rl.addView(field);

            rm.addView(rl);
        }

        confirm = (Button) findViewById(R.id.confirm_button);
        cancel = (Button) findViewById(R.id.back_button);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.confirm_button:
                dismiss();
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
