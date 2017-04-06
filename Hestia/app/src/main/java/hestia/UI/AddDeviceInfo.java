package hestia.UI;

/**
 * This class dynamically creates the fields for the required information.
 * It receives as arguments an activity and a HashMap<String,String> and then adds
 * the text and the fields from the HashMap keys, with the values as values.
 */

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
    public Activity content;
    private final int Confirm = 11;
    private final int Cancel = 12;

    public AddDeviceInfo(Activity a, HashMap<String, String> fields) {
        super(a);
        this.content = a;
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
            LinearLayout ll = new LinearLayout(content);

            // Add text
            TextView name = new TextView(content);
            name.setText(key);
            ll.addView(name);

            //Add field
            EditText field = createEditText(key, params , count);
            ll.addView(field);

            lm.addView(ll);
            count++;
        }
        LinearLayout ll = generateButtons(params);
        lm.addView(ll);

    }

    private EditText createEditText(String key, LinearLayout.LayoutParams params, int count) {
        final EditText field = new EditText(content);
        field.setText(fields.get(key));
        field.setId(count);
        if (fields.get(key) != null) {
            field.setFocusable(false);
            field.setClickable(false);
        }
        field.setLayoutParams(params);
        field.setWidth(800);
        return field;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case Confirm:
                // Fill hashmap, send to backend
                HashMap<String, String> h = getFieldValues();
                if(h==null) {
                    Toast.makeText(getContext(), "One or more incorrect values were entered."
                            , Toast.LENGTH_SHORT).show();
                    break;
                }
                //cic.postRequiredInfo(h);
                Toast.makeText(content, h.toString(), Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case Cancel:
                Toast.makeText(content, "Cancel", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                break;
        }
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
        LinearLayout ll = new LinearLayout(content);
        int i = 10;
        final Button confirm = new Button(content);
        final Button cancel = new Button(content);
        confirm.setId(i + 1);
        cancel.setId(i + 2);
        confirm.setText("Confirm");
        cancel.setText("Cancel");
        // set the layoutParams on the button
        ll.setLayoutParams(params);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        ll.addView(confirm);
        ll.addView(cancel);
        return ll;
    }
}
