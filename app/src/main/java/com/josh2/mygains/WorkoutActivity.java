package com.josh2.mygains;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    String day;
    TextView workoutDayTV;

    EditText exerciseNameET;
    EditText setsET;
    EditText repsET;
    EditText restTimeET;
    Button addButton;

    ExerciseList exerciseAdapter;
    ArrayList<Exercise> exercises;
    ListView exerciseList;

    //List<Boolean> checkBoxes;

    DatabaseReference dbExercises;

    Button updateButton;
    Button deleteButton;
    Button startRestButton;

    TextView timerTextView;
    CountDownTimer timer;
    int restTimeTimer;
    Boolean counterActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        dbExercises = FirebaseDatabase.getInstance().getReference("exercises");

        Intent intent = getIntent();
        day = intent.getStringExtra("day");

        exerciseNameET = findViewById(R.id.exerciseNameET);
        setsET = findViewById(R.id.setsET);
        repsET = findViewById(R.id.repsET);
        restTimeET = findViewById(R.id.restTimeET);
        addButton = findViewById(R.id.addButton);
        workoutDayTV = findViewById(R.id.workoutDayTV);
        exerciseList = findViewById(R.id.exerciseList);

        exercises = new ArrayList<>();
        //checkBoxes = new ArrayList<>();

        exerciseAdapter = new ExerciseList(WorkoutActivity.this, exercises);
        exerciseList.setAdapter(exerciseAdapter);

        workoutDayTV.setText(day + " Workout Schedule");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExercise();
            }
        });

        exerciseList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Exercise exercise = exercises.get(position);
                String restTime = String.valueOf(exercise.getRestTime());
                showExerciseDialog(exercise, exercise.getId(), exercise.getName(), restTime);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbExercises.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                exercises.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Exercise exercise = postSnapshot.getValue(Exercise.class);
                    if(day.equals(exercise.getDay())) {
                        exercises.add(exercise);
                    }
                }
                exerciseAdapter.updateExerciseList(exercises);
                exerciseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addExercise() {
        /*checkBoxes = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            checkBoxes.add(false);
        }*/
        String name = exerciseNameET.getText().toString();
        try {
            int sets = Integer.parseInt(setsET.getText().toString());
            int reps = Integer.parseInt(repsET.getText().toString());
            int restTime = Integer.parseInt(restTimeET.getText().toString());

            if((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(setsET.getText().toString())) && (!TextUtils.isEmpty(repsET.getText().toString())) && (!TextUtils.isEmpty(restTimeET.getText().toString()))) {
                if(sets >=1 && sets <= 6) {
                    String id = dbExercises.push().getKey();
                    Exercise exercise = null;
                    if(day.equals("Monday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Monday");
                    } else if(day.equals("Tuesday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Tuesday");
                    } else if(day.equals("Wednesday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Wednesday");
                    } else if(day.equals("Thursday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Thursday");
                    } else if(day.equals("Friday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Friday");
                    } else if(day.equals("Saturday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Saturday");
                    } else if(day.equals("Sunday")) {
                        exercise = new Exercise(id,name,sets,reps,restTime,"Sunday");
                    }
                    dbExercises.child(id).setValue(exercise);
                    exerciseNameET.setText("");
                    setsET.setText("");
                    repsET.setText("");
                    restTimeET.setText("");
                } else {
                    Toast.makeText(WorkoutActivity.this, "Please enter an amount of sets between 1 and 6.", Toast.LENGTH_LONG).show();
                }
            }
        } catch(NumberFormatException e) {
            Toast.makeText(this, "All fields are required. Please complete the missing fields.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void updateExercise(String id, String name, int sets, int reps, int restTime, String day) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("exercises").child(id);
        Exercise exercise = new Exercise(id, name, sets, reps, restTime, day);
        dbRef.setValue(exercise);
    }

    private boolean deleteExercise(String id) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("exercises").child(id);
        dbRef.removeValue();
        return true;
    }

    private void showExerciseDialog(final Exercise exercise, final String id, String name, String restTime) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.exercise_dialog, null);
        dialog.setView(dialogView);

        timerTextView = dialogView.findViewById(R.id.timerTextView);

        final EditText exerciseNameET = dialogView.findViewById(R.id.exerciseNameET);
        final EditText setsET = dialogView.findViewById(R.id.setsET);
        final EditText repsET = dialogView.findViewById(R.id.repsET);
        final EditText restTimeET = dialogView.findViewById(R.id.restTimeET);

        updateButton = dialogView.findViewById(R.id.updateButton);
        deleteButton = dialogView.findViewById(R.id.deleteButton);
        startRestButton = dialogView.findViewById(R.id.startRestButton);

        if(!counterActive && Integer.valueOf(restTime) != 0) {
            startRestButton.setEnabled(true);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            startRestButton.setEnabled(false);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }

        timerTextView.setText(restTime + ":00");
        try {
            restTimeTimer = Integer.valueOf(restTime);
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }

        dialog.setTitle(name);
        dialog.setMessage("Update, delete, or start rest time of this exercise.");
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = exerciseNameET.getText().toString();
                try {
                    int sets = Integer.parseInt(setsET.getText().toString());
                    int reps = Integer.parseInt(repsET.getText().toString());
                    int restTime = Integer.parseInt(restTimeET.getText().toString());
                    if((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(setsET.getText().toString())) && (!TextUtils.isEmpty(repsET.getText().toString())) && (!TextUtils.isEmpty(restTimeET.getText().toString()))) {
                        updateExercise(id,name,sets,reps,restTime,exercise.getDay());
                        alertDialog.dismiss();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "All fields are required. Please complete the missing fields.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExercise(id);
                alertDialog.dismiss();
            }
        });

        startRestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!counterActive) {
                    counterActive = true;
                    createTimer();
                    startRestButton.setEnabled(false);
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });
    }

    public void createTimer() {
        timer = new CountDownTimer(restTimeTimer * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                resetRestTimer(restTimeTimer);
                startRestButton.setEnabled(true);
                updateButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        }.start();
    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes * 60);
        timerTextView.setText((String.format("%01d", minutes)) + ":" + (String.format("%02d", seconds)));
    }

    public void resetRestTimer(int restTimeTimer) {
        timerTextView.setText(String.valueOf(restTimeTimer) + ":00");
        timer.cancel();
        Toast.makeText(this, "Your rest has finished. Start your next set!", Toast.LENGTH_LONG).show();
        counterActive = false;
    }
}
