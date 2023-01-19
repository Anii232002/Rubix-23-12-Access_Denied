package com.example.csiapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.csiapp.Model.PostModel;
import com.example.csiapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder> {

    Context context;
    List<PostModel> postModelList;
    private onClickListner mOnClickListner;


    public PostAdapter(Context context, List<PostModel> postModelList, onClickListner onClickListner) {
        this.context = context;
        this.postModelList = postModelList;
        this.mOnClickListner = onClickListner;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_layout,parent,false);
        return new MyHolder(view, mOnClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyHolder holder, int position) {

        String title = postModelList.get(position).getpTitle();
        String description = postModelList.get(position).getpDescription();
        String image = postModelList.get(position).getpImage();

        holder.postTitle.setText(title);
//        holder.postDescription.setText (description);

        holder.postImage.setImageDrawable(null);
        Glide.with(context).load(image).into(holder.postImage);

    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView postImage;
        TextView postTitle, postDescription;
        onClickListner mOnClickListner;

        public MyHolder(@NonNull View itemView, onClickListner onClickListner) {
            super(itemView);

            postImage = itemView. findViewById(R.id.postImage);
            postTitle = itemView. findViewById(R.id.postTitle);
//            postDescription = itemView.findViewById(R.id.postDescription);

            mOnClickListner = onClickListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListner.onClicked(getAdapterPosition());
        }
    }

    public interface onClickListner {
        void onClicked(int position);
    }
}
