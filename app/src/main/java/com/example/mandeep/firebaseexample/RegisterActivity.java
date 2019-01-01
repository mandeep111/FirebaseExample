package com.example.mandeep.firebaseexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailText, passwordText;
    Button buttonRegister;
    TextView textviewLogin;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        buttonRegister = findViewById(R.id.buttonRegiser);
        textviewLogin = findViewById(R.id.textviewLogin);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(this);
        textviewLogin.setOnClickListener(this);
    }

    private void addUser() {
        String emailValue = emailText.getText().toString().trim();
        String passwordValue = passwordText.getText().toString().trim();
                progressDialog.setMessage("Adding User...");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(emailValue, passwordValue).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }
                });
            }


    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            if (isFieldEmpty(emailText) && isFieldEmpty(passwordText)) {
                if (isEmailValid(emailText)) {
                    addUser();
                }
            }
        }
        if (view == textviewLogin) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }


    public Boolean isFieldEmpty(EditText view) {
        if (view.getText().toString().length() > 0) {
            return true;
        } else {
            view.setError("Field Required");
            return false;
        }
    }

    public Boolean isEmailValid(EditText view) {
        String value = view.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            return true;
        } else {
            view.setError("Invalid email");
            return false;
        }

    }
}
