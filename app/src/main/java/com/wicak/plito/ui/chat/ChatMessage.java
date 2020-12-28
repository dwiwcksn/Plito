package com.wicak.plito.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wicak.plito.R;
import com.wicak.plito.ui.info.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ChatMessage extends AppCompatActivity {

    ImageView profile_image;
    TextView username, chat_conversationSender, chat_conversationReceiver;
    ImageButton btn_send;
    EditText text_send;
    String currentUName, currentImg, currentDate, currentTime;

    MessageAdapter messageAdapter;
    List<Message> mMessage;

    RecyclerView recyclerView;

    FirebaseUser fUser;
    DatabaseReference roomRef, usersRef, GroupMessageKeyRef;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.recycler_view_message);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
//        chat_conversationReceiver = findViewById(R.id.chat_conversation);
//        chat_conversationSender = findViewById(R.id.chat_conversation_sender);


        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();

        final String chatId = intent.getStringExtra("chatId");
        final String currentGroupName = intent.getStringExtra("room_name");


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        roomRef = FirebaseDatabase.getInstance().getReference().child("ChatMessage").child(currentGroupName);


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentUName = user.getUsername();
                currentImg = user.getImageURL();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Room chat = snapshot.getValue(Room.class);
////                setTitle(chat.getUsername());
                setTitle(currentGroupName);
                username.setText(currentGroupName);
                profile_image.setImageResource(R.mipmap.ic_launcher);

////                if (chat.getImageURL().equals("default")){
////                    profile_image.setImageResource(R.mipmap.ic_launcher);
////                }else{
////                    profile_image.setImageResource(R.mipmap.ic_launcher);
////                }

//                readMessages(fUser.getUid(), currentGroupName);
                readMessages(currentUName, currentGroupName, currentImg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fUser.getUid(), currentImg, currentGroupName, msg);
                }else {
                    Toast.makeText(ChatMessage.this, "You cant send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

//        roomRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                append_chat_conversatin(dataSnapshot);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                append_chat_conversatin(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }


    private void sendMessage(String sender, String imageurl ,String receiver, String message){
        String msgKey = roomRef.push().getKey();

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("sender", sender);
//        hashMap.put("receiver", receiver);
//        hashMap.put("message", message);
//
//        reference.child("Chats").push().setValue(hashMap);

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());

        HashMap<String, Object> groupMessageKey = new HashMap<>();
        roomRef.updateChildren(groupMessageKey);



        GroupMessageKeyRef  = roomRef.child(msgKey);
        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("sender", sender);
        messageInfoMap.put("senderName", currentUName);
        messageInfoMap.put("receiver", receiver);
        messageInfoMap.put("message", message);
        messageInfoMap.put("imageURL", imageurl);
        messageInfoMap.put("date", currentDate);
        messageInfoMap.put("time", currentTime);
        GroupMessageKeyRef.updateChildren(messageInfoMap);
    }

    private void readMessages(final String myid, final String currentGroupName, final String currentImg){
        mMessage = new ArrayList<>();

        roomRef = FirebaseDatabase.getInstance().getReference("ChatMessage").child(currentGroupName);
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMessage.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
//                    if (message.getReceiver().equals(myid) && message.getSender().equals(currentGroupName)){
                        mMessage.add(message);
//                    }
                    messageAdapter = new MessageAdapter(ChatMessage.this, mMessage, currentImg);
                    recyclerView.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}