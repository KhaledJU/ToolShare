package com.example.toolshare.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.toolshare.R;

public class MyToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tools);
    }

    public void deleteClicked(View view){

    }

    public void checkClicked(View view){
        CheckBox checkBox = (CheckBox) view;
        View item = (View) view.getParent();
        RecyclerView cycle = (RecyclerView) item.getParent();
        int position = cycle.getChildLayoutPosition(item);
        if(checkBox.isChecked())
            updateTool(position,true);
        else
            updateTool(position,false);
    }

    private void updateTool(int position, boolean b) {
    }
}
