package com.wicak.plito.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wicak.plito.R;
import com.wicak.plito.ui.info.InfoViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatFragment extends Fragment {

    private ChatViewModel chatViewModel;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private ListView listView;
    private DatabaseReference Dbref = FirebaseDatabase.getInstance().getReference().getRoot();

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_room = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_chat, container, false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        arrayAdapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1, list_of_room);

        listView = root.findViewById(R.id.chatListView);
        Button add_room = root.findViewById(R.id.addBtn);
        final EditText room_name = root.findViewById(R.id.nameRoom);

        listView.setAdapter(arrayAdapter);

        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(), "");
                Dbref.updateChildren(map);
            }
        });
        Dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = snapshot.getChildren().iterator();
                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                list_of_room.clear();
                list_of_room.addAll(set);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent I = new Intent(root.getContext(), ChatRoom.class);
                I.putExtra("room_name", ((TextView)view).getText().toString());
                I.putExtra("user_name", (mUser.getEmail()));
                startActivity(I);
            }
        });



        return root;
    }
}