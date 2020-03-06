package com.example.cab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class CustomerLoginRegisterActivity extends AppCompatActivity {

    private Button btnCustomerLogin;
    private Button btnCustomerRegister;
    private TextView tvCustomerRegister;
    private TextView tvCustomerStatus;
    private EditText etCustomerEmail;
    private EditText etCutomerPwd;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        btnCustomerLogin = (Button) findViewById(R.id.btn_customer_login);
        btnCustomerRegister = (Button) findViewById(R.id.btn_customer_register);
        tvCustomerRegister = (TextView) findViewById(R.id.tv_customer_register);
        tvCustomerStatus = (TextView) findViewById(R.id.tv_customer_status);
        etCustomerEmail = (EditText) findViewById(R.id.et_customer_email);
        etCutomerPwd = (EditText) findViewById(R.id.et_customer_pwd);

        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Customer registration");
        progressBar.setMessage("Please wait, we are registering your data...");

        btnCustomerRegister.setVisibility(View.INVISIBLE);
        tvCustomerStatus.setText("customer login");
        tvCustomerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCustomerRegister.setVisibility(View.INVISIBLE);
                btnCustomerLogin.setVisibility(View.INVISIBLE);
                btnCustomerRegister.setVisibility(View.VISIBLE);
                tvCustomerStatus.setText("customer register");


            }
        });


    }

    public void clickRegisterAndLogin(View view) {
        String customerEmail = etCustomerEmail.getText().toString();
        String customerPwd = etCutomerPwd.getText().toString();
        if (TextUtils.isEmpty(customerEmail)) {
            etCustomerEmail.setError("please enter Email");
        } else if (TextUtils.isEmpty(customerPwd)) {
            etCutomerPwd.setError("please enter password");
        } else {

            progressBar.setTitle(((Button) view).getText().toString());
            progressBar.setMessage("Please wait...");
            progressBar.setCancelable(false);
            progressBar.show();
            String buttonText = ((Button) view).getText().toString().trim().toLowerCase();
            if (buttonText.equals("login")) {

                firebaseLogin(customerEmail, customerPwd);
            } else {
                firebaseRegisteration(customerEmail, customerPwd);
            }


        }


    }

    private void firebaseLogin(String customerEmail, String customerPwd) {
        System.out.println("CustomerLoginRegisterActivity.firebaseLogin");
        firebaseAuth.signInWithEmailAndPassword(customerEmail, customerPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                try {
                    task.getResult();
                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(CustomerLoginRegisterActivity.this, e.getMessage().split(": ")[1], Toast.LENGTH_LONG).show();
                }
                System.out.println("CustomerLoginRegisterActivity.onComplete" + task.isSuccessful());
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerLoginRegisterActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerLoginRegisterActivity.this, "login unsuccessfully", Toast.LENGTH_SHORT).show();
                }
                progressBar.dismiss();
            }
        });

    }

    private void firebaseRegisteration(String customerEmail, String customerPwd) {
        System.out.println("CustomerLoginRegisterActivity.firebaseRegisteration");
        firebaseAuth.createUserWithEmailAndPassword(customerEmail, customerPwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        System.out.printf("resultresultresultresult", task.getResult());

//                        task.getResult();
                        try {
                            task.getResult();
                        } catch (RuntimeExecutionException e) {
                            e.printStackTrace();
                            String newMessage = e.getMessage().split(": ")[1];

//                            System.out.print(e.getMessage()-e.getMessage().split(" ")[0]);
                            Toast.makeText(CustomerLoginRegisterActivity.this, newMessage, Toast.LENGTH_LONG).show();
                        }
                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerLoginRegisterActivity.this, "register successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CustomerLoginRegisterActivity.this, "register unsuccessfully, please try again", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.dismiss();
                    }
                });
    }

}
