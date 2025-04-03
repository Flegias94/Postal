package com.example.postal;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postal.adapters.AdminOrderAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdminOrderAdapter adapter;
    private ArrayList<OrderModel> ordersList = new ArrayList<>();
    private FirebaseFirestore db;

    public static final String EXTRA_USER_ID = "userId";
    public static final String EXTRA_USER_NAME = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 🔙 Back arrow și titlul
        if (getSupportActionBar() != null) {
            String userName = getIntent().getStringExtra(EXTRA_USER_NAME);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Comenzile lui " + (userName != null ? userName : "necunoscut"));
        }

        recyclerView = findViewById(R.id.recyclerAdminOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminOrderAdapter(ordersList, this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        String userId = getIntent().getStringExtra(EXTRA_USER_ID);
        if (userId == null) {
            Toast.makeText(this, "ID-ul utilizatorului nu a fost transmis!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔄 Încarcă comenzile utilizatorului
        db.collection("parcels")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(query -> {
                    ordersList.clear();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        OrderModel order = doc.toObject(OrderModel.class);
                        if (order != null) {
                            order.setId(doc.getId()); // 🔑 ca să putem face update
                            ordersList.add(order);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Eroare la încărcarea comenzilor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
