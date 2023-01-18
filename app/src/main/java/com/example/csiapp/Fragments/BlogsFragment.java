package com.example.csiapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.csiapp.CreatePost;
import com.example.csiapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BlogsFragment extends Fragment {

    FloatingActionButton createPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_blogs, container, false);

        createPost = (FloatingActionButton) v.findViewById(R.id.createBlog);

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreatePost.class);
                startActivity(intent);
            }
        });



        return v;
    }
}