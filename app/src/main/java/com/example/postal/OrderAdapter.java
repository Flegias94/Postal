package com.example.postal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.util.List;
import java.util.Map;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Map<String, Object>> orderList;

    public OrderAdapter(List<Map<String, Object>> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Map<String, Object> order = orderList.get(position);

        holder.tvReceiverName.setText("Destinatar: " + order.get("receiverName"));
        holder.tvAddress.setText("Adresă: " + order.get("receiverAdress"));
        holder.tvWeight.setText("Greutate: " + order.get("weight") + " kg");
        holder.tvQuantity.setText("Cantitate: " + order.get("quantity"));
        holder.tvPrice.setText("Preț: " + order.get("price") + " lei");

        Timestamp timestamp = (Timestamp) order.get("timestamp");
        holder.tvDate.setText("Dată: " + timestamp.toDate().toString());

        String status = String.valueOf(order.get("status"));
        holder.tvStatus.setText("Status: " + status);

        if (status.equalsIgnoreCase("completat")) {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50")); // verde
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#F44336")); // roșu
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
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
