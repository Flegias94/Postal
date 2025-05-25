package com.example.postal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderModel> orders;

    public OrderAdapter(List<OrderModel> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orders.get(position);

        holder.tvReceiverName.setText("Destinatar: " + order.getReceiverName());
        holder.tvAddress.setText("Adresă: " + order.getReceiverAdress());
        holder.tvWeight.setText("Greutate: " + order.getWeight() + " kg");
        holder.tvQuantity.setText("Cantitate: " + order.getQuantity());
        holder.tvPrice.setText("Preț: " + order.getPrice() + " lei");

        // Formatăm data frumos
        Timestamp timestamp = order.getTimestamp();
        if (timestamp != null) {
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    .format(timestamp.toDate());
            holder.tvDate.setText("Dată: " + formattedDate);
        } else {
            holder.tvDate.setText("Dată: necunoscută");
        }

        String status = order.getStatus();
        holder.tvStatus.setText("Status: " + status);

        if ("completat".equalsIgnoreCase(status)) {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50")); // verde
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#F44336")); // roșu
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvReceiverName, tvAddress, tvWeight, tvQuantity, tvPrice, tvStatus, tvDate;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReceiverName = itemView.findViewById(R.id.tvReceiverName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
