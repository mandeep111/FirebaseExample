package com.example.mandeep.firebaseexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    TextView textviewRegister;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonLogin = findViewById(R.id.buttonLogin);
        textviewRegister = findViewById(R.id.textviewRegister);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(this);
        textviewRegister.setOnClickListener(this);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
            finish();
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (isFieldEmpty(editTextEmail) && isFieldEmpty(editTextPassword)) {
            if (isEmailValid(editTextEmail)) {
                progressDialog.setMessage("Logging in");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            loginUser();
        }
        if (view == textviewRegister) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

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
