/**
 * This class smoothens the transition from an activity to a fragment.
 */

package hestia.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import hestia.backend.ClientInteractionController;

public abstract class SingleFragmentActivity extends AppCompatActivity{
    protected abstract Fragment createFragment();
    private static final String TAG = "SingleFragmentActivity";
    private SwipeRefreshLayout layout;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        layout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.i(TAG, "Creating toolbar");

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_SHORT).show();
                //retrieve the newest list of devices
                layout.setRefreshing(false);
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setIp:
                showIpDialog();
                return true;

            case R.id.action_logout:
                gotoLoginActivity("login","logout");
                return true;

            case R.id.action_changepass:
                changePassword();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void gotoLoginActivity(String type, String extraIntent){
        Intent i = new Intent(SingleFragmentActivity.this, LoginActivity.class);
        i.putExtra(type, extraIntent);
        startActivity(i);
        finish();
    }

    private void showIpDialog() {
        IpDialog ipDialog = new IpDialog(SingleFragmentActivity.this);
        ipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ipDialog.show();
    }

    private void changePassword(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Password");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPassword = input.getText().toString();
                gotoLoginActivity("changePassword",newPassword);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
