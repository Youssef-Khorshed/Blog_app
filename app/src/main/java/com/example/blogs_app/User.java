package com.example.blogs_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class User extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    ArrayList<String> data = new ArrayList<>();
    FirebaseAuth auth;
    boolean test_follw;
    Button b;
    String user;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        circleImageView = findViewById(R.id.iii);
        savedInstanceState = getIntent().getExtras();
        user = savedInstanceState.getString("userid");
        String img = savedInstanceState.getString("img");
        Picasso.get().load(img).into(circleImageView);


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("follwers");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        b = findViewById(R.id.fff);
        checker();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();

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

    void check() {
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
                        data.add(d.getValue().toString());
                    }
                    startActivity(new Intent(User.this, MyhomeActivity.class).putExtra("data", data));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    void checker() {
        try {
            test_follw = true;
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (test_follw) {
                        if (snapshot.child(firebaseUser.getUid()).hasChild(user)) {
                            //    data.remove(position);
                            b.setText("Followed");
                            test_follw = false;


                        } else {
                            //  data.add(position, user);
                            b.setText("Follow");
                            test_follw = false;

                        }

                    }
                    //      Toast.makeText(getApplicationContext(), ""+data.size(), Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}