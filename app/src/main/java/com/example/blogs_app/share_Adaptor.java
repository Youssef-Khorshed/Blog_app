package com.example.blogs_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class share_Adaptor extends RecyclerView.Adapter<share_Adaptor.ViewHolder> {
    Context context;
    ArrayList<share_content> share_contents;

    public share_Adaptor(Context context, ArrayList<share_content> share_contents) {
        this.context = context;
        this.share_contents = share_contents;
    }

    @NonNull
    @Override
    public share_Adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.share_show_message_home, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull share_Adaptor.ViewHolder holder, int position) {
        holder.shared_user_name.setText(share_contents.get(position).getShared_user_name());
        holder.sharer_date.setText(share_contents.get(position).getUser_date());
        holder.sharer_title.setText(share_contents.get(position).getUser_comment());
        holder.sharer_usernam.setText(share_contents.get(position).getUser_name());
        Picasso.get().load(share_contents.get(position).getUser_img()).into(holder.sharer_user_img);
        holder.shared_title.setText(share_contents.get(position).getShared_user_title());
        holder.shared_desc.setText(share_contents.get(position).getShared_user_description());
        Picasso.get().load(share_contents.get(position).getShared_img()).into(holder.shared_post_img);
        holder.shared_user_date.setText(share_contents.get(position).getShared_user_date());
        Picasso.get().load(share_contents.get(position).getShare_user_img()).into( holder.shared_user_img);


    }

    @Override
    public int getItemCount() {
        return share_contents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sharer_usernam, sharer_date, sharer_title, shared_user_name, shared_user_date,
                shared_title, shared_desc;
        public ImageView sharer_user_img, shared_user_img, shared_post_img, shared_like_btn1,
                shared_message_btn, shared_share_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            sharer_usernam = itemView.findViewById(R.id.sharer_usernam);
            sharer_date = itemView.findViewById(R.id.sharer_date);
            sharer_title = itemView.findViewById(R.id.sharer_title);
            shared_user_name = itemView.findViewById(R.id.shared_user_name);
            shared_user_date = itemView.findViewById(R.id.shared_user_date);
            shared_title = itemView.findViewById(R.id.shared_title);
            shared_desc = itemView.findViewById(R.id.shared_desc);
            sharer_user_img = itemView.findViewById(R.id.sharer_user_img);
            shared_user_img = itemView.findViewById(R.id.sitting_user_img);
            shared_post_img = itemView.findViewById(R.id.shared_post_img);
            shared_like_btn1 = itemView.findViewById(R.id.shared_like_btn1);
            shared_message_btn = itemView.findViewById(R.id.shared_message_btn);
            shared_share_btn = itemView.findViewById(R.id.shared_share_btn);


        }
    }
}
