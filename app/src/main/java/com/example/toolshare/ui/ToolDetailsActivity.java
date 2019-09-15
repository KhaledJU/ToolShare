package com.example.toolshare.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.toolshare.OwnerWidget;
import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ToolDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_detail) Toolbar mToolbar;
    @BindView(R.id.img_tool_detail) ImageView toolImg;
    @BindView(R.id.img_owner) CircleImageView ownerImg;
    @BindView(R.id.tv_owner_name) TextView ownerNameTv;
    @BindView(R.id.tv_owner_email) TextView ownerEmailTv;
    @BindView(R.id.tv_owner_phone) TextView ownerPhoneTv;
    @BindView(R.id.tv_owner_city) TextView ownerCityTv;

    Tool mTool;
    User owner;

    private static final String city = "City :  ";
    private static final String phone = "Phone :  ";
    private static final String email = "Email :  ";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_details);

        ButterKnife.bind(this);

        mTool = getIntent().getParcelableExtra("Tool");
        owner = new User("","Mohammad Natsha",
                "noob.natsha@gmail.com","basswood",
                "0592499777","Hebron");

        owner.setImgUrl("https://scontent.fjrs1-1.fna.fbcdn.net/v/t1.0-9/67233020_1200242943491050_4026239387086880768_n.jpg?_nc_cat=105&_nc_oc=AQlUy8Ts56XaO2-MXo0p9bvQRS4npIsGve6rdZ-3f0_2BS3jVCw5kiYf1-IF-2jUQ-Q&_nc_ht=scontent.fjrs1-1.fna&oh=dd7a5fb4393bbfbcbd776a679d67c850&oe=5E08000F");
        //owner.setImgUrl("C:\\Users\\asus\\Desktop\\nat.jpg");
        setSupportActionBar(mToolbar);
        setTitle(mTool.getName());

        toolImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(mTool.getImgUrl())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .into(toolImg);
            }
        }, 0);

        ownerImg.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(owner.getImgUrl())
                        .placeholder(R.drawable.user_icon)
                        .error(R.drawable.user_icon)
                        .into(ownerImg);
            }
        }, 0);

        ownerNameTv.setText(owner.getFullName());
        ownerEmailTv.setText(String.format("%s%s", email, owner.getEmail()));
        ownerPhoneTv.setText(String.format("%s%s", phone, owner.getPhoneNumber()));
        ownerCityTv.setText(String.format("%s%s", city, owner.getCity()));

    }

    public void onCallClick(View view) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(String.format("tel:%s", owner.getPhoneNumber())));
        startActivity(dialIntent);
    }

    public void onAddWidgetClicked(View view) {
        Paper.init(this);
        if(owner.getImgUrl() != null) Paper.book().write("imgUrl", owner.getImgUrl());
        Paper.book().write("name", owner.getFullName());
        Paper.book().write("city", owner.getCity());
        Paper.book().write("email", owner.getEmail());
        Paper.book().write("phone", owner.getPhoneNumber());

        Intent intent = new Intent(this, OwnerWidget.class);
        sendBroadcast(intent);
    }
}
