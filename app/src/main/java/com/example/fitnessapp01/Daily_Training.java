package com.example.fitnessapp01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnessapp01.Database.YogaDB;
import com.example.fitnessapp01.Model.Exercise;
import com.example.fitnessapp01.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountdown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id=0,limit_time=0;

    List<Exercise> list = new ArrayList<>();

    YogaDB yogaDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        yogaDB=new YogaDB(this);



        btnStart=(Button)findViewById(R.id.btnStart);

        ex_image=(ImageView)findViewById(R.id.detail_image);

        txtCountdown=(TextView)findViewById(R.id.txtCountdown);
        txtGetReady=(TextView)findViewById(R.id.txtGetReady);
        txtTimer=(TextView)findViewById(R.id.timer);
        ex_name=(TextView)findViewById(R.id.title);

        layoutGetReady=(LinearLayout)findViewById(R.id.layout_get_ready);

        progressBar=(MaterialProgressBar)findViewById(R.id.progressBar);

        //Set Data

        progressBar.setMax(list.size());

        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnStart.getText().toString().toLowerCase().equals("start"))
                {
                    showGetReady();
                    btnStart.setText("done");
                }
                else if(btnStart.getText().toString().toLowerCase().equals("done"))
                {
                    if(yogaDB.getSettingMode()==0)
                        exercisesEasyModeCountdown.cancel();
                    else if(yogaDB.getSettingMode()==1)
                        exercisesMediumModeCountdown.cancel();
                    else if(yogaDB.getSettingMode()==2)
                        exercisesHardModeCountdown.cancel();

                    restTimeCountdown.cancel();

                    if(ex_id<list.size())
                    {
                        showRestTime() ;
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();

                }
                else
                {
                    if(yogaDB.getSettingMode()==0)
                        exercisesEasyModeCountdown.cancel();
                    else if(yogaDB.getSettingMode()==1)
                        exercisesMediumModeCountdown.cancel();
                    else if(yogaDB.getSettingMode()==2)
                        exercisesHardModeCountdown.cancel();

                    restTimeCountdown.cancel();

                    if(ex_id<list.size())
                        setExerciseInformation(ex_id);
                    else
                        showFinished();
                }
            }
        });
    }

    private void showRestTime(){

        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountdown.start();

        txtGetReady.setText("REST TIME");

    }

    private void showGetReady(){
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000,1000)
        {

            @Override
            public void onTick(long l) {
                txtCountdown.setText(""+(1-1000)/1000);
            }

            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises(){
        if(ex_id<list.size()) //list size i permban krejt ushtrimet
        {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if(yogaDB.getSettingMode()==0)
            exercisesEasyModeCountdown.start();
            else if(yogaDB.getSettingMode()==1)
                exercisesMediumModeCountdown.start();
            else if(yogaDB.getSettingMode()==2)
                exercisesHardModeCountdown.start();

            //set data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());

        }
        else
            showFinished();
    }

    private void showFinished(){
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("FINISHED!!!");
        txtCountdown.setText("CONGRATS! \nYou are done with today exercises");
        txtCountdown.setTextSize(20);

        //Save workout done to DB
        yogaDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    //Countdown
    CountDownTimer exercisesEasyModeCountdown= new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(1/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else
            {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumModeCountdown= new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(1/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else
            {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountdown= new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long l) {
            txtTimer.setText(""+(1/1000));
        }

        @Override
        public void onFinish() {
            if(ex_id<list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else
            {
                showFinished();
            }
        }
    };

    CountDownTimer restTimeCountdown= new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long l) {
            txtCountdown.setText(""+(1/1000));
        }

        @Override
        public void onFinish() {
            setExerciseInformation(ex_id);
            showExercises();
        }
    };
    private void setExerciseInformation(int id){

        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);

    }

    private void initData() {

        list.add(new Exercise(R.drawable.poza1,"EASY POSE"));
        list.add(new Exercise(R.drawable.poza2,"HALF PIGEON"));
        list.add(new Exercise(R.drawable.poza3,"CRESCENT LUNGE"));
        list.add(new Exercise(R.drawable.poza4,"COBRA POSE"));
        list.add(new Exercise(R.drawable.poza5,"LOW LUNGE"));
        list.add(new Exercise(R.drawable.poza6,"DOWNWARD FACING"));
        list.add(new Exercise(R.drawable.upward_bow,"UPWARD BOW"));
        list.add(new Exercise(R.drawable.warrior_pose,"WARRIOR POSE"));
        list.add(new Exercise(R.drawable.warrior_pose_2,"WARRIOR POSE 2"));

    }
}
