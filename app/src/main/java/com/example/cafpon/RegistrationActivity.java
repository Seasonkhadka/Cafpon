package com.example.cafpon;

import androidx.activity.result.contract.ActivityResultContracts;
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

public class RegistrationActivity extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mconpassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginNowBtn;
    FirebaseAuth fAuth;
    ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mFullName=findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPhone=findViewById(R.id.nickname);
        mPassword=findViewById(R.id.password);
        mconpassword=findViewById(R.id.conformPassword);

        mRegisterBtn=findViewById(R.id.registerNow);
        mLoginNowBtn=findViewById(R.id.loginNowBtn);
        mProgressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            Toast.makeText(RegistrationActivity.this,"Already registered",Toast.LENGTH_SHORT);

        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullNameTxt=mFullName.getText().toString();
                final String emailTxt=mEmail.getText().toString();
                final String nicknameTxt=mPhone.getText().toString();
                final String passwordTxt=mPassword.getText().toString();
                final String conPasswordTxt=mconpassword.getText().toString();
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
                if(!passwordTxt.equals(conPasswordTxt)) {
                    Toast.makeText(RegistrationActivity.this, "암호가 서로 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
                mProgressBar.setVisibility(View.VISIBLE);
                //register user
                fAuth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this,"User Created",Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else {
                            Toast.makeText(RegistrationActivity.this,"Error",Toast.LENGTH_SHORT);
                        }
                    }
                });

            }
        });
        mLoginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));

            }
        });

    }
}