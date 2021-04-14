package com.example.geomemories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText usernameEditText, passwordEditText;
    TempActivity tempActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

    }

    public void loginBtn(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString("username", usernameEditText.getText().toString());
        editor.putString("password", passwordEditText.getText().toString());

        Boolean usernameEmpty = usernameEditText.getText().toString().isEmpty();
        Boolean passwordEmpty = passwordEditText.getText().toString().isEmpty();

        editor.commit();

        if (usernameEmpty) {
            Toast.makeText(this, "please fill username", Toast.LENGTH_SHORT).show();
            return;
        } else if (passwordEmpty) {
            Toast.makeText(this, "please fill password ", Toast.LENGTH_SHORT).show();
            return;
        } else if (usernameEmpty && passwordEmpty) {
            Toast.makeText(this, "please fill username and password ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "Username and password saved to Preferences", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}