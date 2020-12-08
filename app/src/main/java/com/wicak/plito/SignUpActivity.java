package com.wicak.plito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail, mPass;
    private TextView mTextView;
    private Button signUpButton;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.editTextEmail);
        mPass = findViewById(R.id.editTextPassword);
        mTextView =findViewById(R.id.textView4);

        mAuth = FirebaseAuth.getInstance();

        signUpButton = findViewById(R.id.signUpBtn);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }



    private void createUser(){
        String email = mEmail.getText().toString();
        String pass = mPass.getText().toString();

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty() && pass.length() >= 6){
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                mPass.setText("Minimal 6 karakter");
            }
        }else if (email.isEmpty()){
            mEmail.setText("Mohon isi email");
        }else{
            mEmail.setText("Email Salah");
        }
    }
}