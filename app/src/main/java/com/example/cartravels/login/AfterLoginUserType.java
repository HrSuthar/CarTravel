package com.example.cartravels.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cartravels.HomePage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AfterLoginUserType extends AppCompatActivity {
    DatabaseReference db;
    SharedPreferences pref;
    String valueStr;
    boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference().child("Login Credential");
        pref = getSharedPreferences("userPreferences",MODE_PRIVATE);
        final String email = getIntent().getStringExtra("Email");
        final String pass = getIntent().getStringExtra("Password");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assert email != null;
                valueStr = Objects.requireNonNull(snapshot.child(email.replace(".","_")).getValue()).toString();
                state = valueStr.equals((pass + "#" + "Customer"));
                login_Customer();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void login_Customer(){
        SharedPreferences.Editor editor = pref.edit();
        if(state)
            editor.putString("UserType", "Customer");
        else
            editor.putString("UserType", "Travel Agent");
        editor.putBoolean("LoggedIn", true);
        editor.apply();
        startActivity(new Intent(AfterLoginUserType.this, HomePage.class));
        Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
        finish();
    }
}
