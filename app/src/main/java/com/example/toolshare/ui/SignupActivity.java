package com.example.toolshare.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toolshare.R;
import com.example.toolshare.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @BindView(R.id.email_login) EditText editName;
    @BindView(R.id.email_login) EditText editEmail;
    @BindView(R.id.pass_login) EditText editPass;
    @BindView(R.id.email_login) EditText editPhone;
    @BindView(R.id.pass_login) EditText editCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void createUser(FirebaseUser user) {
        User newUser = new User(user.getUid(),editName.getText().toString()
                ,editEmail.getText().toString(),editPass.getText().toString()
                ,editPhone.getText().toString(),editCity.getText().toString());

        //firebase

    }

    private boolean isValidInput() {
        if(editName==null  || editEmail==null || editPass==null || editPhone==null || editCity==null
                || TextUtils.isEmpty(editName.getText().toString())
                || TextUtils.isEmpty(editEmail.getText().toString())
                || TextUtils.isEmpty(editPass.getText().toString())
                || TextUtils.isEmpty(editPhone.getText().toString())
                || TextUtils.isEmpty(editCity.getText().toString())){
            Toast.makeText(this,"Please fill all the fields!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void switchToLogin(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupClicked(View view) {
        if(isValidInput())
            createAccount(editEmail.getText().toString(), editPass.getText().toString());
    }
}
