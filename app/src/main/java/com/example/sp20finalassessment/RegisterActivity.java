package com.example.sp20finalassessment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;;import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText userName, userPassword, userEmail, confirmPass;
    Button registerBtn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /* TODO Part 2
        * Implement registration. If the imageView is clicked, set it to an image from the gallery
        * and store the image as a Uri instance variable (also change the imageView's image to this
        * Uri. If the create new user button is pressed, call createUser using the email and password
        * from the edittexts. Remember that it's email2 and password2 now!
        */

        userName = findViewById(R.id.name);
        userEmail = findViewById(R.id.email2);
        userPassword = findViewById(R.id.password2);
        confirmPass = findViewById(R.id.confirmpassword);

        registerBtn = findViewById(R.id.createnewuser);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, TabActivity.class));
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                createUser(email, password);
            }
        });

    }

    private void createUser(final String email, final String password) {
        /* TODO Part 2.1
         * This part's long, so listen up!
         * Create a user, and if it fails, display a Toast.
         *
         * If it works, we're going to add their image to the database. To do this, we will need a
         * unique user id to identify the user (push isn't the best answer here. Do some Googling!)
         *
         * Now, if THAT works (storing the image), set the name and photo uri of the user (hint: you
         * want to update a firebase user's profile.)
         *
         * Finally, if updating the user profile works, go to the TabbedActivity
         */
        String name = userName.getText().toString().trim();
        String confirmPassword = confirmPass.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            Toast toast = Toast.makeText(RegisterActivity.this, "Name is requied", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(email)) {
            Toast toast = Toast.makeText(RegisterActivity.this, "Email is requied", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast toast = Toast.makeText(RegisterActivity.this, "Password is requied", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(!confirmPassword.equals(password)) {
            Toast toast = Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(password.length() < 6) {
            Toast toast = Toast.makeText(RegisterActivity.this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Succesfully Created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, TabActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Error Creating User! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
