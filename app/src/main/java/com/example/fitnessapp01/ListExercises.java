package com.example.fitnessapp01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitnessapp01.Adapter.RecyclerViewAdapter;
import com.example.fitnessapp01.Model.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ListExercises extends AppCompatActivity {

    List<Exercise> exerciseList= new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);

        initData();

        recyclerView = (RecyclerView)findViewById(R.id.list_ex);
        adapter = new RecyclerViewAdapter(exerciseList, getBaseContext());
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
         exerciseList.add(new Exercise(R.drawable.poza1,"EASY POSE"));
         exerciseList.add(new Exercise(R.drawable.poza2,"HALF PIGEON"));
         exerciseList.add(new Exercise(R.drawable.poza3,"CRESCENT LUNGE"));
         exerciseList.add(new Exercise(R.drawable.poza4,"COBRA POSE"));
         exerciseList.add(new Exercise(R.drawable.poza5,"LOW LUNGE"));
         exerciseList.add(new Exercise(R.drawable.poza6,"DOWNWARD FACING"));
         exerciseList.add(new Exercise(R.drawable.upward_bow,"UPWARD BOW"));
         exerciseList.add(new Exercise(R.drawable.warrior_pose,"WARRIOR POSE"));
         exerciseList.add(new Exercise(R.drawable.warrior_pose_2,"WARRIOR POSE 2"));

    }


}
