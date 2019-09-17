package com.example.toolshare.ui;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteSigninActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @BindView(R.id.phone_signup) EditText editPhone;
    @BindView(R.id.city_signup) EditText editCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_signin);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        mAuth.signOut();
        finish();
    }
    private boolean isValidInput() {
        if(editPhone==null || editCity==null
                || TextUtils.isEmpty(editPhone.getText().toString())
                || TextUtils.isEmpty(editCity.getText().toString())){
            Toast.makeText(this,getString(R.string.fill_all),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void onFinishClicked(View view) {
        if(isValidInput()){
            User newUser = getIntent().getParcelableExtra("User");
            newUser.setPhoneNumber(editPhone.getText().toString());
            newUser.setCity(editCity.getText().toString());

            mDatabase.child("users").child(newUser.getId()).setValue(newUser);
            updateUI();
        }

    }


    private void updateUI() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
