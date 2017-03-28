package com.example.eoghan.firebaselogin2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextAddress;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextAddress =(EditText) findViewById(R.id.addressTxt);
        editTextName =(EditText) findViewById(R.id.nameET);
        btnSave = (Button) findViewById(R.id.addUserBtn);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextView tvWelcome = (TextView) findViewById(R.id.welcomeTV);
        buttonLogout = (Button) findViewById(R.id.lgoutBtn);
        buttonLogout.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tvWelcome.setText("welcome" +user.getEmail());
    }


    private void saveUserInfo() {
        String name = editTextName.getText().toString().trim();
        String add = editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, add);

        FirebaseUser user = firebaseAuth.getCurrentUser();

            databaseReference.child(user.getUid()).setValue(userInformation);
            Toast.makeText(this, "info saved", Toast.LENGTH_LONG).show();


    }
    @Override
    public void onClick(View v) {
        if(v == buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (v == btnSave){
            saveUserInfo();
        }
    }
}
