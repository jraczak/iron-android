package com.justinraczak.android.squadgoals;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.justinraczak.android.squadgoals.models.Workout;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogWorkoutActivityFragment extends Fragment {

    private final static String LOG_TAG = LogWorkoutActivityFragment.class.getSimpleName();

    private Workout workout;
    private Realm mRealm;

    public LogWorkoutActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();

        //TODO: Check if there is a workout passed in before creating a new one
        if (getActivity().getIntent().getStringExtra("workoutId") == null) {
            workout = new Workout(new Date().toString(), null, new Date());
            Log.d(LOG_TAG, "fragment's workout is: " + this.workout);
            mRealm.beginTransaction();
            Workout realmWorkout = mRealm.copyToRealm(workout);
            mRealm.commitTransaction();
            Log.d(LOG_TAG, "Created and saved workout " + workout.getName() + " to realm.");
        } else {
            RealmResults<Workout> workoutRealmResults = mRealm.where(Workout.class)
                    .equalTo("id", getActivity().getIntent().getStringExtra("workoutId"))
                    .findAll();
            Log.d(LOG_TAG, workoutRealmResults.size() + " workouts found by searching ID");
            workout = workoutRealmResults.first();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_workout, container, false);
        Button addExerciseButton = (Button) view.findViewById(R.id.button_add_exercise);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectExerciseDialog();
            }
        });
        TextView workoutName = (TextView) view.findViewById(R.id.workout_name);
        workoutName.setText(this.workout.getName());
        return view;

    }

    public void showSelectExerciseDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        SelectExerciseFragment selectExerciseFragment = new SelectExerciseFragment();

        //TODO: Figure out how to make this full screen instead of small pop-up dialog
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //fragmentTransaction.replace(R.id.fragment, selectExerciseFragment)
        //        .addToBackStack(null).commit();

        selectExerciseFragment.show(fragmentManager, "SELECT_EXERCISE_FRAGMENT");

    }

}
