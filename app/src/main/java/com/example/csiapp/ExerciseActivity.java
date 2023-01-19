package com.example.csiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.csiapp.Adapter.ExerciseAdapter;
import com.example.csiapp.databinding.ActivityExerciseBinding;
import com.example.csiapp.utils.ExerciseModel;
import com.example.csiapp.utils.ExerciseQueryUtils;

import java.net.URL;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    private String LogTag = ActivityExerciseBinding.class.getSimpleName();
    ActivityExerciseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ExerciseAsyncTask task = new ExerciseAsyncTask();
        task.execute();

        binding.exerciseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), ExerciseListActivity.class));
            }
        });

    }

    protected void updateUi(ArrayList<ExerciseModel> list){

       /* RepoResAdapter flightAdapter = new RepoResAdapter(list, binding.recyclerViewExercise, getApplicationContext());
        binding.recyclerView.setAdapter(flightAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));*/

        ExerciseAdapter adapter = new ExerciseAdapter(list,binding.recyclerViewExercise,getApplicationContext());
        binding.recyclerViewExercise.setAdapter(adapter);
        binding.recyclerViewExercise.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        Log.i(LogTag,"List:  "+list.toString());
        Log.i(LogTag,"List:  "+list.get(5).getGifUrl());

    }

    private class ExerciseAsyncTask extends AsyncTask<URL, Void, ArrayList<ExerciseModel>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<ExerciseModel> doInBackground(URL... urls) {
            ArrayList<ExerciseModel> event = ExerciseQueryUtils.fetchInfo("https://exercisedb.p.rapidapi.com/exercises");            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<ExerciseModel> event) {

            if (event == null) {
                // Log.i("AllFlights", "NULL EVENT");
                //  binding.emptyTextView.setVisibility(View.VISIBLE);
                return;
            }

            updateUi(event);

        }

    }

}