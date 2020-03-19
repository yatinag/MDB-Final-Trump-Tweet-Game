package com.example.sp20finalassessment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button loginBtn, registerBtn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* TODO Part 1
         * Implement login. When the login button is pressed, attempt to login the user, and if
         * that is successful, go to the TabbedActivity. If the register button is pressed, go to
         * the RegisterActivity. Check the layout files for the IDs of the views used in this part
         */
        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        registerBtn = findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)) {
                    Toast toast = Toast.makeText(LoginActivity.this, "Email is requied", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast toast = Toast.makeText(LoginActivity.this, "Password is requied", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(password.length() < 6) {
                    Toast toast = Toast.makeText(LoginActivity.this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, TabActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error Signing In User! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
}
