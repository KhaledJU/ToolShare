package com.example.toolshare.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.toolshare.AvailableToolsAdapter;
import com.example.toolshare.R;
import com.example.toolshare.Tool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_main) RecyclerView mRecycle;
    @BindView(R.id.toolbar_main) Toolbar mToolbar;

    List<Tool> toolList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);


        mRecycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycle.setHasFixedSize(true);

        toolList = new ArrayList<>();

        toolList.add(new Tool("","https://www.constructiontoolwarehouse.com/images/homeFeature/682-600354420_L.jpg", "first tool",true));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "second tool",true));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "third tool",true));
        toolList.add(new Tool("","0", "forth tool",true));
        toolList.add(new Tool("","https://www.constructiontoolwarehouse.com/images/homeFeature/682-600354420_L.jpg", "first tool",true));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "second tool",true));
        toolList.add(new Tool("","http://i.imgur.com/DvpvklR.png", "third tool",true));
        toolList.add(new Tool("","0", "forth tool",true));

        mRecycle.setAdapter(new AvailableToolsAdapter(this,toolList));

    }
}
