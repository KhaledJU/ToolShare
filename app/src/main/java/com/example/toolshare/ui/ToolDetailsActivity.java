package com.example.toolshare.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.toolshare.R;
import com.example.toolshare.Tool;
import com.example.toolshare.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_details);

        ButterKnife.bind(this);

        mTool = getIntent().getParcelableExtra("Tool");
        owner = new User("","Mohammad Natsha",
                "noob.natsha@gmail.com","basswood",
                "0592499777","Hebron");

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
        ownerEmailTv.setText(owner.getEmail());
        ownerPhoneTv.setText(owner.getPhoneNumber());
        ownerCityTv.setText(owner.getCity());

    }

    public void onCallClick(View view) {
    }

    public void onAddWidgetClicked(View view) {
    }
}
