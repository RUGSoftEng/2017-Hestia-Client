package com.rugged.application.hestia;

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

import java.util.Locale;


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
    public static final String LOGIN_PREFERENCES = "LoginPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.loginButton);
        userField = (EditText)findViewById(R.id.username);
        passField = (EditText)findViewById(R.id.password);
        rememberButton = (CheckBox) findViewById(R.id.rememberButton);

        attemptsText = (TextView)findViewById(R.id.textView4);
        attemptsText.setVisibility(View.GONE);


        loginPreferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            userField.setText(loginPreferences.getString("username", ""));
            passField.setText(loginPreferences.getString("password", ""));
            rememberButton.setChecked(true);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = userField.getText().toString();
                password = passField.getText().toString();
                if(checkCredentials(username,password)){
                    if (rememberButton.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.apply();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.apply();
                    }
                    // TODO: Redirect to the PeripheralListActivity
                    Toast.makeText(getApplicationContext(),
                            "Correct, redirecting now.",Toast.LENGTH_SHORT).show();
                    //gotoPeripheralListActivity();

                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials"
                            ,Toast.LENGTH_SHORT).show();

                    attemptsText.setVisibility(View.VISIBLE);
                    counter--;
                    attemptsText.setText(String.format(Locale.getDefault(), "%d",counter));

                    if (counter == 0) {
                        loginButton.setEnabled(false);
                    }
                }
            }
        });
    }

    public boolean checkCredentials(String username,String password){
        // TODO: Check credentials with server database
        return(username.equals("admin")&&password.equals("admin"));
    }

    public void gotoPeripheralListActivity(){
        Intent intent = new Intent(getApplicationContext(), PeripheralListActivity.class);
        startActivity(intent);
    }
}
