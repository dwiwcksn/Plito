package com.wicak.plito.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wicak.plito.BottomActivity;
import com.wicak.plito.LoggedIn;
import com.wicak.plito.R;
import com.wicak.plito.SignInActivity;
import com.wicak.plito.SignUpActivity;
import com.wicak.plito.ui.info.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class ChatFragment extends Fragment {


    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signInButton;
    private CheckBox showPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        final View root = inflater.inflate(R.layout.fragment_chatroom, container, false);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        if (mUser != null){
            Intent intent = new Intent(getContext(), RoomActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        mEmail = root.findViewById(R.id.emailSignIn);
        mPass = root.findViewById(R.id.passSignIn);
        mTextView = root.findViewById(R.id.toSignUp);
        progressBar = root.findViewById(R.id.progressBar2);
        showPassword = root.findViewById(R.id.showPass);

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPassword.isChecked()){
                    mPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    mPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signInButton = root.findViewById(R.id.signInBtn);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        return root;
    }

    private void loginUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();


        if(TextUtils.isEmpty(email)){
            mEmail.setError("Email is Required.");
            return;
        }


        if(TextUtils.isEmpty(pass)){
            mPass.setError("Password is Required.");
            return;
        }

        if(pass.length() < 6){
            mPass.setError("Password Must be >= 6 Characters");
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent =  new Intent(getContext(), RoomActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            }
        });

    }


}