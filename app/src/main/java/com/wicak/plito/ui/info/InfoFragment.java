package com.wicak.plito.ui.info;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.wicak.plito.R;
import com.wicak.plito.SignInActivity;
import com.wicak.plito.ui.materi.MateriViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class InfoFragment extends Fragment {
    private TextView profleName, profileEmail;
    private String profilekode;
    private CircleImageView profileImage;
    private Button signOutBtn, addRoom;
    private EditText roomName, kodeName;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    private DatabaseReference userRef;
    private FirebaseUser fUser;

    private InfoViewModel infoViewModel;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        final View root = inflater.inflate(R.layout.fragment_info, container, false);

        profleName = root.findViewById(R.id.profileName);
        profileEmail = root.findViewById(R.id.profileEmail);
        profileImage = root.findViewById(R.id.profileImage);
        signOutBtn = root.findViewById(R.id.signOutBtn);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();


        fUser = mAuth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                profleName.setText(user.getUsername());
                profileEmail.setText(user.getKodeKelas());
                if (user.getImageURL().equals("default")){
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(getContext()).load(user.getImageURL()).into(profileImage);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        roomName = root.findViewById(R.id.add_room_txt);
        kodeName = root.findViewById(R.id.room_kode_txt);
        addRoom = root.findViewById(R.id.add_room_btn);

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("ChatMessage");

                Map<String,Object> chatroom = new HashMap<String,Object>();
                chatroom.put(roomName.getText().toString(),"");

                ref.updateChildren(chatroom);

/////////////

//                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("RoomChat");
//                String roomKey = ref1.push().getKey();
//                DatabaseReference roomRef = ref1.child(kodeName.getText().toString());
//
//                Map<String,Object> chatroom1 = new HashMap<String,Object>();
////                chatroom1.put(roomName.getText().toString(),"");
//                chatroom1.put("RoomName" ,kodeName.getText().toString());
//
//                roomRef.updateChildren(chatroom1);

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        return root;
    }

    private void openImage() {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading..");
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() +"."+ getFileExtension(imageUri) );

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        userRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);

                        userRef.updateChildren(map);

                        pd.dismiss();

                    }else{
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }else {
            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() != null){
            imageUri = data.getData();

            if (uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getContext(), "Upload in Progress", Toast.LENGTH_SHORT).show();
            }else{
                uploadImage();
            }
        }

    }
}