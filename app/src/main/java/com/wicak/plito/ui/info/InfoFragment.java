package com.wicak.plito.ui.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.wicak.plito.R;
import com.wicak.plito.SignInActivity;
import com.wicak.plito.ui.materi.MateriViewModel;

public class InfoFragment extends Fragment {
    private Button signOutBtn;
    private FirebaseAuth mAuth;

    private InfoViewModel infoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        infoViewModel =
                new ViewModelProvider(this).get(InfoViewModel.class);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View root = inflater.inflate(R.layout.fragment_info, container, false);
        final TextView textView = root.findViewById(R.id.text_info);
        infoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);


            }
        });
        signOutBtn = root.findViewById(R.id.signOutBtn);

        mAuth = FirebaseAuth.getInstance();

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

        return root;
    }
}