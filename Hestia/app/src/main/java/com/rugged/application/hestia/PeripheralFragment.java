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

import java.util.UUID;

/**
 * Created by Mark on 4-3-2017.
 */

public class PeripheralFragment extends Fragment {
    private Peripheral mPeripheral;
    //lock and unlock button
    private Button onButton;
    private Button offButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int peripheralId = (int) getActivity().getIntent()
                .getSerializableExtra(PeripheralActivity.EXTRA_PERIPHERAl_ID);
        mPeripheral = PeripheralLab.get(getActivity()).getPeripheral(peripheralId);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_peripheral, container, false);
        onButton = (Button)v.findViewById(R.id.on_button);
        offButton = (Button)v.findViewById(R.id.off_button);

        switch (mPeripheral.getType()) {
            case "Lock" :
                onButton.setText("Lock");
                offButton.setText("Unlock");
                break;
            case "Light" :
                onButton.setText("Light on");
                offButton.setText("Light off");
                break;
            default:
                onButton.setText("On");
                offButton.setText("Off");
                break;


        }

        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendJSONFile().execute("82.73.173.179", "openLock");
            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendJSONFile2().execute("82.73.173.179", "closeLock");

            }
        });




        return v;
    }

    private class SendJSONFile extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... strings) {
            Client client = new Client("82.73.173.179", 8000);
            client.sendActionRequest(mPeripheral.getId(), "openLock");
            client.closeClientSocket();
            return null;
        }
    }

    private class SendJSONFile2 extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... strings) {
            Client client = new Client("82.73.173.179", 8000);
            client.sendActionRequest(mPeripheral.getId(), "closeLock");
            client.closeClientSocket();
            return null;
        }
    }


}
