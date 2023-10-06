package com.coder1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.coder1.myapplication.databinding.ActivityOtpBinding;
import com.coder1.myapplication.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class OtpActivity extends AppCompatActivity {
ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        binding.sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(OtpActivity.this,PinActivity.class);
                intent.putExtra("phoneNumber",binding.editText.getText().toString());
                startActivity(intent);
            }
        });

    }
}