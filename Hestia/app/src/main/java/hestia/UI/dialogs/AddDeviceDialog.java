package hestia.UI.dialogs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import hestia.backend.Cache;
import hestia.backend.models.RequiredInfo;

/**
* This class opens the dialog to enter the collection name and plugin name.
* It then sends this to the networkHandler which tries to get the required info.
* If this works it consecutively opens a new dialog for the other info.
* @see AddDeviceInfo
 */

public class AddDeviceDialog extends HestiaDialog {
    private EditText collectionField, pluginField;
    private Cache cache;

    public AddDeviceDialog(Context context, Cache cache) {
        super(context, R.layout.add_device_dialog, "Add a device");
        this.cache = cache;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        collectionField = (EditText)findViewById(R.id.collection);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        pluginField = (EditText)findViewById(R.id.pluginName);
    }

    @Override
    void pressCancel() {
        Toast.makeText(context, "Cancel pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    void pressConfirm() {
        final String collection = collectionField.getText().toString();
        final String pluginName = pluginField.getText().toString();
         new AsyncTask<Object, Object, RequiredInfo>() {
            @Override
            protected RequiredInfo doInBackground(Object... params) {
                RequiredInfo info =  cache.getRequiredInfo(collection, pluginName);
                return info;
            }

            @Override
            protected void onPostExecute(RequiredInfo info) {
                new AddDeviceInfo(context, info, cache);
            }
        }.execute();
    }


}
