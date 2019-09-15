package com.example.toolshare.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.toolshare.MyToolsAdapter;
import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.ToolsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyToolsActivity extends AppCompatActivity {

    @BindView(R.id.recycler_mytools) RecyclerView mRecycle;
    @BindView(R.id.toolbar_mytools) Toolbar mToolbar;

    List<Tool> toolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tools);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setTitle("My Tools");

        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycle.setHasFixedSize(true);

        toolList = new ArrayList<>();

        toolList.add(new Tool("","https://www.constructiontoolwarehouse.com/images/homeFeature/682-600354420_L.jpg", "first tool",true));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "second tool",false));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "third tool",true));
        toolList.add(new Tool("","0", "forth tool",true));
        toolList.add(new Tool("","https://www.constructiontoolwarehouse.com/images/homeFeature/682-600354420_L.jpg", "first tool",false));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "second tool",true));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "third tool",false));
        toolList.add(new Tool("","0", "forth tool",true));

        mRecycle.setAdapter(new MyToolsAdapter(this,toolList));



    }

    public void deleteClicked(View view){
        View item = (View) view.getParent();
        item = (View) item.getParent();
        item = (View) item.getParent();
        RecyclerView cycle = (RecyclerView) item.getParent();
        int position = cycle.getChildLayoutPosition(item);
    }

    public void checkClicked(View view){
        CheckBox checkBox = (CheckBox) view;
        View item = (View) view.getParent();
        item = (View) item.getParent();
        item = (View) item.getParent();
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
