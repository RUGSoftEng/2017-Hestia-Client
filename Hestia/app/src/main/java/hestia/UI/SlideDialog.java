/*
This class handles the dialog which is opened if a Device has the 'slide' option
 */

package hestia.UI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import hestia.UIWidgets.HestiaSeekbar;
import hestia.backend.Activator;
import hestia.backend.ActivatorState;
import hestia.backend.ClientInteractionController;
import hestia.backend.Device;

public class SlideDialog extends Dialog implements android.view.View.OnClickListener{
    private final static String TAG = "SlideDialog";
    private Device d;
    private boolean changer;
    private ArrayList<Activator> fields;
    private Context context;
    private ClientInteractionController cic;

    public SlideDialog(Context a, ArrayList<Activator> fields, Device d) {
        super(a);
        this.context = a;
        this.cic = ClientInteractionController.getInstance();
        this.fields = fields;
        this.d = d;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slide_dialog);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
        int count = 0;

        for (Activator activator : fields) {
            LinearLayout ll = new LinearLayout(context);

            // Add text
            TextView name = new TextView(context);
            name.setText(activator.getName());
            ll.addView(name);

            //Add field
            HestiaSeekbar bar = createSlider(activator,count);
            bar.getActivatorSeekBar().setProgress(Integer.parseInt(activator.getState().toString()));
            ll.addView(bar.getActivatorSeekBar());

            lm.addView(ll);
            count++;
        }
        LinearLayout ll = generateButtons(params);
        lm.addView(ll);
    }


    public HestiaSeekbar createSlider(Activator activator, int count){
        HestiaSeekbar slider = new HestiaSeekbar(d,activator,findViewById(android.R.id.content),count);
        return slider;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case 11:
                // Fill hashmap, send to backend
                sendActivatorStates();
                Toast.makeText(getContext(), "Sent states", Toast.LENGTH_SHORT).show();
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

    public void sendActivatorStates(){
        int i = 0;
        for (Activator a : fields) {
            SeekBar bar = (SeekBar) findViewById(i);
            int value = bar.getProgress();
            ActivatorState<Integer> state = a.getState();
            state.setState(value);
            cic.setActivatorState(d,a.getId(),state);
            i++;
        }
    }


    public LinearLayout generateButtons(LinearLayout.LayoutParams params){
        // Add buttons.
        LinearLayout ll = new LinearLayout(context);
        int i = 10;
        final Button confirm = new Button(context);
        final Button cancel = new Button(context);
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