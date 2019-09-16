package com.example.toolshare.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toolshare.R;
import com.example.toolshare.Tool;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToolActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_add_tool) Toolbar mToolbar;
    @BindView(R.id.img_new_tool) ImageView mImageView;
    @BindView(R.id.tv_new_tool) TextView mTextView;
    @BindView(R.id.checkbox_new_tool) CheckBox mCheckBox;

    private static final int galleryRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tool);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle("Add Tool");
    }

    public void addToolClicked(View view) {
        Tool newTool = new Tool("","",mTextView.getText().toString(),mCheckBox.isChecked());
    }

    public void addPictureClicked(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,galleryRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryRequestCode && resultCode==RESULT_OK){
            Uri uri=data.getData();
            mImageView.setImageURI(uri);
        }
    }
}
