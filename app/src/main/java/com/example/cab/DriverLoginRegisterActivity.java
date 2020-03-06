package com.example.cab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegisterActivity extends AppCompatActivity {

    private Button btnDriverLogin;
    private Button btnDriverRegister;
    private TextView tvDriverStatus;
    private TextView tvDriverRegister;
    private FirebaseAuth firebaseAuth;
    private EditText etDriverEmail;
    private EditText etDriverPwd;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);

        firebaseAuth = FirebaseAuth.getInstance();
        btnDriverLogin = (Button) findViewById(R.id.btn_driver_login);
        btnDriverRegister = (Button) findViewById(R.id.btn_driver_register);
        tvDriverStatus = (TextView) findViewById(R.id.tv_driver_status);
        tvDriverRegister = (TextView) findViewById(R.id.tv_driver_register);
        etDriverEmail = (EditText) findViewById(R.id.et_driver_email);
        etDriverPwd = (EditText) findViewById(R.id.et_driver_pwd);

        btnDriverRegister.setVisibility(View.INVISIBLE);
        tvDriverStatus.setText("driver login");
        tvDriverRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDriverLogin.setVisibility(View.INVISIBLE);
                tvDriverRegister.setVisibility(View.INVISIBLE);
                btnDriverRegister.setVisibility(View.VISIBLE);
                tvDriverStatus.setText("driver register");
            }
        });
        progressDialog = new ProgressDialog(this);

    }


    public void driverLoginAndRegister(View view) {
        String driverEmail = etDriverEmail.getText().toString().trim();
        String driverPwd = etDriverPwd.getText().toString().trim();
        String buttonText = ((Button) view).getText().toString().trim().toLowerCase();


        if (driverEmail.equals("")) {
            etDriverEmail.setError("please enter Email");

        } else if (driverPwd.equals("")) {
            etDriverPwd.setError("please enter Password");
        } else {
            progressDialog.setTitle(tvDriverStatus.getText().toString());
            progressDialog.setMessage("Please waiting...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            if (buttonText.equals("login")) {
                firebaseLogin(driverEmail, driverPwd);
            } else {
                firebaseRegister(driverEmail, driverPwd);
            }
        }
    }

    private void firebaseLogin(String driverEmail, String driverPwd) {
        firebaseAuth.signInWithEmailAndPassword(driverEmail, driverPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(DriverLoginRegisterActivity.this, DriverMapsActivity.class));

                    Toast.makeText(DriverLoginRegisterActivity.this, "login successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DriverLoginRegisterActivity.this, "login unsuccessfully", Toast.LENGTH_SHORT).show();
                }
                try {
                    task.getResult();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DriverLoginRegisterActivity.this, e.getMessage().split(": ")[1], Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void firebaseRegister(String driverEmail, String driverPwd) {
        firebaseAuth.createUserWithEmailAndPassword(driverEmail, driverPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(DriverLoginRegisterActivity.this, "register successfully ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DriverLoginRegisterActivity.this, DriverMapsActivity.class));
                } else {
                    Toast.makeText(DriverLoginRegisterActivity.this, "register unsuccessfully", Toast.LENGTH_SHORT).show();
                }
                try {
                    task.getResult();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DriverLoginRegisterActivity.this, e.getMessage().split(": ")[1], Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

        });
    }
}
