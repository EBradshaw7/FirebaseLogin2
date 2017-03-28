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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_LONG;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextAddress;
    private Button btnSave;
    private TextView textViewData;
    private Button checkinBtn;

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

        textViewData = (TextView) findViewById(R.id.textView123);
        editTextAddress =(EditText) findViewById(R.id.addressTxt);
        editTextName =(EditText) findViewById(R.id.nameET);
        btnSave = (Button) findViewById(R.id.addUserBtn);
        checkinBtn = (Button) findViewById(R.id.button2);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        TextView tvWelcome = (TextView) findViewById(R.id.welcomeTV);
        buttonLogout = (Button) findViewById(R.id.lgoutBtn);
        buttonLogout.setOnClickListener(this);
        checkinBtn.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tvWelcome.setText("welcome" +user.getEmail());
    }


    private void saveUserInfo() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, address);

        FirebaseUser user = firebaseAuth.getCurrentUser();

            databaseReference.child(user.getUid()).setValue(userInformation);
            Toast.makeText(this, "info saved", LENGTH_LONG).show();

        //value listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    //get data from snapshot
                    UserInformation userInformation = postSnapshot.getValue(UserInformation.class);

                    //add it to string
                    String string = "Name: "+ userInformation.getName()+"\nAddress: " +userInformation.getAddress();
                    textViewData.setText(string);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("fail" + databaseError.getMessage());
            }
        });



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
        if (v == checkinBtn){
            finish();
            startActivity(new Intent(this, UserAreaActivity.class));
        }
    }
}
