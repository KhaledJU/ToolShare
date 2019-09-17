package com.example.toolshare.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddToolActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_add_tool) Toolbar mToolbar;
    @BindView(R.id.img_new_tool) ImageView mImageView;
    @BindView(R.id.tv_new_tool) TextView mTextView;
    @BindView(R.id.checkbox_new_tool) CheckBox mCheckBox;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseStorage mStorage;
    StorageReference mStorageReference;

    String imgUrl;
    boolean uploaded = false;

    private static final int galleryRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tool);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.title_add_tool));

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();
    }

    public void addToolClicked(View view) {
        if(isValidInput()) {
            String id = mDatabase.child("tools").push().getKey();
            Tool newTool = new Tool(id, mAuth.getUid(), imgUrl, mTextView.getText().toString(), mCheckBox.isChecked());
            mDatabase.child("tools").child(newTool.getId()).setValue(newTool);
            finish();
        }
    }

    public void addPictureClicked(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,galleryRequestCode);
    }

    private boolean isValidInput() {
        if(mTextView==null || mImageView==null || mCheckBox==null
                || TextUtils.isEmpty(mTextView.getText().toString()) || !uploaded) {
            Toast.makeText(this, getString(R.string.fill_all),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryRequestCode && resultCode==RESULT_OK){
            Uri filePath = data.getData();
            mImageView.setImageURI(filePath);
            StorageReference ref = mStorageReference.child("toolsImages")
                    .child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgUrl = uri.toString();
                                }

                            });
                            uploaded=true;
                            Toast.makeText(getApplicationContext(), getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), getString(R.string.failed)+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
