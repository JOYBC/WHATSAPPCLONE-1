package com.coder1.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.coder1.myapplication.Adapters.ChatAdapter;
import com.coder1.myapplication.Model.MessageModel;
import com.coder1.myapplication.databinding.ActivityChatDetailBinding;
import com.coder1.myapplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
  ActivityChatDetailBinding binding;
  FirebaseAuth auth;
  FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding= ActivityChatDetailBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
      getSupportActionBar().hide();
      database=FirebaseDatabase.getInstance();
      auth=FirebaseAuth.getInstance();
      final String senderId= auth.getUid();
      String receiverId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");
        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar2).into(binding.profilePic);



        binding.backArrow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent= new Intent(ChatDetailActivity.this,MainActivity.class);
            startActivity(intent);
          }
        });
        final ArrayList<MessageModel> messageModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this,receiverId);
        binding.chatRecyclerView.setAdapter(chatAdapter);

      LinearLayoutManager layoutManager=new LinearLayoutManager(this);
      binding.chatRecyclerView.setLayoutManager(layoutManager);

      final String  senderRoom= senderId+receiverId;
      final String  receiverRoom= receiverId+senderId;

      database.getReference().child("Chats")
                      .child(senderRoom)
                              .addValueEventListener(new ValueEventListener() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                      messageModels.clear();
                                      for(DataSnapshot snapshot1:snapshot.getChildren())
                                      {
                                        MessageModel model=snapshot1.getValue(MessageModel.class);

                                        model.setMessageId(snapshot1.getKey());
                                        messageModels.add(model);
                                      }
                                      chatAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                              });






      binding.send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          String message= binding.enterMessage.getText().toString();
          final MessageModel model=new MessageModel(senderId,message);
          model.setTimestamp(new Date().getTime());
          binding.enterMessage.setText("");


          database.getReference().child("Chats")
                  .child(senderRoom)
                  .push()
                  .setValue(model)
                  .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
              database.getReference().child("Chats")
                      .child(receiverRoom)
                      .push()
                      .setValue(model)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
              });
            }
          });

        }
      });

    }
}