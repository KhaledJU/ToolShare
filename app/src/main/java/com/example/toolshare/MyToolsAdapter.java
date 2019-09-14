package com.example.toolshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toolshare.ui.ToolDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyToolsAdapter extends RecyclerView.Adapter<MyToolsAdapter.ViewHolder> {

    private Context context;
    private List<Tool> myToolsList;

    public MyToolsAdapter(Context context, List<Tool> myToolsList) {
        this.context = context;
        this.myToolsList = myToolsList;
    }



    @NonNull
    @Override
    public MyToolsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_tool, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyToolsAdapter.ViewHolder holder, int position) {
        final Tool mTool = myToolsList.get(position);

        holder.textView.setText(mTool.getToolName());

        holder.imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(mTool.getImgUrl())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .into(holder.imageView);
            }
        }, 0);
        holder.checkBox.setChecked(mTool.isAvailable());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()){
                    holder.checkBox.setChecked(false);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return myToolsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_mytool_item) ImageView imageView;
        @BindView(R.id.tv_mytool_item) TextView textView;
        @BindView(R.id.checkbox_mytool_item) CheckBox checkBox;
        @BindView(R.id.delete_img) ImageView deleteImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
