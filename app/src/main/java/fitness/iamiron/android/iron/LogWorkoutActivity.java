package fitness.iamiron.android.iron;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import fitness.iamiron.android.iron.models.Exercise;
import fitness.iamiron.android.iron.models.Set;
import fitness.iamiron.android.iron.models.Workout;
import io.realm.Realm;
import io.realm.RealmResults;

public class LogWorkoutActivity extends DashboardActivity
implements SelectExerciseFragment.OnExerciseSelectedListener,
ExerciseFragment.OnFragmentInteractionListener,
EditSetsFragment.OnFragmentInteractionListener,
SetFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = LogWorkoutActivity.class.getSimpleName();
    public Workout mWorkout;
    private Realm mRealm;
    private String mSaveButtonState;
    private Set mEditingSet;
    private SetFragment mEditingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mRealm = Realm.getDefaultInstance();

        // Check if this activity instance was stored in a bundle previously
        if (savedInstanceState != null) {
            Log.d(LOG_TAG, "Trying to get workout from saved bundle.");
            Log.d(LOG_TAG, "Found this in bundle: " + savedInstanceState.getParcelable("mWorkout"));
            mWorkout = savedInstanceState.getParcelable("mWorkout");
        } else {
            // Create or look up the workout to load into the logging screen
            if (getIntent().getIntExtra("workoutId", 0) == 0) {
                //mWorkout = new Workout(new Date().toString(), null, new Date());
                //Log.d(LOG_TAG, "fragment's workout is: " + this.mWorkout);
                //Log.d(LOG_TAG, "workout realm id is " + mWorkout.getRealmId());
                mRealm.beginTransaction();
                //mRealm.copyToRealm(mWorkout);
                mWorkout = mRealm.createObject(Workout.class, Workout.getNewAutoIncrementId());
                mWorkout.setDate(new Date());
                mWorkout.setSets(null);
                mWorkout.setName(new Date().toString());
                mRealm.commitTransaction();
                Log.d(LOG_TAG, "Created and saved workout " + mWorkout.getName() + " to realm with realm id " + mWorkout.getRealmId());
            } else {
                RealmResults<Workout> workoutRealmResults = mRealm.where(Workout.class)
                        .equalTo("realmId", getIntent().getIntExtra("workoutId", 0))
                        .findAll();
                Log.d(LOG_TAG, workoutRealmResults.size() + " workouts found by searching for realm id " + getIntent().getIntExtra("workoutId", 0));
                mWorkout = workoutRealmResults.first();
            }
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

        mSaveButtonState = "save";

        //TODO: Figure out how to deal with closing Realms

    }

    public void onExerciseSelected(int position, String name) {

        mRealm = Realm.getDefaultInstance();
        Exercise exercise = mRealm.where(Exercise.class).equalTo("name", name).findAll().first();

        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag("SELECT_EXERCISE_FRAGMENT"))
                .commit();

        Log.d(LOG_TAG, "Checking if selected exercise is already in workout");
        Exercise existingExercise = mWorkout.getExercises().where().equalTo("id", exercise.getId()).findAll().first();
        Log.d(LOG_TAG, "Search returned " + existingExercise.getName());
        if (existingExercise.getId() != exercise.getId()) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            ExerciseFragment exerciseFragment = ExerciseFragment.newInstance(exercise, mWorkout.getSetCountForExercise(exercise));
            Log.d("LogWorkoutActivity", String.valueOf(R.id.content_log_workout));

            fragmentTransaction.add(R.id.exercise_fragment_container, exerciseFragment, name + "_fragment");
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            //Add the exercise to the workout
            mWorkout.addExercise(exercise);
            Toast.makeText(this, "Added " + name + " to workout", Toast.LENGTH_SHORT).show();
            mRealm.close();
        } else {
            Toast.makeText(this, exercise.getName() + " is already in this workout", Toast.LENGTH_LONG).show();
        }

    }


    public void onExerciseCardSelected(Exercise exercise, Workout workout) {
        EditSetsFragment editSetsFragment = EditSetsFragment.newInstance(workout, exercise);
        //Replace the fragment now that it is added programmatically instead of via xml, which doesn't work
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_log_workout, editSetsFragment, "EDIT_SETS_FRAGMENT")
                .addToBackStack("add_edit_sets_fragment")
                .commit();
        return;
    }

    public void onSetsSaved(Exercise exercise, Workout workout, int reps, float weight) {
        Log.d(LOG_TAG, "onSetsSaved called");


        //TODO: Save the set to the database

        mRealm.beginTransaction();
        //Integer id = Set.getNewAutoIncrementId();
        //Set set = new Set(id, new Date(), workout, exercise, reps, weight);
        //Log.d(LOG_TAG, "Attempting to save set with id " + set.getRealmId() + " to realm");
        //Set realmSet = mRealm.copyToRealm(set);
        Set set = mRealm.createObject(Set.class, Set.getNewAutoIncrementId());
        set.setWorkout(workout);
        set.setExercise(exercise);
        set.setDate(new Date());
        set.setReps(reps);
        set.setWeight(weight);
        mRealm.commitTransaction();
        // Need to ALSO set the set on the workout explicitly, relationship is not built implicitly unidirectionally
        workout.addSet(set);
        Log.d(LOG_TAG, "id of managed realm set is " + set.getRealmId() + " and workout id is " + set.getWorkout().getRealmId());
        Log.d(LOG_TAG, "Workout " + workout.getRealmId() + " now has " + workout.getSets().size() + " sets");
        //Log.d(LOG_TAG, "id of managed realm object is " + realmSet.getRealmId());
        mRealm.close();

        // Don't think I need this now: Long id = Set.getNewAutoIncrementId();
        //Set set = new Set(new Date(), workout, exercise, reps, weight);
        //mRealm.beginTransaction();
        //mRealm.copyToRealm(set);
        //mRealm.commitTransaction();

        SetFragment setFragment = SetFragment.newInstance(exercise, workout, set, reps, weight);
        Log.d(LOG_TAG, "Adding fragment to container");
        getFragmentManager().beginTransaction()
                .add(R.id.container_saved_sets, setFragment, UUID.randomUUID().toString())
                .commit();

        Log.d(LOG_TAG, "Clearing text field values");
        //TODO: See if these should be variables in the class so they're not always looked up
        EditText repsEditText = (EditText) findViewById(R.id.edit_text_reps);
        EditText weightEditText = (EditText) findViewById(R.id.edit_text_weight);
        //repsEditText.getText().clear();
        //weightEditText.getText().clear();
        weightEditText.requestFocus();



    }

    public void onSetUpdated(Set set, int updatedReps, int updatedWeight) {

        mRealm.beginTransaction();
        Log.d(LOG_TAG, "Setting updated values on set");

        set.setReps(updatedReps);
        set.setWeight(updatedWeight);

        Log.d(LOG_TAG, "Updating object in realm");
        mRealm.copyToRealmOrUpdate(set);
        mRealm.commitTransaction();

        Log.d(LOG_TAG, "Resetting edited set to null");
        mEditingSet = null;

        toggleButtons("save", null);

        Toast.makeText(getApplicationContext(), "Set updated", Toast.LENGTH_SHORT).show();

        //TODO: Update the UI values for the updated set
        mEditingFragment.updateDisplayValues();

    }

    public void onSetDeleted(Set set) {
        mRealm.beginTransaction();
        Log.d(LOG_TAG, "Preparing to delete set with realm id " + set.getRealmId());

        set.deleteFromRealm();
        mRealm.commitTransaction();
        Log.d(LOG_TAG, "Set was deleted: " + (!set.isValid()));
        getFragmentManager().beginTransaction()
                .remove(mEditingFragment)
                .commit();
        Toast.makeText(this, "Set deleted", Toast.LENGTH_SHORT).show();
        mRealm.close();

        toggleButtons("save", null);
    }

    public void onSetSelected(Set set, String fragmentTag) {
        Log.d(LOG_TAG, "onSetSelected callback initiated");
        //TODO: Change the logger to edit mode on this action
        EditText repsEditText = (EditText) findViewById(R.id.edit_text_reps);
        EditText weightEditText = (EditText) findViewById(R.id.edit_text_weight);
        repsEditText.setText(String.valueOf(set.getReps()));
        weightEditText.setText(String.valueOf(Math.round(set.getWeight())));

        mEditingSet = set;
        Log.d(LOG_TAG, "Looking for fragment with tag " + fragmentTag);
        mEditingFragment = (SetFragment) getFragmentManager().findFragmentByTag(fragmentTag);


        toggleButtons("update", set);

    }

    public void toggleButtons(String mode, Set set) {

        Button saveUpdateButton = (Button) findViewById(R.id.button_save_set);
        Button clearDeleteButton = (Button) findViewById(R.id.button_clear_edittext_values);
        final EditSetsFragment editSetsFragment = (EditSetsFragment) getFragmentManager().findFragmentByTag("EDIT_SETS_FRAGMENT");
        switch (mode) {
            case "save":
                Log.d(LOG_TAG, "Setting the controls to save mode.");
                saveUpdateButton.setText("SAVE");
                saveUpdateButton.setBackgroundColor(getResources().getColor(R.color.button_primary));
                saveUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editSetsFragment.onSaveButtonPressed(editSetsFragment.getmExercise(), mWorkout);
                    }
                });
                clearDeleteButton.setText("CLEAR");
                clearDeleteButton.setBackgroundColor(getResources().getColor(R.color.primary_light));
                clearDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editSetsFragment.clearTextFields();
                    }
                });
                break;
            case "update":
                Log.d(LOG_TAG, "Setting the controls to update mode.");
                saveUpdateButton.setText("UPDATE");
                saveUpdateButton.setBackgroundColor(getResources().getColor(R.color.button_secondary));
                saveUpdateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editSetsFragment.onUpdateButtonPressed(mEditingSet);
                    }
                });
                clearDeleteButton.setText("DELETE");
                clearDeleteButton.setBackgroundColor(getResources().getColor(R.color.button_warning));
                clearDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editSetsFragment.onDeleteButtonPressed(mEditingSet);
                    }
                });
                break;
        }
    }

    public Workout getWorkout() {
        return mWorkout;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Make sure the workout reference isn't lost, for example during rotation
        Log.d(LOG_TAG, "Saving workout " + mWorkout + " to bundle");
        outState.putParcelable("mWorkout", mWorkout);
        super.onSaveInstanceState(outState);
    }

    //@Override
    //public void onResume() {
    //    super.onResume();
    //}
}
