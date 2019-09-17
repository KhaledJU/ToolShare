package com.example.toolshare;

import android.os.AsyncTask;

import com.google.firebase.database.FirebaseDatabase;

public class DoInBackground_AsyncClass extends AsyncTask<String,Void,Void> {
    @Override
    protected Void doInBackground(String... strings) {
        FirebaseDatabase.getInstance().getReference().child("tools").child(strings[0]).removeValue();
        return null;
    }

}
