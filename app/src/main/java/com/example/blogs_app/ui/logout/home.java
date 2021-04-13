package com.example.blogs_app.ui.logout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogs_app.R;
import com.example.blogs_app.commnet_Activity;
import com.example.blogs_app.data;
import com.example.blogs_app.message_data;
import com.example.blogs_app.share_Adaptor;
import com.example.blogs_app.share_content;
import com.example.blogs_app.sharedviewmode;
import com.example.blogs_app.postAdaptor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class home extends Fragment {
    RecyclerView recyclerView;
    postAdaptor postAdaptor;
    ArrayList<message_data> posts;
    ImageView Like_view;
    public static String user = null;
    public static ArrayList<String> getfollwersdata;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user =auth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("message");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    posts = new ArrayList<>();

                    for (DataSnapshot d : snapshot.getChildren()) {
                        message_data messageData = d.getValue(message_data.class);

                        if (messageData.getUser_id().compareTo(user.getUid())==0)
                        {
                            posts.add(messageData);
                        }
                        for (String x : getfollwersdata) {
                            if (x.compareTo(messageData.getUser_id()) == 0  ) {
                                posts.add(messageData);
                                break;
                            }


                        }

                    }


                    //   Toast.makeText(getContext(), "hi "+getfollwersdata.size(), Toast.LENGTH_SHORT).show();


                    postAdaptor = new postAdaptor(getActivity(), posts);
                    recyclerView.setAdapter(postAdaptor);
                    postAdaptor.setOnItemClickListener(new postAdaptor.OnItemClickListener() {
                        @Override
                        public void onEditClick(int position) {
                            Edit(position);
                        }

                        @Override
                        public void onShare(int position) {
                            Share(position);
                        }
                    });


                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }






    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.my_home_page, container, false);
        recyclerView = v.findViewById(R.id.recycler);
        Like_view = v.findViewById(R.id.like_btn1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return v;
    }

    public void Edit(int positon) {

        Intent intent = new Intent(getContext(), commnet_Activity.class);
        intent.putExtra("title", posts.get(positon).getTitle());
        intent.putExtra("desc", posts.get(positon).getDescription());
        intent.putExtra("postimg", posts.get(positon).getPost_image());
        intent.putExtra("username", posts.get(positon).getUser_name());
        intent.putExtra("userid", posts.get(positon).getUser_name());
        intent.putExtra("userimg", posts.get(positon).getUser_image());
        intent.putExtra("postdate", posts.get(positon).getDate());
        intent.putExtra("postkey", posts.get(positon).getPost_key());
        postAdaptor.notifyItemChanged(positon);
        getContext().startActivity(intent);

    }

    public void Share(int position) {

        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user =auth.getCurrentUser();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("share").push();
            Dialog set_message = new Dialog(getContext());
            set_message.setContentView(R.layout.share_layout);
            set_message.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            set_message.getWindow().getAttributes().gravity = Gravity.BOTTOM;
            ImageView userimg = set_message.findViewById(R.id.share_user_img);
            ImageView share_btn = set_message.findViewById(R.id.share_btn);
            TextView comment = set_message.findViewById(R.id.share_comment);
            TextView username = set_message.findViewById(R.id.share_username_);
            Picasso.get().load(user.getPhotoUrl()).into(userimg);
            username.setText(user.getDisplayName());
            Date currenttime = Calendar.getInstance().getTime();
            String post_date = DateFormat.getInstance().format(currenttime);
            set_message.show();
            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    databaseReference.setValue(new share_content(
                            posts.get(position).getPost_image(),
                            posts.get(position).getUser_image(),
                            posts.get(position).getUser_name(),
                            posts.get(position).getDate(),
                            posts.get(position).getTitle(),
                            posts.get(position).getDescription(),
                            user.getPhotoUrl().toString(),
                            user.getDisplayName(),
                            post_date,
                            comment.getText().toString(),
                            databaseReference.getKey()

                            ));
                    set_message.dismiss();
                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    /*
    private void message() {


        user_message_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user_message_title.getText().toString().trim().isEmpty()) {

                    user_message_title.setError("Enter your Name");
                } else if (user_message_description.getText().toString().trim().isEmpty()) {
                    user_message_description.setError("Enter your Description");

                } else if (img_uri == null) {
                    Toast.makeText(getApplicationContext(), "Select your image", Toast.LENGTH_SHORT).show();

                } else {
                    user_message_progressbar.setVisibility(View.VISIBLE);
                    user_message_send.setVisibility(View.INVISIBLE);

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User_message_post_photos");
                    StorageReference referenc = storageReference.child(img_uri.getLastPathSegment());
                    referenc.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            referenc.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String userimg = user.getPhotoUrl().toString();
                                    Date currenttime = Calendar.getInstance().getTime();
                                    String post_date = DateFormat.getInstance().format(currenttime);
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference("message").push();
                                    //     String key = databaseReference.getKey();
                                    //     data.setPost_key(key);

                                    databaseReference.setValue(new message_data(
                                            user_message_title.getText().toString()
                                            , user_message_description.getText().toString()
                                            , user.getDisplayName()
                                            , user.getUid()
                                            , uri.toString()
                                            , userimg
                                            , user.getEmail()
                                            , databaseReference.getKey()
                                            , post_date


                                    )).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            user_message_progressbar.setVisibility(View.INVISIBLE);
                                            user_message_send.setVisibility(View.VISIBLE);
                                            set_message.dismiss();
                                            Toast.makeText(getApplicationContext(), "post..added..successfully..!", Toast.LENGTH_LONG).show();

                                        }
                                    });


                                }


                            });
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            user_message_progressbar.setVisibility(View.INVISIBLE);
                            user_message_send.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }
        });


    }


     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postAdaptor.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


}
