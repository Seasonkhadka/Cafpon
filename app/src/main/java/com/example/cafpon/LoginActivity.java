package com.example.cafpon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mRegisterNowBtn;
    FirebaseAuth fAuth;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mLoginBtn=findViewById(R.id.loginBtn);
        mRegisterNowBtn=findViewById(R.id.registerNowBtn);
        mProgressBar=findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailTxt=mEmail.getText().toString();
                final String passwordTxt=mPassword.getText().toString();
                if(TextUtils.isEmpty(emailTxt)){
                    mEmail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(passwordTxt)){
                    mPassword.setError("password is required");
                    return;
                }
                if (passwordTxt.length()<8){
                    mPassword.setError("Password Must be greater than 8 character");
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged in successfully",Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"Please check your email or password",Toast.LENGTH_SHORT);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });


        mRegisterNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));

            }
        });

    }
}