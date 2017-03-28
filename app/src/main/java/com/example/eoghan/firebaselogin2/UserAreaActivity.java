package com.example.eoghan.firebaselogin2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAreaActivity extends AppCompatActivity {

    private TextView nameTv;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        nameTv = (TextView) findViewById(R.id.userInfoTV);

        //FirebaseUser user = firebaseAuth.getCurrentUser();
        showInfo();
    }

    private void showInfo() {
        //serInformation userInformation = new UserInformation(name, a)
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        //databaseReference.child(user.getUid()).
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    //get data from snapshot
                    UserInformation userInformation = postSnapshot.getValue(UserInformation.class);

                    //add it to string
                    String string = "Name: "+ userInformation.getName()+"\nAddress: " +userInformation.getAddress();
                    nameTv.setText(string);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.print("fail" + databaseError.getMessage());
            }
        });
    }
}
