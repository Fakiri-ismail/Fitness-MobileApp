package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class ActivityIntermediaire extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediaire);

        CardView cardViewInter = findViewById(R.id.cardViewInter);
        CardView cardViewInter2 = findViewById(R.id.cardViewInter2);

        TextView text1 = findViewById(R.id.tvWorkoutList);
        TextView text2 = findViewById(R.id.tvWorkoutList2);

        text1.setText("CARDIO");
        text2.setText("BODYBUILDIG");

        cardViewInter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("origin",1);
                startActivity(intent );
            }
        });

        cardViewInter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("origin",2);
                startActivity(intent );
            }
        });

    }

}
