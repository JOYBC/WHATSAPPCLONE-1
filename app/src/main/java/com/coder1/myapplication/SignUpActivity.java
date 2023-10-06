package com.coder1.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import com.coder1.myapplication.Model.Users;
import com.coder1.myapplication.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    ActivitySignUpBinding binding;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account.");
        binding.b1Signup.setOnClickListener(v -> {
            if(!(binding.e1.getText().toString().isEmpty())&& !(binding.e2.getText().toString().isEmpty()) && !(binding.e3.getText().toString().isEmpty())){
               progressDialog.show();
               mAuth.createUserWithEmailAndPassword(binding.e2.getText().toString(),binding.e3.getText().toString())
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressDialog.dismiss();
                               if(task.isSuccessful()){
                                   Users user= new
                                           Users(binding.e1.getText().toString(),binding.e2.getText().toString(),binding.e3.getText().toString());
                                   String id=task.getResult().getUser().getUid();
                                   database.getReference().child("Users").child(id).setValue(user);
                                   Toast.makeText(SignUpActivity.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
            }
            else{
                Toast.makeText(SignUpActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
            }
        });
        if(mAuth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        }
        binding.tvalready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}