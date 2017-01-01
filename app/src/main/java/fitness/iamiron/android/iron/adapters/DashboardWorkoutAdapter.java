package fitness.iamiron.android.iron.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fitness.iamiron.android.iron.DashboardActivity;
import fitness.iamiron.android.iron.R;
import fitness.iamiron.android.iron.models.Workout;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Justin on 11/21/16.
 */

public class DashboardWorkoutAdapter extends BaseAdapter {

    private final String LOG_TAG = DashboardWorkoutAdapter.class.getSimpleName();
    private Realm mRealm;

    private Context mContext;
    public int numberOfWorkouts;
    public RealmResults<Workout> mWorkoutsList;
    public LayoutInflater mInflater;

    public DashboardWorkoutAdapter(Context context, int count, RealmResults<Workout> workouts) {
        mContext = context;
        numberOfWorkouts = count;
        mWorkoutsList = workouts;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return mWorkoutsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWorkoutsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardView cardView;
        TextView workoutNameTextView;
        TextView setCountTextView;
        TextView exerciseCountTextView;
        TextView totalLoadTextView;
        Button deleteWorkoutButton;

        final Workout workout = (Workout) this.getItem(position);

        Log.d(LOG_TAG, "convertView is " + convertView);
        Log.d(LOG_TAG, "parent is " + parent);

        if (convertView == null) {
            Log.d(LOG_TAG, "workout name is " + workout.getName());

            cardView = (CardView) mInflater.inflate(R.layout.card_workout, parent, false);
        } else {
            cardView = (CardView) convertView;
        }
        Log.d(LOG_TAG, "Setting card values for " + workout.getName());

        //Set card display values
        workoutNameTextView = (TextView) cardView.findViewById(R.id.workout_card_workout_name);
        workoutNameTextView.setText(workout.getName());
        setCountTextView = (TextView) cardView.findViewById(R.id.workout_card_set_count);
        //TODO: Abide the rules and don't use string concatenation here
        setCountTextView.setText(String.valueOf(workout.getSets().size()));
        Log.d(LOG_TAG, "Workout has " + String.valueOf(workout.getSets() + " sets"));
        exerciseCountTextView = (TextView) cardView.findViewById(R.id.workout_card_exercise_count);
        exerciseCountTextView.setText(String.valueOf(workout.getExercises().size()));
        totalLoadTextView = (TextView) cardView.findViewById(R.id.workout_card_total_load);
        totalLoadTextView.setText(String.valueOf(workout.getTotalLoad()) + " lbs");
        deleteWorkoutButton = (Button) cardView.findViewById(R.id.button_delete_workout);
        deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Attempting to delete workout " + workout.getName());
                Toast.makeText(v.getContext(), "Deleting workout " + workout.getName(), Toast.LENGTH_LONG).show();
                mRealm.beginTransaction();
                workout.deleteFromRealm();
                mRealm.commitTransaction();
                ((DashboardActivity)mContext).updateWorkoutList();
            }
        });


        Log.d(LOG_TAG, "tried to make card for " + workout.getName());

        return cardView;
    }
}
