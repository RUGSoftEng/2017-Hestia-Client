package com.rugged.application.hestia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class LoginActivity extends Activity  {
    Button loginButton;
    EditText userField,passField;
    RadioButton rememberButton;

    TextView attemptsText;
    int counter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Check SharedPreferences, for remembered user/pass. Then redirect.


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.loginButton);
        userField = (EditText)findViewById(R.id.username);
        passField = (EditText)findViewById(R.id.password);
        rememberButton = (RadioButton)findViewById(R.id.rememberButton);

        attemptsText = (TextView)findViewById(R.id.textView4);
        attemptsText.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCredentials(userField.getText().toString()
                        , passField.getText().toString())){
                    // TODO: Input the data in SharedPreferences object,

                    // TODO: Redirect to the PeripheralListActivity
                    Toast.makeText(getApplicationContext(),
                            "Correct, redirecting now.",Toast.LENGTH_SHORT).show();

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
        return(username=="admin"&&password=="admin");
    }
}
