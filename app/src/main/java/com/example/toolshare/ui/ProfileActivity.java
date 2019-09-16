package com.example.toolshare.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_profile) Toolbar mToolbar;
    @BindView(R.id.my_img) CircleImageView myImg;
    @BindView(R.id.my_name) TextView myName;
    @BindView(R.id.my_email) TextView myEmail;
    @BindView(R.id.my_phone) TextView myPhone;
    @BindView(R.id.my_city) TextView myCity;

    User user;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseStorage mStorage;
    StorageReference mStorageReference;

    private static final String name =  "Name :  ";
    private static final String city =  "City :  ";
    private static final String phone = "Phone :  ";
    private static final String email = "Email :  ";
    private static final int galleryRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setTitle("Profile");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance();
        mStorageReference = mStorage.getReference();

        getUser();

    }

    private void getUser(){

        mDatabase.child("users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=  dataSnapshot.getValue(User.class);
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateUI() {
        myImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(user.getImgUrl())
                        .placeholder(R.drawable.user_icon)
                        .error(R.drawable.user_icon)
                        .into(myImg);
            }
        }, 0);

        myName.setText(String.format("%s%s", name, user.getFullName()));
        myEmail.setText(String.format("%s%s", email, user.getEmail()));
        myPhone.setText(String.format("%s%s", phone, user.getPhoneNumber()));
        myCity.setText(String.format("%s%s", city, user.getCity()));
    }

    public void onChosePhotoClick(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,galleryRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryRequestCode && resultCode==RESULT_OK){
            Uri filePath = data.getData();
            myImg.setImageURI(filePath);
            StorageReference ref = mStorageReference.child("usersImages")
                    .child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mDatabase.child("users").child(user.getId()).child("imgUrl").setValue(uri.toString());
                                }
                            });
                            //Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
