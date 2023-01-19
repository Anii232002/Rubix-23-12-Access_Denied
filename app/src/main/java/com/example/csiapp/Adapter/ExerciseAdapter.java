package com.example.csiapp.Adapter;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.csiapp.R;
import com.example.csiapp.utils.ExerciseModel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {

    private List<ExerciseModel> list;
    private RecyclerView recyclerView;
    private Context context;
    private String LogTag = ExerciseAdapter.class.getSimpleName();

    public ExerciseAdapter(List<ExerciseModel> list, RecyclerView recyclerView, Context context) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {

        holder.setDetails(list.get(position),context);
        Log.i(LogTag,"yo");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ExerciseHolder extends RecyclerView.ViewHolder {

        GifImageView gif;
        TextView name;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.exerciseName);
            gif =  itemView.findViewById(R.id.gifImage);

        }

        protected void setDetails(ExerciseModel model,Context context) {

            // https://d205bpvrqc9yn1.cloudfront.net/0001.gif
            String s1 = model.getGifUrl().substring(4);
            s1 = "https"+s1;
            Glide.with(context).asGif().load(s1).into(gif);
            name.setText(model.getName());

        }


    }


}