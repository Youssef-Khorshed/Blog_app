package com.example.blogs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.blogs_app.ui.logout.sitting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ArrayList<String> data = new ArrayList<>();
    FirebaseAuth auth;
    boolean test_follw;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        savedInstanceState = getIntent().getExtras();
        String user = savedInstanceState.getString("userid");
        int position = savedInstanceState.getInt("index");
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("follwers");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        Button b = findViewById(R.id.button);
//        Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    test_follw = true;
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (test_follw) {
                                if (snapshot.child(firebaseUser.getUid()).hasChild(user)) {
                                    //    data.remove(position);
                                    reference.child(firebaseUser.getUid()).child(user).removeValue();
                                    test_follw = false;
                                } else {
                                    //  data.add(position, user);
                                    reference.child(firebaseUser.getUid()).child(user).setValue(user);
                                    test_follw = false;
                                }

                            }
                            //      Toast.makeText(getApplicationContext(), ""+data.size(), Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            data = new ArrayList<>();
                            for (DataSnapshot d : snapshot.getChildren()) {
                                //Toast.makeText(getApplicationContext(), ""+d.child(firebaseUser.getUid()).getValue().toString(), Toast.LENGTH_SHORT).show();
                                data.add(d.getValue().toString());
                            }
                            //   Toast.makeText(getApplicationContext(), ""+data.size(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(User.this, MyhomeActivity.class).putExtra("data", data));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                /*
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot d : snapshot.getChildren())
                        {
                            Toast.makeText(getApplicationContext(),d.getValue().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            */

            }
        });


    }
}