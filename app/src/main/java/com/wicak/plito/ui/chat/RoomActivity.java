package com.wicak.plito.ui.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wicak.plito.R;
import com.wicak.plito.ui.info.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class RoomActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private TextView hiddenKelas;
    FirebaseUser fUser;
    DatabaseReference usersRef;
    String currentRoomKode, getCurrentRoomKode;
    private ListView listView;
    private List<String> mChat;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

//        recyclerView = root.findViewById(R.id.recycler_view_chat);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //        Untuk Room Chat
        mChat = new ArrayList();
        listView = findViewById(R.id.chatListView);
//        hiddenKelas = findViewById(R.id.hidden_klas_txt);

        arrayAdapter = new ArrayAdapter<String>(RoomActivity.this , android.R.layout.simple_list_item_1, mChat);
        listView.setAdapter(arrayAdapter);
        readRoom();
        openRoom();
    }


    private void openRoom() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentGroupName = adapterView.getItemAtPosition(i).toString();
                Intent I = new Intent(RoomActivity.this, ChatMessage.class);
                I.putExtra("room_name", currentGroupName);
                startActivity(I);
            }
        });
    }

    private void readRoom() {
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentRoomKode = user.getKodeKelas();
//                hiddenKelas.setText(currentRoomKode);
//                getCurrentRoomKode = hiddenKelas.getText().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatMessage");
        Query query = reference.orderByValue().equalTo(getCurrentRoomKode);

        Log.d(TAG, "readRoom: " + getCurrentRoomKode);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
//                for (DataSnapshot snapshot1 : snapshot.getChildren()){
//                    Room chat = snapshot1.getValue(Room.class);
//                    if(!chat.getId().equals(firebaseUser.getUid())){
//                        mChat.add(chat);
//                    }
//                }

                Set<String> set = new HashSet<String>();
                Iterator i = snapshot.getChildren().iterator();
                while ( i.hasNext())
                {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                mChat.clear();
                mChat.addAll(set);

                arrayAdapter.notifyDataSetChanged();
//                roomAdapter = new RoomAdapter(getContext(), mChat);
//                recyclerView.setAdapter(roomAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}