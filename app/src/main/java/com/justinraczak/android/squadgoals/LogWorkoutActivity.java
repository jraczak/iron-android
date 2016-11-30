package com.justinraczak.android.squadgoals;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.justinraczak.android.squadgoals.models.Exercise;
import com.justinraczak.android.squadgoals.models.Workout;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class LogWorkoutActivity extends DashboardActivity
implements SelectExerciseFragment.OnExerciseSelectedListener,
ExerciseFragment.OnFragmentInteractionListener,
EditSetsFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = LogWorkoutActivity.class.getSimpleName();
    public Workout mWorkout;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create or look up the workout to load into the logging screen
        //TODO: Check if a workout already exists for this date before creating a new one
        mRealm = Realm.getDefaultInstance();

        //TODO: Check if there is a workout passed in before creating a new one
        if (getIntent().getStringExtra("workoutId") == null) {
            mWorkout = new Workout(new Date().toString(), null, new Date());
            Log.d(LOG_TAG, "fragment's workout is: " + this.mWorkout);
            mRealm.beginTransaction();
            mRealm.copyToRealm(mWorkout);
            mRealm.commitTransaction();
            Log.d(LOG_TAG, "Created and saved workout " + mWorkout.getName() + " to realm.");
        } else {
            RealmResults<Workout> workoutRealmResults = mRealm.where(Workout.class)
                    .equalTo("id", getIntent().getStringExtra("workoutId"))
                    .findAll();
            Log.d(LOG_TAG, workoutRealmResults.size() + " workouts found by searching ID");
            mWorkout = workoutRealmResults.first();
        }

        //setContentView(R.layout.activity_log_workout);
        setContentView(R.layout.activity_log_workout);
        super.onCreateDrawer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add the content fragment
        LogWorkoutActivityFragment logWorkoutActivityFragment = new LogWorkoutActivityFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container_log_workout, logWorkoutActivityFragment, "LOG_WORKOUT_ACTIVITY_FRAGMENT")
                .commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Log workout");

    }

    public void onExerciseSelected(int position, String name) {
        Toast.makeText(this, "Added " + name + " to workout", Toast.LENGTH_SHORT).show();


        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag("SELECT_EXERCISE_FRAGMENT"))
                .commit();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ExerciseFragment exerciseFragment = ExerciseFragment.newInstance(name, 0);
        Log.d("LogWorkoutActivity", String.valueOf(R.id.content_log_workout));

        fragmentTransaction.add(R.id.exercise_fragment_container, exerciseFragment, name + "_fragment");
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public void onExerciseCardSelected(Exercise exercise, Workout workout) {
        //TODO: Figure out what actually needs to be done here to navigate to logger
        EditSetsFragment editSetsFragment = EditSetsFragment.newInstance(workout, exercise);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_log_workout, editSetsFragment)
                .addToBackStack("add_edit_sets_fragment")
                .commit();
        //TODO: Figure out why new fragment lays over top of old one
        return;
    }

    public void onSetsSaved() {
        Log.d(LOG_TAG, "onSetsSaved called");
    }

    public Workout getWorkout() {
        return mWorkout;
    }
}
