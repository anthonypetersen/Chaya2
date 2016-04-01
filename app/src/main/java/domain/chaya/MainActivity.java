package domain.chaya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url = "https://amber-heat-6570.firebaseio.com";
    Firebase ref;
    EditText email;
    EditText password;
    TextView loginCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize Firebase reference
        Firebase.setAndroidContext(this);
        ref = new Firebase(url);

        //Have to find the Views that we want to access from here
        email = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        loginCheck = (TextView) findViewById(R.id.loginCheck);

        final Button sub_button = (Button) findViewById(R.id.submit_button);
        sub_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Firebase userRef = ref.child("users").child(email.getText().toString());
                User temp = new User(email.getText().toString(),password.getText().toString());
                userRef.setValue(temp);
            }
        });

        final Button login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        loginCheck.setText("Login Successful");
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        loginCheck.setText("Login Failed");
                    }
                };
                ref.authWithPassword(email.getText().toString(), password.getText().toString(), authResultHandler);
            }
        });


    }
}

class User {

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
