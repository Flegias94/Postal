package com.example.postal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface OnUserClick {
        void onClick(UserItem user);
    }

    private final List<UserItem> users;
    private final OnUserClick listener;

    public UserAdapter(List<UserItem> users, OnUserClick listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserItem user = users.get(position);
        holder.email.setText(user.getEmail());
        holder.count.setText("Comenzi: " + user.getOrderCount());
        holder.itemView.setOnClickListener(v -> listener.onClick(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView email, count;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(android.R.id.text1);
            count = itemView.findViewById(android.R.id.text2);
        }
    }
}
