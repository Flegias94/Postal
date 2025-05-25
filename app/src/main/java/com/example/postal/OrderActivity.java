// OrderActivity.java – Doar pentru clienți
package com.example.postal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    private EditText etReceiverName, etReceiverAddress, etWeight, etQuantity;
    private TextView tvTotalPrice;
    private Button btnSubmitOrder, btnLogout;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        etReceiverName = findViewById(R.id.etReceiverName);
        etReceiverAddress = findViewById(R.id.etReceiverAddress);
        etWeight = findViewById(R.id.etWeight);
        etQuantity = findViewById(R.id.etQuantity);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        btnLogout = findViewById(R.id.btnLogout);

        TextWatcher watcher = new TextWatcher() {
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { updateTotal(); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        };

        etWeight.addTextChangedListener(watcher);
        etQuantity.addTextChangedListener(watcher);

        btnSubmitOrder.setOnClickListener(v -> submitOrder());
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(OrderActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void updateTotal() {
        try {
            double w = Double.parseDouble(etWeight.getText().toString());
            int q = Integer.parseInt(etQuantity.getText().toString());
            double rate = (w <= 5) ? 5 : (w <= 10) ? 10 : 20;
            double total = w * rate * q;
            tvTotalPrice.setText("Total: " + total + " lei");
        } catch (Exception e) {
            tvTotalPrice.setText("Total: 0 lei");
        }
    }

    private void submitOrder() {
        String name = etReceiverName.getText().toString().trim();
        String address = etReceiverAddress.getText().toString().trim();
        String wStr = etWeight.getText().toString().trim();
        String qStr = etQuantity.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(wStr) || TextUtils.isEmpty(qStr)) {
            Toast.makeText(this, "Completează toate câmpurile!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double w = Double.parseDouble(wStr);
            int q = Integer.parseInt(qStr);
            double rate = (w <= 5) ? 5 : (w <= 10) ? 10 : 20;
            double price = w * rate * q;

            Map<String, Object> order = new HashMap<>();
            order.put("userId", auth.getCurrentUser().getUid());
            order.put("receiverName", name);
            order.put("receiverAdress", address);
            order.put("weight", w);
            order.put("quantity", q);
            order.put("price", price);
            order.put("status", "în procesare");
            order.put("timestamp", Timestamp.now());

            db.collection("parcels")
                    .add(order)
                    .addOnSuccessListener(ref -> {
                        Toast.makeText(this, "Comandă salvată!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Eroare la salvare!", Toast.LENGTH_SHORT).show());

        } catch (Exception e) {
            Toast.makeText(this, "Date invalide!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
