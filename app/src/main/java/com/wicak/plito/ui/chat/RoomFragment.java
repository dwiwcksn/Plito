package com.wicak.plito.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wicak.plito.R;
import com.wicak.plito.SignInActivity;
import com.wicak.plito.ui.info.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class RoomFragment extends Fragment {

    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private TextView hiddenKelas;
    FirebaseUser fUser;
    DatabaseReference usersRef;
    String currentRoomKode, getCurrentRoomKode;
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
        hiddenKelas = root.findViewById(R.id.hidden_klas_txt);

        arrayAdapter = new ArrayAdapter<String>(getContext() , android.R.layout.simple_list_item_1, mChat);
        listView.setAdapter(arrayAdapter);
        readRoom();
        openRoom();

        return root;
    }



    private void openRoom() {
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

    private void readRoom() {
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentRoomKode = user.getKodeKelas();
                hiddenKelas.setText(currentRoomKode);
                getCurrentRoomKode = hiddenKelas.getText().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatMessage");
        Query query = reference.orderByValue().equalTo(getCurrentRoomKode);

        Log.d(TAG, "readRoom: " + getCurrentRoomKode);
        query.addValueEventListener(new ValueEventListener() {
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