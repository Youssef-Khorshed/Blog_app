package com.example.blogs_app.ui.logout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blogs_app.R;
import com.example.blogs_app.User;
import com.example.blogs_app.data;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profile extends Fragment {
    RecyclerView recyclerView;
    user_searchAdaptor postAdaptor;
    ArrayList<data> userinfo = new ArrayList<>();
    Senddata senddata;
    ImageView imageView;

    public interface Senddata {
        public void send(String data,boolean d);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       try {
           FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
           DatabaseReference databaseReference = firebaseDatabase.getReference("user_info");
           FirebaseAuth auth = FirebaseAuth.getInstance();
           FirebaseUser user =auth.getCurrentUser();
           databaseReference.addValueEventListener(new ValueEventListener() {

               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {

                   for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                       data messageData = dataSnapshot.getValue(data.class);
                       if(user.getUid().compareTo(messageData.getUserid())!=0) {
                           userinfo.add(messageData);
                       }
                   }
                   postAdaptor = new user_searchAdaptor(userinfo, getContext());
                   recyclerView.setAdapter(postAdaptor);
                   postAdaptor.setOnClickListener(new user_searchAdaptor.OnItemClickListener() {
                       @Override
                       public void Edit_item(int position, boolean like_state) {
                           startActivity(new Intent(getActivity(), User.class)
                                   .putExtra("userid",userinfo.get(position).getUserid())
                                   .putExtra("index",position)
                                   .putExtra("img",userinfo.get(position).getUserphoto())
                                   .putExtra("state", like_state)




                           );
                       }
                   });

               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       } catch (Exception e) {
           Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
       }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.profile, container, false);
        recyclerView = v.findViewById(R.id.profile_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        imageView = v.findViewById(R.id.follow_user);



        return v;

    }

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Senddata) {
            senddata = (Senddata) context;
        } else {
            throw new RuntimeException(context.toString() + " must implemented..!!!");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        senddata = null;
    }
}
