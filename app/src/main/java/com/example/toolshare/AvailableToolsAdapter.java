package com.example.toolshare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AvailableToolsAdapter extends RecyclerView.Adapter<AvailableToolsAdapter.ViewHolder> {

    private Context context;
    private List<Tool> toolList;

    public AvailableToolsAdapter(Context context, List<Tool> toolList) {
        this.context = context;
        this.toolList = toolList;
    }

    @NonNull
    @Override
    public AvailableToolsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tool, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AvailableToolsAdapter.ViewHolder holder, final int position) {

        final Tool mTool = toolList.get(position);

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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToolDetailsActivity.class);
                intent.putExtra("Tool",toolList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return toolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.linear_tool_item) LinearLayout linearLayout;
        @BindView(R.id.img_tool_item) ImageView imageView;
        @BindView(R.id.tv_tool_item) TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
