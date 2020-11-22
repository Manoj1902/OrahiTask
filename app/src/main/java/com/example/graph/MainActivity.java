package com.example.graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graph.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    EditText loginUserName;
    EditText loginPassword;
    Button loginButton;
    TextView notRegisteredTxt;
    DBHelper dbHelper;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUserName = findViewById(R.id.txtUserName_main);
        loginPassword = findViewById(R.id.txtPassword_main);
        loginButton = findViewById(R.id.loginButton_main);
        notRegisteredTxt = findViewById(R.id.txtNotRegistered_main);

        dbHelper = new DBHelper(this);

        preferences = getSharedPreferences("LoginPref", MODE_PRIVATE);

        if(!preferences.getString("userName", "0").equals("0")){

            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();

        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserAndPassword();
            }
        });

        notRegisteredTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

    }

    private void checkUserAndPassword() {
        String username = loginUserName.getText().toString();
        String password = loginPassword.getText().toString();

        if (!username.equals("")){
            if(!password.equals("")){
                Boolean checkUserAndPass = dbHelper.checkUserAndPassword(username, password);

                if (checkUserAndPass){
                    Toast.makeText(this, "Login Successful!!", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userName", username);
                    editor.commit();

                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }else{
                    Toast.makeText(this, "Username or Password does'nt match.", Toast.LENGTH_SHORT).show();
                }
            }else {
                loginPassword.setError("Wrong Password");
            }
        }else{
            loginUserName.setError("Please Input Valid Username");
        }

    }
}