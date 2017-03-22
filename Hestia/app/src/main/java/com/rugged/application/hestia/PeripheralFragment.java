package com.rugged.application.hestia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import java.util.UUID;

//Fragment class taking care of a specific Peripheral
public class PeripheralFragment extends Fragment {
    private Peripheral mPeripheral;
    //lock and unlock button
    private Button onButton;
    private Button offButton;
    private int peripheralId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //receive the id from the PeripheralActivity
        peripheralId = (int) getActivity().getIntent()
                .getSerializableExtra(PeripheralActivity.EXTRA_PERIPHERAl_ID);
//        mPeripheral = PeripheralLab.get(getActivity()).getPeripheral(peripheralId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_peripheral, container, false);
        ViewFlipper vf = (ViewFlipper) v.findViewById( R.id.viewFlipper );
        if (peripheralId % 2 == 0) {
            vf.setDisplayedChild(vf.indexOfChild(v.findViewById(R.id.first_layout)));
            //set the properties of the first layout here
        } else {
            vf.setDisplayedChild(vf.indexOfChild(v.findViewById(R.id.second_layout)));
            //set the properties of the second layout here
        }
//        onButton = (Button)v.findViewById(R.id.on_button);
//        offButton = (Button)v.findViewById(R.id.off_button);

//        switch (mPeripheral.getType()) {
//            case "Lock" :
//                onButton.setText("Lock");
//                offButton.setText("Unlock");
//                break;
//            case "Light" :
//                onButton.setText("Light on");
//                offButton.setText("Light off");
//                break;
//            default:
//                onButton.setText("On");
//                offButton.setText("Off");
//                break;
//        }

//        onButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new SendJSONFile().execute("172.20.10.2", "openLock");
//            }
//        });
//
//        offButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new SendJSONFile().execute("172.20.10.2", "closeLock");
//
//            }
//        });
        return v;
    }

    //Thread classes taking care of sending commands
    private class SendJSONFile extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            Client client = new Client("172.20.10.2", 8850);
            client.sendActionRequest(mPeripheral.getId(), "openLock");
            client.closeClientSocket();
            return null;
        }
    }
}
