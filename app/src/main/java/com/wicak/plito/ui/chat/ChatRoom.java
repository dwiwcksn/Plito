package com.wicak.plito.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wicak.plito.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoom extends AppCompatActivity {
    private Button btn_send_msg;
    private EditText input_msg;
    private TextView chat_conversation;
    private String user_name, room_name;
    private DatabaseReference Dbref;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        btn_send_msg = findViewById(R.id.sendBtn);
        input_msg = findViewById(R.id.editChat);
        chat_conversation = findViewById(R.id.chatTxt);
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle("Room - "+room_name);

        Dbref = FirebaseDatabase.getInstance().getReference().child(room_name);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = Dbref.push().getKey();
                Dbref.updateChildren(map);

                DatabaseReference message_root = Dbref.child(temp_key);
                Map<String,Object> map2 = new HashMap<String,Object>();
                map2.put("name", user_name);
                map2.put("msg",input_msg.getText().toString());

                message_root.updateChildren(map2);
            }
        });

        Dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                append_chat_conversation(snapshot);
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                append_chat_conversation(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String chat_msg, chat_username;

    private void append_chat_conversation(DataSnapshot snapshot) {
        Iterator i = snapshot.getChildren().iterator();
        while (i.hasNext()){
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_username = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_username + " : "+chat_msg+"\n");
        }
    }
}