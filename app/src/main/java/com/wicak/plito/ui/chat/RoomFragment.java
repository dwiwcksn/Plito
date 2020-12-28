package com.wicak.plito.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wicak.plito.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RoomFragment extends Fragment {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private ListView listView;
    private List<String> mChat;
    private ArrayAdapter<String> arrayAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        final View root = inflater.inflate(R.layout.fragment_chatroom, container, false);

//        recyclerView = root.findViewById(R.id.recycler_view_chat);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChat = new ArrayList();
        listView = root.findViewById(R.id.chatListView);

        arrayAdapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1, mChat);
        listView.setAdapter(arrayAdapter);
        readChat();

        openchat();

        return root;
    }

    private void openchat() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentGroupName = adapterView.getItemAtPosition(i).toString();
                Intent I = new Intent(getContext(), ChatMessage.class);
                I.putExtra("room_name", currentGroupName);
                startActivity(I);
            }
        });
    }

    private void readChat() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatMessage");

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