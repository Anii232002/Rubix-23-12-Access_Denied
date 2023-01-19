package com.example.csiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.csiapp.Adapter.ExerciseAdapter;
import com.example.csiapp.databinding.ActivityExerciseListActvitiyBinding;
import com.example.csiapp.utils.ExerciseModel;
import com.example.csiapp.utils.ExerciseQueryUtils;

import java.net.URL;
import java.util.ArrayList;

public class ExerciseListActivity extends AppCompatActivity {


    ActivityExerciseListActvitiyBinding binding;
    String[] bodyParts = {"back","cardio","chest","lower arms","lower legs","shoulders","waist"};
    private String LogTag = ExerciseListActivity.class.getSimpleName();
    private String BASEURL = "https://exercisedb.p.rapidapi.com/exercises/bodyPart";
    private String BASEURLTemp = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExerciseListActvitiyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, bodyParts);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBodyParts.setAdapter(ad);
        binding.spinnerBodyParts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                Log.i(LogTag, "Menu Position: " + position);
                if (item != null) {
                    BASEURL = BASEURLTemp;
                    BASEURL += item.toString();
                    ExerciseAsyncTask task = new ExerciseAsyncTask();
                    task.execute();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

    }

    protected void updateUi(ArrayList<ExerciseModel> list){

        ExerciseAdapter adapter = new ExerciseAdapter(list,binding.recyclerViewExerciseList,getApplicationContext());
        binding.recyclerViewExerciseList.setAdapter(adapter);
        binding.recyclerViewExerciseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        Log.i(LogTag,"List:  "+list.toString());
        Log.i(LogTag,"List:  "+list.get(5).getGifUrl());

    }

    private class ExerciseAsyncTask extends AsyncTask<URL, Void, ArrayList<ExerciseModel>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<ExerciseModel> doInBackground(URL... urls) {
            ArrayList<ExerciseModel> event = ExerciseQueryUtils.fetchInfo(BASEURL);            //also we can use  urls[0]
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