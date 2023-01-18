package com.example.csiapp.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.csiapp.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    FragmentDashboardBinding binding;
    PieChart pieChart;
    int[] colorArr = new int[] {Color.LTGRAY,Color.BLUE,Color.CYAN,Color.DKGRAY,Color.GREEN,Color.MAGENTA,Color.RED};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(getLayoutInflater());

        pieChart = binding.piechart;

        PieDataSet pieDataSet = new PieDataSet(dataValuesPC(),"");
        pieDataSet.setColors(colorArr);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        return binding.getRoot();
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