package com.example.fitnessapp;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<ActiviteSportive, RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;

    public RecyclerViewAdapter(Context mContext, @NonNull FirebaseRecyclerOptions<ActiviteSportive> options) {
        super(options);
        this.mContext = mContext;
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view=layoutInflater.inflate(R.layout.list_workout_activities,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, @NonNull ActiviteSportive exercice) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Exercise: "+exercice.getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,ExerciceActivity.class);
                intent.putExtra("desc",exercice.getDesc());
                intent.putExtra("video",exercice.getVideoUrl());
                mContext.startActivity(intent);
            }
        });

        holder.name.setText(exercice.getName());
        Picasso.with(holder.img.getContext()).load(exercice.getImageUrl()).into(holder.img);
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView name;
        ImageView img;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvWorkoutList);
            img=itemView.findViewById(R.id.ivWorkoutList);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
