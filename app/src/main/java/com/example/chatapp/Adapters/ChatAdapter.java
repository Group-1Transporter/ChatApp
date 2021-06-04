package com.example.chatapp.Adapters;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.Models.MessageModel;
import com.example.chatapp.R;

import com.example.chatapp.databinding.ChatListBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Handler;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.SenderViewHolder> {
    ArrayList<MessageModel> messageModels;
    Context context;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ChatListBinding binding = ChatListBinding.inflate(LayoutInflater.from(context));
        return new SenderViewHolder(binding);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull SenderViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){

           holder.binding.senderMsg.setText(messageModel.getMessage());
           long time = messageModel.getTimestamp();
           holder.binding.senderTime.setText(getDateCurrentTimeZone(time));
           holder.binding.left.setVisibility(View.GONE);
           holder.binding.right.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.left.setVisibility(View.VISIBLE);
            holder.binding.right.setVisibility(View.GONE);
            long time = messageModel.getTimestamp();
            holder.binding.reciverTime.setText(getDateCurrentTimeZone(time));
            holder.binding.reciver.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        ChatListBinding binding;
        public SenderViewHolder(ChatListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }




}
