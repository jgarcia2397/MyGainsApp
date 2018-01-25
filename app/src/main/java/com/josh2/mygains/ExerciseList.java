package com.josh2.mygains;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

public class ExerciseList extends ArrayAdapter<Exercise> {
    private Activity context;
    List<Exercise> exercises;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;

    //Boolean[] checkBoxes;

    public ExerciseList(Activity context, List<Exercise> exercises) {
        super(context, R.layout.layout_exercise_list, exercises);
        this.context = context;
        this.exercises = exercises;
    }

    public void updateExerciseList(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_exercise_list, null, true);

        TextView nameTextView = listViewItem.findViewById(R.id.nameTextView);
        TextView setsTextView = listViewItem.findViewById(R.id.setsTextView);
        TextView repsTextView = listViewItem.findViewById(R.id.repsTextView);
        TextView restTextView = listViewItem.findViewById(R.id.restTextView);

        checkBox1 = listViewItem.findViewById(R.id.checkBox1);
        checkBox2 = listViewItem.findViewById(R.id.checkBox2);
        checkBox3 = listViewItem.findViewById(R.id.checkBox3);
        checkBox4 = listViewItem.findViewById(R.id.checkBox4);
        checkBox5 = listViewItem.findViewById(R.id.checkBox5);
        checkBox6 = listViewItem.findViewById(R.id.checkBox6);

        Exercise exercise = exercises.get(position);

        if(exercise != null && exercise.getName() != null) {
            nameTextView.setText(exercise.getName());
        }
        if(exercise != null) {
            setsTextView.setText("Sets: " + String.valueOf(exercise.getSets()));
        }
        if(exercise != null) {
            repsTextView.setText("Reps: " + String.valueOf(exercise.getReps()));
        }
        if(exercise != null) {
            restTextView.setText("Rest time: " + String.valueOf(exercise.getRestTime()) + " mins");
        }

        try {
            int sets = Integer.parseInt(String.valueOf(exercise.getSets()));
            showCheckBoxes(sets);
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }

        //checkBoxes = new Boolean[6];
        //List<Boolean> checkBoxStates = clickCheckBoxes();
        //exercise.setCheckBoxes(checkBoxStates);

        return listViewItem;
    }

    public void showCheckBoxes(int sets) {
        if(sets == 1) {
            checkBox1.setVisibility(View.VISIBLE);
        } else if(sets == 2) {
            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
        } else if(sets == 3) {
            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
        } else if(sets == 4) {
            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
        } else if(sets == 5) {
            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
        } else if(sets == 6) {
            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
            checkBox6.setVisibility(View.VISIBLE);
        }
    }

    /*public List<Boolean> clickCheckBoxes() {
        checkBoxes[0] = false;
        checkBoxes[1] = false;
        checkBoxes[2] = false;
        checkBoxes[3] = false;
        checkBoxes[4] = false;
        checkBoxes[5] = false;

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxes[0] = checkBox1.isChecked();
                //Log.i("check state 1", String.valueOf(checkBoxes[0]));
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxes[1] = checkBox2.isChecked();
                //Log.i("check state 2", String.valueOf(checkBoxes[1]));
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxes[2] = checkBox3.isChecked();
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxes[3] = checkBox4.isChecked();
            }
        });

        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxes[4] = checkBox5.isChecked();
            }
        });

        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBoxes[5] = checkBox6.isChecked();
            }
        });

        //List<Boolean> listCheckBoxes = Arrays.asList(checkBoxes);
        return Arrays.asList(checkBoxes);
    }*/
}
