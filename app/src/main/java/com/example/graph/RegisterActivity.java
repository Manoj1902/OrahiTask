package com.example.graph;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graph.database.DBHelper;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    //Register Section
    TextView titleRegisterSection;
    EditText fullName;
    EditText email;
    EditText password, confirmPassword;
    Button signUpBtn;
    TextView alreadyUser;

    //OTP Section
    TextView titleVerifyOTPSection;
    EditText txtOTP;
    Button verifyOTPButton;
    TextView resendOTP;

    int otp;

    DBHelper dbHelper;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Register Section
        titleRegisterSection = findViewById(R.id.textView2);
        fullName = findViewById(R.id.txtFullName_register);
        email = findViewById(R.id.txtEmail_register);
        password = findViewById(R.id.txtPassword_register);
        confirmPassword = findViewById(R.id.txtConfirmPassword_register);
        signUpBtn = findViewById(R.id.signUp_button_register);
        alreadyUser = findViewById(R.id.txtAlreadyRegistered_register);

        //Verify OTP Section
        titleVerifyOTPSection = findViewById(R.id.textView3);
        txtOTP = findViewById(R.id.txtVerifyOTP_veryfyOtp_register);
        verifyOTPButton = findViewById(R.id.verifyOTP_button_register);
        resendOTP = findViewById(R.id.txtResentOTP_register);


        dbHelper = new DBHelper(this);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }


    public void checkCredentials (){
        String username = fullName.getText().toString();
        String emailAddress = email.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        if (!username.equals("")){
            if (!emailAddress.equals("") && emailAddress.matches(emailPattern)){
                if (!pass.equals("") && pass.length() >= 8){
                    if (confirmPass.equals(pass)){
                        Boolean checkEmail = dbHelper.checkEmail(emailAddress);
                        if (!checkEmail){
                            titleRegisterSection.setVisibility(View.GONE);
                            fullName.setVisibility(View.GONE);
                            email.setVisibility(View.GONE);
                            password.setVisibility(View.GONE);
                            confirmPassword.setVisibility(View.GONE);
                            signUpBtn.setVisibility(View.GONE);
                            alreadyUser.setVisibility(View.GONE);

                            titleVerifyOTPSection.setVisibility(View.VISIBLE);
                            txtOTP.setVisibility(View.VISIBLE);
                            verifyOTPButton.setVisibility(View.VISIBLE);
                            resendOTP.setVisibility(View.VISIBLE);

                            generateOTP();

                            resendOTP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    generateOTP();
                                }
                            });

                            verifyOTPButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    verifyOTP();
                                }
                            });

                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User Already Exists. Please Login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        confirmPassword.setError("Password does'nt match");
                    }
                }
                else{
                    password.setError("Please input valid password");
                }
            }
            else{
                email.setError("Please input valid email");
            }
        }
        else{
            fullName.setError("Can't be Empty");
        }

//                Toast.makeText(RegisterActivity.this, "You can access this soon", Toast.LENGTH_SHORT).show();
    }

//    Generating OTP
    private void generateOTP() {
        otp = new Random().nextInt(9999-1000+1)+1000;
        Toast.makeText(this, "OTP: "+ String.valueOf(otp), Toast.LENGTH_LONG).show();
    }

    private void verifyOTP() {

        String username = fullName.getText().toString();
        String emailAddress = email.getText().toString();
        String pass = password.getText().toString();

        if(txtOTP.getText().toString().equals(String.valueOf(otp))){
            Boolean insert = dbHelper.insertData(username, emailAddress, pass);

            if (insert){
            Toast.makeText(RegisterActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }else{
            Toast.makeText(RegisterActivity.this, "Oops!! Registration Unsuccessful.", Toast.LENGTH_SHORT).show();
        }
        }

        else{
            Toast.makeText(this, "Incorrect OTP!!", Toast.LENGTH_SHORT).show();
        }

    }
}

