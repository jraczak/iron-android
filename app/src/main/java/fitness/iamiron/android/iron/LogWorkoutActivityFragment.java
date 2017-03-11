package fitness.iamiron.android.iron;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fitness.iamiron.android.iron.models.Exercise;
import fitness.iamiron.android.iron.models.Workout;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogWorkoutActivityFragment extends Fragment {

    private final static String LOG_TAG = LogWorkoutActivityFragment.class.getSimpleName();

    private Workout mWorkout;
    private Realm mRealm;

    public LogWorkoutActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "Fragment was stored in a bundle. Fetching the workout from the saved bundle");
            mWorkout = savedInstanceState.getParcelable("mWorkout");
        } else {
            mWorkout = ((LogWorkoutActivity)getActivity()).getWorkout();
        }

        //mRealm = Realm.getDefaultInstance();
//
        ////TODO: Check if there is a workout passed in before creating a new one
        //if (getActivity().getIntent().getStringExtra("workoutId") == null) {
        //    workout = new Workout(new Date().toString(), null, new Date());
        //    Log.d(LOG_TAG, "fragment's workout is: " + this.workout);
        //    mRealm.beginTransaction();
        //    Workout realmWorkout = mRealm.copyToRealm(workout);
        //    mRealm.commitTransaction();
        //    Log.d(LOG_TAG, "Created and saved workout " + workout.getName() + " to realm.");
        //} else {
        //    RealmResults<Workout> workoutRealmResults = mRealm.where(Workout.class)
        //            .equalTo("id", getActivity().getIntent().getStringExtra("workoutId"))
        //            .findAll();
        //    Log.d(LOG_TAG, workoutRealmResults.size() + " workouts found by searching ID");
        //    workout = workoutRealmResults.first();
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_workout, container, false);
        Button addExerciseButton = (Button) view.findViewById(R.id.button_add_exercise);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Add exercise button clicked");
                showSelectExerciseDialog();
            }
        });
        TextView workoutName = (TextView) view.findViewById(R.id.workout_name);
        //TODO: Figure out why workout is not available at activity start time
        workoutName.setText(mWorkout.getName());

        FragmentManager fragmentManager = getFragmentManager();
        for (Exercise exercise : mWorkout.exercises) {
            // Make sure no duplicate fragments are created for an exercise
            if (fragmentManager.findFragmentByTag("EXERCISE_"+exercise.getId()+"_FRAGMENT") == null) {
                Log.d(LOG_TAG, "No fragment was found for " + exercise.getName() + ", creating new fragment");
                ExerciseFragment exerciseFragment = ExerciseFragment.newInstance(exercise, mWorkout.getSetCountForExercise(exercise));
                fragmentManager.beginTransaction()
                        .add(R.id.exercise_fragment_container, exerciseFragment, "EXERCISE_" + exercise.getId() + "_FRAGMENT")
                        .commit();
            } else {
                Log.d(LOG_TAG, "An existing fragment was found for " + exercise.getName());
            }
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Log Workout");

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

        //selectExerciseFragment.show(fragmentManager, "SELECT_EXERCISE_FRAGMENT");
        Log.d(LOG_TAG, "SelectExerciseFragment is " + selectExerciseFragment);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_log_workout, selectExerciseFragment, "SELECT_EXERCISE_FRAGMENT")
                .addToBackStack("show_select_exercise_fragment")
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.d(LOG_TAG, "Storing the workout into a bundle");
        outState.putParcelable("mWorkout", mWorkout);
        super.onSaveInstanceState(outState);
    }
}
