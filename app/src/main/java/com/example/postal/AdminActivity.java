package com.example.postal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerClients;
    private List<UserModel> clientList = new ArrayList<>();
    private AdminClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Toolbar + titlu + logout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView title = findViewById(R.id.tvToolbarTitle);
        title.setText("Lista clienților");

        TextView btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // RecyclerView setup
        recyclerClients = findViewById(R.id.recyclerClients);
        recyclerClients.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminClientAdapter(clientList, this::onClientClicked);
        recyclerClients.setAdapter(adapter);

        loadClients();
    }

    private void loadClients() {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("role", "client")
                .get()
                .addOnSuccessListener(snapshot -> {
                    clientList.clear();
                    for (DocumentSnapshot doc : snapshot) {
                        UserModel user = doc.toObject(UserModel.class);
                        if (user != null) {
                            user.setUserId(doc.getId());
                            clientList.add(user);
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void onClientClicked(UserModel client) {
        Intent intent = new Intent(this, AdminOrdersActivity.class);
        intent.putExtra("userId", client.getUserId());
        intent.putExtra("userName", client.getSenderName());
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // back ←
        return true;
    }
}
