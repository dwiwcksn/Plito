package com.wicak.plito.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wicak.plito.R;

import java.util.List;

import static java.security.AccessController.getContext;

//public class MessageAdapter {

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    FirebaseUser fuser;

    private Context mContext;
    private List<Message> mMessage;
    private String imageurl;

    public MessageAdapter(Context mContext, List<Message> mMessage, String imageurl){
        this.mMessage = mMessage;
        this.mContext = mContext;
        this.imageurl = imageurl;

    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final Message message = mMessage.get(position);

        holder.show_username.setText(message.getSenderName());
        holder.show_message.setText(message.getMessage());
//        holder.profileImage.setImageResource(R.mipmap.ic_launcher);

        if ( imageurl != null && imageurl.equals("default")){
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(imageurl).into(holder.profileImage);
        }

    }

    @Override
    public int getItemCount() {
        return mMessage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public TextView show_username;
        public ImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_username = itemView.findViewById(R.id.show_username);
            show_message = itemView.findViewById(R.id.show_message);
            profileImage = itemView.findViewById(R.id.profile_image);

        }
    }

    @Override
    public int getItemViewType(int position){
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mMessage.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return  MSG_TYPE_LEFT;
        }
    }

}
