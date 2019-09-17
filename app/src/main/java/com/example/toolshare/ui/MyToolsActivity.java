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
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.toolshare.DoInBackground_AsyncClass;
import com.example.toolshare.MyToolsAdapter;
import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.ToolsAdapter;
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

public class MyToolsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_mytools) RecyclerView mRecycle;
    @BindView(R.id.toolbar_mytools) Toolbar mToolbar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    List<Tool> toolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tools);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setTitle(R.string.my_tool);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycle.setHasFixedSize(true);

        toolList = new ArrayList<>();

        getTasks();

    }
    private void getTasks() {
        mDatabase.child("tools").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toolList = new ArrayList<>();
                for (DataSnapshot toolSnapshot: dataSnapshot.getChildren()) {
                    Tool mTool = toolSnapshot.getValue(Tool.class);
                    if(mTool.getOwnerId().equals(mAuth.getUid()))
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
        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycle.setAdapter(new MyToolsAdapter(this,toolList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tool,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this,AddToolActivity.class);
        //intent.putExtra()
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void deleteClicked(View view){
        View item = (View) view.getParent();
        item = (View) item.getParent();
        item = (View) item.getParent();
        RecyclerView cycle = (RecyclerView) item.getParent();
        int position = cycle.getChildLayoutPosition(item);
        //mDatabase.child("tools").child(toolList.get(position).getId()).removeValue();
        new DoInBackground_AsyncClass().execute(toolList.get(position).getId());

    }

    public void checkClicked(View view){
        CheckBox checkBox = (CheckBox) view;
        View item = (View) view.getParent();
        item = (View) item.getParent();
        item = (View) item.getParent();
        RecyclerView cycle = (RecyclerView) item.getParent();
        int position = cycle.getChildLayoutPosition(item);
        mDatabase.child("tools").child(toolList.get(position).getId())
                .child("available").setValue(checkBox.isChecked());
    }

}
