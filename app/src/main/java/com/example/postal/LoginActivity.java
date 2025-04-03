package com.example.postal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private MaterialButton btnLogin, btnRegister;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(v -> performLogin());

        btnRegister.setOnClickListener(v -> {
            // Navigăm direct către RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Completează email și parolă!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = auth.getCurrentUser().getUid();

                    db.collection("users").document(uid).get().addOnSuccessListener(doc -> {
                        if (doc.exists()) {
                            String role = doc.getString("role");

                            if ("admin".equals(role)) {
                                startActivity(new Intent(this, AdminActivity.class));
                            } else if ("client".equals(role)) {
                                startActivity(new Intent(this, ClientActivity.class));
                            } else {
                                Toast.makeText(this, "Rol necunoscut!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Utilizatorul nu există în baza de date!", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Eroare la autentificare: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
    }
}
