package hestia.UI.activities.discovery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import hestia.UI.activities.home.HomeActivity;
import hestia.UI.activities.login.LoginActivity;

public class ServerDiscoveryActivity extends Activity {

    private final String intentExtra = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        Intent fromIntent = getIntent();
        String extra = fromIntent.getStringExtra(intentExtra);

        loginPreferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        if(loginPreferences.getString(prefsUser,"").equals("")){
            setSharedPreferences(standardUser, standardPass,false);
        }
        Boolean saveLogin = loginPreferences.getBoolean(saveLoginString, false);
        if (saveLogin) {
            if(extra==null) {
                gotoMainActivity();
            }
            else{
                clearSaveLogin();
            }
        }

}
