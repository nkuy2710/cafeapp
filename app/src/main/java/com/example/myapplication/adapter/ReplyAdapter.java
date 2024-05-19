package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Reply;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder>{
    private final List<Reply> replyList;

    public ReplyAdapter(List<Reply> replyList) {
        this.replyList = replyList;
    }

    @NonNull
    @Override
    public ReplyAdapter.ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ReplyViewHolder holder, int position) {
        Reply reply = replyList.get(position);
        if (reply == null) {
            return;
        }
        holder.nameTxt.setText(reply.getName());
        holder.phoneNumTxt.setText(reply.getPhoneNumber());
        holder.emailTxt.setText(reply.getEmail());
        holder.contentTxt.setText(reply.getContent());
    }

    @Override
    public int getItemCount() {
        if (!replyList.isEmpty()) {
            return replyList.size();
        }
        return 0;
    }

    public static class ReplyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTxt;
        private final TextView phoneNumTxt;
        private final TextView emailTxt;
        private final TextView contentTxt;
        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            phoneNumTxt = itemView.findViewById(R.id.phoneNumTxt);
            emailTxt = itemView.findViewById(R.id.emailTxt);
            contentTxt = itemView.findViewById(R.id.contentTxt);
        }
    }
}
