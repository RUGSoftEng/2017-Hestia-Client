/* This class handles the login activity.
 * It takes the fields from the layout, gets the values the user inputs and validates it.
 * Furthermore it first checks the shared preferences of the phone if the user is remembered.
 */

package hestia.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.util.Locale;

import hestia.backend.ClientInteractionController;

/**
 * This class represents a login screen, complete with remember me function.
 */
public class LoginActivity extends Activity  {
    private Button loginButton;
    private EditText userField,passField;
    private CheckBox rememberButton;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    private TextView attemptsText;
    private int counter = 10;
    private String username,password;
    private String corrpass;
    public static final String LOGIN_PREFERENCES = "LoginPreferences";
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(corrpass==null){
            corrpass = "password";
        }

        /* Before going on, check if the user is remembered, if so, directly redirect. */
        Intent i = getIntent();
        String extra = i.getStringExtra("login");

        loginPreferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            // Check if the user is redirected (from login)
            if(!extra.equals("logout")) {
                gotoMainActivity();
            }
        }

//        extra = i.getStringExtra("changePassword");
//        if (extra!=null){
//            Log.i("LOGIN",extra);
//            corrpass = extra;
//        }

        /* Get all the buttons and fields from the layout */
        loginButton = (Button)findViewById(R.id.loginButton);
        userField = (EditText)findViewById(R.id.username);
        passField = (EditText)findViewById(R.id.password);
        rememberButton = (CheckBox) findViewById(R.id.rememberButton);
        attemptsText = (TextView)findViewById(R.id.textView4);
        attemptsText.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            /* Button has been clicked, get and check credentials */
            @Override
            public void onClick(View v) {
                username = userField.getText().toString();
                password = passField.getText().toString();
                if(checkCredentials(username,password)){
                    if (rememberButton.isChecked()) {
                        setSaveLogin(username,password);
                    } else {
                        clearSaveLogin();
                    }
                    showLoginToast("Correct, redirecting...");
                    gotoMainActivity();
                }else{
                    showLoginToast("Wrong credentials.");
                    editLoginAttempts();
                }
            }
        });
    }

    private boolean checkCredentials(String username,String password){
        return(username.equals("admin")&&password.equals(corrpass));
    }

    private void gotoMainActivity(){
        Intent i = new Intent(LoginActivity.this, DeviceListActivity.class);
        startActivity(i);
        finish();
    }

    private void setSaveLogin(String username, String password){
        loginPrefsEditor.putBoolean("saveLogin", true);
        loginPrefsEditor.putString("username", username);
        loginPrefsEditor.putString("password", password);
        loginPrefsEditor.apply();
    }

    private void clearSaveLogin(){
        loginPrefsEditor.clear();
        loginPrefsEditor.apply();
    }

    private void editLoginAttempts(){
        attemptsText.setVisibility(View.VISIBLE);
        counter--;
        attemptsText.setText(String.format(Locale.getDefault(), "%d",counter));
        if (counter == 0) {
            loginButton.setEnabled(false);
        }
    }

    private void showLoginToast(String info){
        Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
    }
}