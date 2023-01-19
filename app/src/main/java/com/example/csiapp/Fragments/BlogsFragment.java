package com.example.csiapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.csiapp.Adapter.PostAdapter;
import com.example.csiapp.CreatePost;
import com.example.csiapp.Model.PostModel;
import com.example.csiapp.PostContent;
import com.example.csiapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlogsFragment extends Fragment implements PostAdapter.onClickListner {

    FloatingActionButton createPost;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<PostModel> postModelList;
    FirebaseAuth auth;
    ProgressDialog pd;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =  inflater.inflate(R.layout.fragment_blogs, container, false);

        progressBar = v.findViewById(R.id.progressBar_blog);

//        Intent intent = new Intent(getActivity(), PostContent.class);
//        startActivity(intent);

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

    private void loadPosts() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {
                postModelList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    PostModel postModel = ds.getValue(PostModel.class);
                    postModelList.add(postModel);
                    postAdapter = new PostAdapter(getContext(),postModelList,BlogsFragment.this::onClicked );
                    recyclerView.setAdapter(postAdapter);
                    postAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled (@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        // this will load from end and will show latest post first
        recyclerView.setLayoutManager(layoutManager);

        postModelList = new ArrayList<>();
        loadPosts();

    }

    @Override
    public void onClicked(int position) {

        Intent intent = new Intent(getActivity(),PostContent.class);
        intent.putExtra("title",postModelList.get(position).getpTitle());
        intent.putExtra("desc",postModelList.get(position).getpDescription());
        intent.putExtra("img",postModelList.get(position).getpImage());
        startActivity(intent);
    }
}