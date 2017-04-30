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
import hestia.backend.PostDeviceTask;

/**
 * This class dynamically creates the fields for the required information.
 * It receives as arguments an activity and a HashMap<String,String> and then adds
 * the text and the fields from the HashMap keys, with the values as values.
 * Finally it sends back the HashMap to the cic which posts it to the server.
 */

public class AddDeviceInfo extends Dialog implements android.view.View.OnClickListener {
    private HashMap<String, String> fields;
    private Activity content;

    public AddDeviceInfo(Activity activity, HashMap<String, String> fields) {
        super(activity);
        this.content = activity;
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
        if (key.equals("organization")||key.equals("plugin")) {
            field.setFocusable(false);
            field.setClickable(false);
        }
        field.setLayoutParams(params);
        field.setWidth(800);
        return field;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                HashMap<String, String> h = getFieldValues();
                if(h==null) {
                    Toast.makeText(getContext(), "One or more empty values were entered."
                            , Toast.LENGTH_SHORT).show();
                    break;
                }
                new PostDeviceTask(h).execute();
                Toast.makeText(content, h.toString(), Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.back_button:
                Toast.makeText(content, "Cancel", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            default:
                break;
        }
    }

    private HashMap<String, String> getFieldValues() {
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

    private LinearLayout generateButtons(LinearLayout.LayoutParams params){
        // Create buttons.
        LinearLayout layout = new LinearLayout(content);
        final Button confirm = new Button(content);
        final Button cancel = new Button(content);
        confirm.setId(R.id.confirm_button);
        cancel.setId(R.id.back_button);
        confirm.setText("Confirm");
        cancel.setText("Cancel");
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        layout.addView(confirm);
        layout.addView(cancel);
        return layout;
    }
}