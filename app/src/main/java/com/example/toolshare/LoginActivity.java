package com.example.toolshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void switchToSignup(View view) {
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
        finish();
    }

}
