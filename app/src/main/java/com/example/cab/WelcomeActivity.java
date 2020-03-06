package com.example.cab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void goToDriver(View view) {
        startActivity(new Intent(this, DriverLoginRegisterActivity.class));
    }

    public void goToCustomer(View view) {
        startActivity(new Intent(this, CustomerLoginRegisterActivity.class));
    }
}
