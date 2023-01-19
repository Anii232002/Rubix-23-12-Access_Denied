package com.example.csiapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.csiapp.R;
import com.example.csiapp.UserInfo;
import com.example.csiapp.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.PieChart;


import com.example.csiapp.ExerciseActivity;
import com.example.csiapp.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.auth.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    FragmentDashboardBinding binding;
    PieChart pieChart;
    int[] colorArr = new int[] {Color.LTGRAY,Color.BLUE,Color.CYAN,Color.DKGRAY,Color.GREEN,Color.MAGENTA,Color.RED};
    static int maxCal = 2300;
    static int currCal = 1200;
    private FrameLayout fb ;

    private TextView streakCount;
    private ImageView streakImg;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        streakCount=binding.streakNumber;
        streakImg = binding.streakImg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(getLayoutInflater());

        SharedPreferences preferences = this.getActivity().getSharedPreferences("dailyStreakPref", Context.MODE_PRIVATE);
        String lastDate = preferences.getString ("Date","");
        int streakTillNow = preferences.getInt("Streak",0);

//        streakCount.setText(streakTillNow);

        try {
            streakChecker();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pieChart = binding.piechart;

        PieDataSet pieDataSet = new PieDataSet(dataValuesPC(),"");
        pieDataSet.setColors(colorArr);

        pieChart.setDrawEntryLabels(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        binding.piechart1.setDrawEntryLabels(false);
        binding.piechart2.setDrawEntryLabels(false);
        binding.piechart2.setData(pieData);
        binding.piechart1.setData(pieData);

        binding.speedView.setMaxSpeed(maxCal);
        binding.speedView.speedTo(currCal, 1000);
        binding.speedView.setWithTremble(false);
        fb = binding.fb;
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), UserInfo.class));
            }
        });

        binding.exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ExerciseActivity.class));
            }
        });


        return binding.getRoot();
    }

    private void streakChecker() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get (Calendar. YEAR);
        int month = calendar.get (Calendar.MONTH);
        int day = calendar.get (Calendar.DAY_OF_MONTH);
        String todayString = year + "" + month + ""+ day;

        SharedPreferences preferences = this.getActivity().getSharedPreferences("dailyStreakPref", Context.MODE_PRIVATE);
        String lastDate = preferences.getString ("Date","");
        int streakTillNow = preferences.getInt("Streak",0);

        if(lastDate.equals("")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString ("Date", todayString);
            editor.putInt("Streak",0);

            // dull emoji
            streakImg.setImageResource(R.drawable.nonfire);
        }else{
            if (!lastDate.equals(todayString)) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(todayString);
                calendar.setTime(date);
                calendar.add(Calendar.DATE, -1);
                Date previousDate = calendar.getTime();
                String yesterday = sdf.format(previousDate);

                if(lastDate.equals(yesterday)){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString ("Date", todayString);
                    editor.putInt("Streak",streakTillNow+1);

                    // fire emoji
                    streakImg.setImageResource(R.drawable.fire);

                }
                else{
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString ("Date", todayString);
                    editor.putInt("Streak",0);

                    // dull emoji
                    streakImg.setImageResource(R.drawable.nonfire);

                }

                streakCount.setText(preferences.getInt("Streak",0));

            }

        }






    }

    private ArrayList<PieEntry> dataValuesPC(){
        ArrayList<PieEntry> dataVals = new ArrayList<>();
        dataVals.add(new PieEntry(15,"Linux"));
        dataVals.add(new PieEntry(34,"Html"));
        dataVals.add(new PieEntry(23,"Docker"));
        dataVals.add(new PieEntry(86,"DevOps"));
        dataVals.add(new PieEntry(26,"Kubernetes"));
        dataVals.add(new PieEntry(17,"Laravel"));
        dataVals.add(new PieEntry(63,"SQL"));
        return dataVals;
    }

}