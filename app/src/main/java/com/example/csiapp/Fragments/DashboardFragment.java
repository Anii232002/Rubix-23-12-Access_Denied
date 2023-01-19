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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    FragmentDashboardBinding binding;
    PieChart pieChart;
    int[] colorArr = new int[] {Color.LTGRAY,Color.BLUE,Color.CYAN,Color.DKGRAY,Color.GREEN,Color.MAGENTA,Color.RED};
    static int maxCal = 2300;
    static int currCal = 1200;
    private FloatingActionButton fb ;

    private TextView streakNumber;
    private ImageView streakImg;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        streakNumber=binding.streakNumber;
        streakImg=binding.streakImg;

        int streak = updateStreak();

        streakNumber.setText(String.valueOf(streak));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(getLayoutInflater());



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

    private int updateStreak() {

        String today=null;
        String dateOnSp;
        String expectedYesterday = null;
        int storedStreak;
        LocalDate t = null,y=null;

        SharedPreferences preferences = this.getActivity().getSharedPreferences("StreakPref", Context.MODE_PRIVATE);
        dateOnSp = preferences.getString("date","");
        storedStreak = preferences.getInt("streak",0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            t = LocalDate.now();
            today=t.toString();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            y = t.minusDays(1);
            expectedYesterday=y.toString();
        }

        // already updated today
        if(dateOnSp.equals(today)){
            return storedStreak;
        }

        // visiting for the first time
        if(dateOnSp.equals("")){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("date",today);
            editor.putInt("streak",0);
            editor.apply();
            streakImg.setImageResource(R.drawable.nonfire);
            return 0;
        }

        // came yesterday
        if(dateOnSp.equals(expectedYesterday)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("date",today);
            editor.putInt("streak",storedStreak+1);
            editor.apply();
            streakImg.setImageResource(R.drawable.fire);
            return storedStreak+1;
        }

        //visiting app after long time
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("date",today);
        editor.putInt("streak",0);
        editor.apply();
        streakImg.setImageResource(R.drawable.nonfire);
        return 0;

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