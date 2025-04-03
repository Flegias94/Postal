package com.example.postal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postal.OrderModel;
import com.example.postal.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder> {

    private final ArrayList<OrderModel> orders;
    private final Context context;

    public AdminOrderAdapter(ArrayList<OrderModel> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orders.get(position);

        holder.tvName.setText("Destinatar: " + order.getReceiverName());
        holder.tvAddress.setText("Adresa: " + order.getReceiverAdress());
        holder.tvWeight.setText("Greutate: " + order.getWeight() + " kg");
        holder.tvQuantity.setText("Bucăți: " + order.getQuantity());
        holder.tvPrice.setText("Preț: " + order.getPrice() + " lei");
        holder.tvStatus.setText(order.getStatus());

        if ("în procesare".equals(order.getStatus())) {
            holder.tvStatus.setTextColor(Color.RED);
            holder.btnComplete.setVisibility(View.VISIBLE);
        } else if ("completat".equals(order.getStatus())) {
            holder.tvStatus.setTextColor(Color.GREEN);
            holder.btnComplete.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setTextColor(Color.GRAY);
            holder.btnComplete.setVisibility(View.GONE);
        }

        // 🔘 Finalizează comanda
        holder.btnComplete.setOnClickListener(v -> {
            FirebaseFirestore.getInstance()
                    .collection("parcels")
                    .document(order.getId())
                    .update("status", "completat")
                    .addOnSuccessListener(unused -> {
                        order.setStatus("completat");
                        notifyItemChanged(holder.getAdapterPosition());
                        Toast.makeText(context, "Comandă finalizată!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Eroare la actualizare: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvWeight, tvQuantity, tvPrice, tvStatus;
        Button btnComplete;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnComplete = itemView.findViewById(R.id.btnComplete);
        }
    }
}
