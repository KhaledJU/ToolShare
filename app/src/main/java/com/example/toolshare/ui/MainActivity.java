package com.example.toolshare.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.toolshare.ToolsAdapter;
import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_main) RecyclerView mRecycle;
    @BindView(R.id.toolbar_main) Toolbar mToolbar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    List<Tool> toolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycle.setHasFixedSize(true);



        getTasks();

    }

    private void getTasks() {
        mDatabase.child("tools").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toolList = new ArrayList<>();
                for (DataSnapshot toolSnapshot: dataSnapshot.getChildren()) {
                    Tool mTool = toolSnapshot.getValue(Tool.class);
                    if(mTool.isAvailable())
                        toolList.add(mTool);
                }
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUI() {
        mRecycle.setAdapter(new ToolsAdapter(this,toolList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.item_profile:
                intent = new Intent(this,ProfileActivity.class);
                //intent.putExtra()
                startActivity(intent);
                return true;
            case R.id.item_mytools:
                intent = new Intent(this,MyToolsActivity.class);
                //intent.putExtra()
                startActivity(intent);
                return true;
            case R.id.item_signout:
                mAuth.signOut();
                intent=new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
