package com.coder1.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coder1.myapplication.Model.Status;
import com.coder1.myapplication.Model.UserStatus;
import com.coder1.myapplication.R;
import com.coder1.myapplication.databinding.ActivityStatusBinding;

import java.util.ArrayList;
import java.util.List;
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    Context context;
    ArrayList<UserStatus> userStatuses;
    public StatusAdapter(Context context,ArrayList<UserStatus>userStatuses){
        this.context=context;
        this.userStatuses=userStatuses;
    }
    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_status,parent,false);
        return  new StatusViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder{
        ActivityStatusBinding binding;
        public StatusViewHolder(@NonNull View itemView) {
                super(itemView);
                binding=ActivityStatusBinding.bind(itemView);
            }

        }
    }


