package com.example.toolshare.ui;

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

import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.User;
import com.squareup.picasso.Picasso;

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

        user = new User("","Mohammad Natsha",
                "noob.natsha@gmail.com","basswood",
                "0592499777","Hebron");

        user.setImgUrl("https://scontent.fjrs1-1.fna.fbcdn.net/v/t1.0-9/67233020_1200242943491050_4026239387086880768_n.jpg?_nc_cat=105&_nc_oc=AQlUy8Ts56XaO2-MXo0p9bvQRS4npIsGve6rdZ-3f0_2BS3jVCw5kiYf1-IF-2jUQ-Q&_nc_ht=scontent.fjrs1-1.fna&oh=dd7a5fb4393bbfbcbd776a679d67c850&oe=5E08000F");
        //user.setImgUrl("C:\\Users\\asus\\Desktop\\nat.jpg");
        setSupportActionBar(mToolbar);
        setTitle("Profile");

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
            Uri uri=data.getData();
            myImg.setImageURI(uri);
        }
    }
}
