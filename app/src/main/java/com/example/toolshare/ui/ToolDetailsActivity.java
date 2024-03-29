package com.example.toolshare.ui;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_details);

        ButterKnife.bind(this);

        mTool = getIntent().getParcelableExtra("Tool");

        setSupportActionBar(mToolbar);
        setTitle(mTool.getName());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        

        getOwner();

    }

    private void getOwner() {
        mDatabase.child("users").child(mTool.getOwnerId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                owner=  dataSnapshot.getValue(User.class);
                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
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
        ownerEmailTv.setText(String.format("%s%s", getString(R.string.emaill), owner.getEmail()));
        ownerPhoneTv.setText(String.format("%s%s", getString(R.string.phonee), owner.getPhoneNumber()));
        ownerCityTv.setText(String.format("%s%s", getString(R.string.cityyy), owner.getCity()));
    }

    public void onCallClick(View view) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(String.format("tel:%s", owner.getPhoneNumber())));
        startActivity(dialIntent);
    }

    public void onAddWidgetClicked(View view) {
        Paper.init(this);
        Paper.book().write("imgUrl", owner.getImgUrl());
        Paper.book().write("name", owner.getFullName());
        Paper.book().write("city", owner.getCity());
        Paper.book().write("email", owner.getEmail());
        Paper.book().write("phone", owner.getPhoneNumber());

        Intent intent = new Intent(this, OwnerWidget.class);
        sendBroadcast(intent);
    }
}
