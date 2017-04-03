package hestia.UI;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.util.HashMap;

import hestia.backend.ClientInteractionController;

public class AddDeviceInfo extends Dialog implements android.view.View.OnClickListener {
    private ClientInteractionController cic;
    private HashMap<String, String> fields;
    public Activity c;

    public AddDeviceInfo(Activity a, HashMap<String, String> fields) {
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

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
        int count = 0;

        for (String key : fields.keySet()) {
            LinearLayout ll = new LinearLayout(getContext());

            // Add text
            TextView name = new TextView(getContext());
            name.setText(key);
            ll.addView(name);

            //Add field
            EditText field = createEditText();
            field.setText(fields.get(key));
            field.setId(count);
            if (fields.get(key) != null) {
                field.setFocusable(false);
                field.setClickable(false);
            }
            field.setLayoutParams(params);
            ll.addView(field);

            lm.addView(ll);
            count++;
        }
        LinearLayout ll = generateButtons(params);
        lm.addView(ll);

    }

    private EditText createEditText() {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(800, 150);
        final EditText edittext = new EditText(getContext());
        edittext.setLayoutParams(lparams);
        edittext.setWidth(800);
        return edittext;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case 11:
                // Fill hashmap, send to backend
                HashMap<String, String> h = getFieldValues();
                if(h==null) {
                    Toast.makeText(getContext(), "One or more incorrect values were entered."
                            , Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                }
                //cic.postRequiredInfo(h);
                Toast.makeText(getContext(), "Added device", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case 12:
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public HashMap<String, String> getFieldValues() {
        int i = 0;
        for (String key : fields.keySet()) {
            EditText field = (EditText) findViewById(i);
            String value = field.getText().toString();
            if(value.equals("")){
                return null;
            }
            fields.put(key, value);
            i++;
        }
        return fields;
    }

    public LinearLayout generateButtons(LinearLayout.LayoutParams params){
        // Add buttons.
        LinearLayout ll = new LinearLayout(getContext());
        int i = 10;
        final Button confirm = new Button(getContext());
        final Button cancel = new Button(getContext());
        confirm.setId(i + 1);
        cancel.setId(i + 2);
        confirm.setText("Confirm");
        cancel.setText("Cancel");
        // set the layoutParams on the button
        confirm.setLayoutParams(params);
        cancel.setLayoutParams(params);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ll.addView(confirm);
        ll.addView(cancel);
        return ll;
    }
}
