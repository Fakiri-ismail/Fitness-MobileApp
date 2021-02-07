package com.example.fitnessapp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    int origin;
    HomeFragment(){}
    HomeFragment(int origin){
        this.origin = origin;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        if (origin == 1) {
            FirebaseRecyclerOptions<ActiviteSportive> activiteSportiveList =
                    new FirebaseRecyclerOptions.Builder<ActiviteSportive>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Cardio"), ActiviteSportive.class)
                            .build();

            recyclerViewAdapter =new RecyclerViewAdapter(getContext(),activiteSportiveList);
            recyclerView.setAdapter(recyclerViewAdapter);
        }

        if (origin == 2) {
            FirebaseRecyclerOptions<ActiviteSportive> activiteSportiveList =
                    new FirebaseRecyclerOptions.Builder<ActiviteSportive>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Bodybuilding"), ActiviteSportive.class)
                            .build();

            recyclerViewAdapter =new RecyclerViewAdapter(getContext(),activiteSportiveList);
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerViewAdapter.stopListening();
    }



}
