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

import com.wicak.plito.R;

import java.util.List;
public class RoomAdapter {

//public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
//
//
//    private Context mContext;
//    private List<Room> mChat;
//
//    public RoomAdapter(Context mContext, List<String> mChat){
//        this.mChat = mChat;
//        this.mContext = mContext;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item, parent, false);
//        return new RoomAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final Room chat = mChat.get(position);
//        holder.username.setText(chat.getUsername());
//        if (chat.getImageURL().equals("default")){
//            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
//        }else {
//            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
//        }
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, ChatMessage.class);
//                intent.putExtra("chatId", chat.getId());
//                intent.putExtra("receivedmsg", chat.getUsername());
//                mContext.startActivity(intent);
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mChat.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView username;
//        public ImageView profileImage;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            username = itemView.findViewById(R.id.group_chat);
//            profileImage = itemView.findViewById(R.id.profile_image);
//
//        }
//    }


}
