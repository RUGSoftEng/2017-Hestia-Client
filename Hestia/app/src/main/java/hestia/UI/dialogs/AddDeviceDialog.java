package hestia.UI.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.rugged.application.hestia.R;
import java.io.IOException;
import java.util.ArrayList;

import hestia.UI.elements.InstantAutoComplete;
import hestia.backend.ServerCollectionsInteractor;
import hestia.backend.exceptions.ComFaultException;
import hestia.backend.models.RequiredInfo;

/**
* This class opens the dialog to enter the collection name and plugin name.
* It then sends this to the networkHandler which tries to get the required info.
* If this works it consecutively opens a new dialog for the other info.
* @see EnterRequiredInfoDialog
 */

public class AddDeviceDialog extends HestiaDialog {
    private InstantAutoComplete collectionField, pluginField;
    private ArrayAdapter<String> adapterCollections;
    private ArrayAdapter<String> adapterPlugins;
    private ServerCollectionsInteractor serverCollectionsInteractor;
    private final static String TAG = "AddDeviceDialog";
    private FragmentManager fragmentManager;

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
        builder.setTitle("Add Device")

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
        collectionField = (InstantAutoComplete) view.findViewById(R.id.collection);
        collectionField.setAdapter(adapterCollections);
        collectionField.setThreshold(1);
        collectionField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                collectionField.showDropDown();
            }
        });

        adapterPlugins = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1);
        pluginField = (InstantAutoComplete) view.findViewById(R.id.pluginName);
        pluginField.setAdapter(adapterPlugins);
//        if (pluginField.isFocused()) {
//            Toast.makeText(getContext(), "I am focused", Toast.LENGTH_SHORT).show();
//            pluginField.showDropDown();
//        }
        pluginField.setThreshold(1);
//        pluginField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//
//            }
//        });

        getCollections();

        pluginField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getPlugins(collectionField.getText().toString());
//                if (pluginField.isFocused())
//                    pluginField.showDropDown();
            }
        });

        builder.setView(view);

        AlertDialog dlg = builder.create();
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dlg;
    }

    @Override
    void pressCancel() {
        Toast.makeText(getContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    void pressConfirm() {
        final String collection = collectionField.getText().toString();
        final String pluginName = pluginField.getText().toString();

         new AsyncTask<Object, String, RequiredInfo>() {
            @Override
            protected RequiredInfo doInBackground(Object... params) {
                RequiredInfo info = null;
                try {
                    info = serverCollectionsInteractor.getRequiredInfo(collection, pluginName);
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                    String exceptionMessage = "Could not connect to the server";
                    publishProgress(exceptionMessage);
                } catch (ComFaultException comFaultException) {
                    Log.e(TAG, comFaultException.toString());
                    String error = comFaultException.getError();
                    String message = comFaultException.getMessage();
                    String exceptionMessage = error + ":" + message;
                    publishProgress(exceptionMessage);
                }
                return info;
            }

             @Override
             protected void onProgressUpdate(String... exceptionMessage) {
                 Toast.makeText(getContext(), exceptionMessage[0], Toast.LENGTH_SHORT).show();
             }

            @Override
            protected void onPostExecute(RequiredInfo info) {
                if(info != null){
                    EnterRequiredInfoDialog fragment = EnterRequiredInfoDialog.newInstance();
                    fragment.setData(info, serverCollectionsInteractor);
                    if(fragmentManager == null){
                    }
                    fragment.show(fragmentManager, "dialog");
                }
            }
        }.execute();
    }

    private void getCollections() {
        new AsyncTask<Object, String, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Object... params) {
                ArrayList<String> collections = new ArrayList<String>();
                try {
                    collections = serverCollectionsInteractor.getCollections();
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                    String exceptionMessage = "Could not connect to the server";
                    publishProgress(exceptionMessage);
                } catch (ComFaultException comFaultException) {
                    Log.e(TAG, comFaultException.toString());
                    String error = comFaultException.getError();
                    String message = comFaultException.getMessage();
                    String exceptionMessage = error + ":" + message;
                    publishProgress(exceptionMessage);
                }
                return collections;
            }

            @Override
            protected void onProgressUpdate(String... exceptionMessage) {
                Toast.makeText(getContext(), exceptionMessage[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(ArrayList<String> collections) {
                adapterCollections.clear();
                if (adapterCollections != null) {
                    adapterCollections.addAll(collections);
                }
            }
        }.execute();
    }

    private void getPlugins(final String collection) {
        new AsyncTask<Object, String, ArrayList<String>>() {
            @Override
            protected ArrayList<String> doInBackground(Object... params) {
                ArrayList<String> plugins = null;
                try {
                    plugins = serverCollectionsInteractor.getPlugins(collection);
                } catch (IOException e) {
                    Log.e(TAG,e.toString());
                    String exceptionMessage = "Could not connect to the server";
                    publishProgress(exceptionMessage);
                } catch (ComFaultException comFaultException) {
                    Log.e(TAG, comFaultException.toString());
                    String error = comFaultException.getError();
                    String message = comFaultException.getMessage();
                    String exceptionMessage = error + ":" + message;
                    publishProgress(exceptionMessage);
                }
                return plugins;
            }

            @Override
            protected void onProgressUpdate(String... exceptionMessage) {
                Toast.makeText(getContext(), exceptionMessage[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(ArrayList<String> plugins) {
                adapterPlugins.clear();
                if(plugins != null) {
                    adapterPlugins.addAll(plugins);
                }
            }
        }.execute();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}

