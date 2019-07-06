package com.example.fitnessapp01.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp01.Interface.ItemClickListener;
import com.example.fitnessapp01.ListExercises;
import com.example.fitnessapp01.Model.Exercise;
import com.example.fitnessapp01.R;
import com.example.fitnessapp01.ViewExercise;

import java.util.List;

class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public ImageView image;
    public TextView text;

    private ItemClickListener itemClickListener;


    public RecycleViewHolder(View itemView) {
        super(itemView);
        image=(ImageView)itemView.findViewById(R.id.ex_img);
        text=(TextView)itemView.findViewById(R.id.ex_name);

        itemView.setOnClickListener(this);
    }



    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());

    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecycleViewHolder>
{

    private List<Exercise> exerciseList;
    private Context context;

    public RecyclerViewAdapter(List<Exercise> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_exercise, parent,false);

        return new RecycleViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

        holder.image.setImageResource(exerciseList.get(position).getImage_id());
        holder.text.setText(exerciseList.get(position).getName());

        holder.setItemClickListener(new ItemClickListener()
        {
            @Override
            public void onClick(View view, int position) {

                //Call to new activity
               Intent intent = new Intent(context, ViewExercise.class);
               intent.putExtra("image_id", exerciseList.get(position).getImage_id());
               intent.putExtra("name", exerciseList.get(position).getName());
               context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return exerciseList.size();
    }
}
