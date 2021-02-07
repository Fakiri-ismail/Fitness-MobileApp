package com.example.fitnessapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DashboardFragment extends Fragment {

    private ArrayList<String> daysOfTheWeek;
    private CompactCalendarView compactCalendarView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
    }

    private void initViews(View view) {

        compactCalendarView =  view.findViewById(R.id.compactcalendar_view);

        BarChart mBarChart = view.findViewById(R.id.barChart);
        daysOfTheWeek=new ArrayList<>();
        mBarChart.getDescription().setEnabled(false);
        mBarChart.setFitBars(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        final ArrayList<String> labels=getDaysOfTheWeek();


        for (int i = 0; i < 7; i++) {
            float f = (float) Math.random() * 10;
            barEntries.add(new BarEntry(i, prefs(labels.get(i))));
        }

        final BarDataSet barDataSet=new BarDataSet(barEntries,"ismail");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        final BarData barData=new BarData(barDataSet);
        barData.setBarWidth(.5F);

        mBarChart.setData(barData);

        mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        mBarChart.invalidate();
        mBarChart.animateY(1000);
        mBarChart.setFitBars(true);

    }

    private ArrayList<String> getDaysOfTheWeek(){

        Calendar sCalendar = Calendar.getInstance();
        String today = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        for (int i=0;i<7;i++){
            String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            daysOfTheWeek.add(dayLongName);
            sCalendar.add(Calendar.DAY_OF_YEAR,1);
        }

        return daysOfTheWeek;
    }

    private float prefs(String day) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String value=sharedPreferences.getString(day,"0");

        if(!value.equals("0"))  cal();

        return Float.parseFloat(value);
    }
    private void cal(){

        // Set first day of week to Monday, defaults to Monday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                
                Date date=new Date();
                if(dateClicked.after(date))
                    Toast.makeText(getActivity(), "vous n'avez pas entrainer ce jour la ", Toast.LENGTH_SHORT).show();

                Toast.makeText(getActivity(), ""+dateClicked, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) { }
        });

        Event ev1 = new Event(Color.RED, System.currentTimeMillis(), "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);

    }


}
