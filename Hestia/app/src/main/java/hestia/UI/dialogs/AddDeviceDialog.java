package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import com.rugged.application.hestia.R;

import java.io.IOException;
import java.util.ArrayList;

import hestia.UI.activities.home.HomeActivity;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.RequiredInfo;

/**
* This class opens the dialog to enter the collection name and plugin name.
* It then sends this to the networkHandler which tries to get the required info.
* If this works it consecutively opens a new dialog for the other info.
* @see AddDeviceInfo
 */

public class AddDeviceDialog extends HestiaDialog2 {
    private AutoCompleteTextView collectionField, pluginField;
    private ArrayAdapter<String> adapterCollections;
    private ArrayAdapter<String> adapterPlugins;
    private ServerCollectionsInteractor serverCollectionsInteractor;
    private final static String TAG = "AddDeviceDialog";

    public static AddDeviceDialog newInstance() {
        AddDeviceDialog fragment = new AddDeviceDialog();
        return fragment;
    }

    public void setInteractor(ServerCollectionsInteractor interactor) {
        serverCollectionsInteractor = interactor;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set Dialog Title
        builder.setTitle("Change IP")

                // Positive button
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        pressConfirm();
                        dismiss();

                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }

                });
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.add_device_dialog, null);

        adapterCollections = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1);
        collectionField = (AutoCompleteTextView) view.findViewById(R.id.collection);
        collectionField.setAdapter(adapterCollections);
        collectionField.setThreshold(1);

        adapterPlugins = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1);
        pluginField = (AutoCompleteTextView) view.findViewById(R.id.pluginName);
        pluginField.setAdapter(adapterPlugins);
        pluginField.setThreshold(1);

        getCollections();

        pluginField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getPlugins(collectionField.getText().toString());
            }
        });

        builder.setView(view);

        AlertDialog dlg = builder.create();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dlg;
    }

//    public AddDeviceDialog(Context context, ServerCollectionsInteractor serverCollectionsInteractor) {
//        super(context, R.layout.add_device_dialog, "Add a device");
//        Log.i(TAG, "I am being called");
//        this.serverCollectionsInteractor = serverCollectionsInteractor;
//        if (serverCollectionsInteractor == null) {
//            Log.i(TAG, "ServerCollectionsInteractor is now null");
//        }
//    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        adapterCollections = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1);
//        collectionField = (AutoCompleteTextView) findViewById(R.id.collection);
//        collectionField.setAdapter(adapterCollections);
//        collectionField.setThreshold(1);
//
//        adapterPlugins = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1);
//        pluginField = (AutoCompleteTextView)findViewById(R.id.pluginName);
//        pluginField.setAdapter(adapterPlugins);
//        pluginField.setThreshold(1);
//
//        getCollections();
//
//        pluginField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                getPlugins(collectionField.getText().toString());
//            }
//        });
//    }



    @Override
    void pressCancel() {
        Toast.makeText(getContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    void pressConfirm() {

        final String collection = collectionField.getText().toString();
        final String pluginName = pluginField.getText().toString();

         new AsyncTask<Object, Object, RequiredInfo>() {
            @Override
            protected RequiredInfo doInBackground(Object... params) {
                RequiredInfo info = null;
                try {
                    info = serverCollectionsInteractor.getRequiredInfo(collection, pluginName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return info;
            }

            @Override
            protected void onPostExecute(RequiredInfo info) {
                if(info != null){
                    AddDeviceInfo fragment = AddDeviceInfo.newInstance();
                    fragment.setData(info, serverCollectionsInteractor);
                    fragment.show(getFragmentManager(), "dialog");
                }
            }
        }.execute();
    }

    private void getCollections() {
        new AsyncTask<Object, Object, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Object... params) {
                ArrayList<String> list = null;
                try {
                    list = serverCollectionsInteractor.getCollections();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ComFaultException e) {
                    Toast.makeText(getContext(), e.getError() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            protected void onPostExecute(ArrayList<String> collections) {
                adapterCollections.clear();
                if (adapterCollections == null) {
                    Log.i(TAG, "adapterCollections became null?");
                }
                adapterCollections.addAll(collections);
            }
        }.execute();
    }

    private void getPlugins(final String collection) {
        new AsyncTask<Object, Object, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Object... params) {
                ArrayList<String> list = null;
                try {
                    list = serverCollectionsInteractor.getPlugins(collection);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ComFaultException e) {
                    Toast.makeText(getContext(), e.getError() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            protected void onPostExecute(ArrayList<String> collections) {
                adapterPlugins.clear();
                adapterPlugins.addAll(collections);
            }
        }.execute();
    }
}

