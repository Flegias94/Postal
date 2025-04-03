package com.example.postal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdminClientAdapter extends RecyclerView.Adapter<AdminClientAdapter.ClientViewHolder> {

    public interface OnClientClickListener {
        void onClick(UserModel client);
    }

    private final List<UserModel> clients;
    private final OnClientClickListener listener;

    public AdminClientAdapter(List<UserModel> clients, OnClientClickListener listener) {
        this.clients = clients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        UserModel client = clients.get(position);
        holder.tvClientId.setText("Nume: " + client.getSenderName());
        holder.tvEmail.setText("Email: " + client.getEmail());

        holder.itemView.setOnClickListener(v -> listener.onClick(client));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView tvClientId, tvEmail;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientId = itemView.findViewById(R.id.tvClientId);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}
