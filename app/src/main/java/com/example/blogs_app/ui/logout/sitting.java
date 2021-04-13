package com.example.blogs_app.ui.logout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogs_app.R;
import com.example.blogs_app.share_Adaptor;
import com.example.blogs_app.share_content;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sitting extends Fragment {
    RecyclerView share_recyclerView;
    share_Adaptor shareAdaptor;
    ArrayList<share_content> share_contents;
    ImageView userimg;
    TextView username, follow;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sitting, container, false);
        userimg = v.findViewById(R.id.sitting_user_img);
        username = v.findViewById(R.id.sitting_username);
        follow = v.findViewById(R.id.siiting_follow);
        share_recyclerView = v.findViewById(R.id.sitting_recycle);
        share_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("follwers").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> follows = new ArrayList<>();

                    for (DataSnapshot d : snapshot.getChildren()) {
                        String shareContent = (String) d.getValue();
                        follows.add(shareContent);

                    }
                    follow.setText("Follow\n" + " "+follows.size());




                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            username.setText(user.getDisplayName());
            Picasso.get().load(user.getPhotoUrl()).into(userimg);

            DatabaseReference databaseReference = firebaseDatabase.getReference("share");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    share_contents = new ArrayList<>();

                    for (DataSnapshot d : snapshot.getChildren()) {
                        share_content shareContent = d.getValue(share_content.class);

                        share_contents.add(shareContent);

                    }


                    shareAdaptor = new share_Adaptor(getContext(), share_contents);
                    share_recyclerView.setAdapter(shareAdaptor);


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


}
