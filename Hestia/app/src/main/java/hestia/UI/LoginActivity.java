package hestia.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rugged.application.hestia.R;

import java.util.Locale;

/**
 *  This class handles the login activity.
 *  It takes the fields from the layout, gets the values the user inputs and validates it.
 *  Furthermore it first checks the shared preferences of the phone if the user is remembered.
 */

public class LoginActivity extends Activity  {
    private Button loginButton;
    private EditText userField,passField;
    private CheckBox rememberButton;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private TextView attemptsText;
    private int counter = 10;
    private String username,password;
    public static final String LOGIN_PREFERENCES = "LoginPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent fromIntent = getIntent();
        String extra = fromIntent.getStringExtra("login");

        loginPreferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        Boolean saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            if(extra==null) {
                gotoMainActivity();
            }
            else{
                clearSaveLogin();
            }
        }

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
        return(username.equals("admin")&&password.equals("password"));
    }

    private void gotoMainActivity(){
        Intent toIntent = new Intent(LoginActivity.this, DeviceListActivity.class);
        startActivity(toIntent);
        finish();
    }

    private void setSaveLogin(String username, String password){
        loginPrefsEditor = loginPreferences.edit();
        loginPrefsEditor.putBoolean("saveLogin", true);
        loginPrefsEditor.putString("username", username);
        loginPrefsEditor.putString("password", password);
        loginPrefsEditor.apply();
    }

    private void clearSaveLogin(){
        loginPrefsEditor = loginPreferences.edit();
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