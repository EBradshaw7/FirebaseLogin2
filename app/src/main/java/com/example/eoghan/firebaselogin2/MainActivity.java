package com.example.eoghan.firebaselogin2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegister;
    private Button btnLogin;
    private EditText etEmail;
    private EditText etpword;
    private TextView tvreg;

    private ProgressDialog pbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister = (Button) findViewById(R.id.regBtn);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etpword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btLg);
        pbar = new ProgressDialog(this);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
    }



    private void registerUser(){
        String email= etEmail.getText().toString().trim();
        String password = etpword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "enter password", Toast.LENGTH_LONG).show();
            return;
        }

        pbar.setMessage("waiting");
        pbar.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(new Intent(getApplicationContext(),ProfileActivity.class)));
                            //Toast.makeText(MainActivity.this, "well ddone", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "fail, email already in use", Toast.LENGTH_LONG).show();
                        }
                        pbar.dismiss();
                    }
                });

        }




    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            registerUser();

            }

        if (v == btnLogin){
            if (firebaseAuth.getCurrentUser() != null){
                //finish();
                Toast.makeText(MainActivity.this, "fail, already logged in", Toast.LENGTH_LONG).show();
            }else {
                startActivity(new Intent(this, LoginActivity.class));
                }
            }
        }



}
