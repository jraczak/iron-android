package fitness.iamiron.android.iron.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import fitness.iamiron.android.iron.R;
import fitness.iamiron.android.iron.models.Exercise;
import io.realm.RealmResults;

/**
 * Created by Justin on 10/15/16.
 */

public class SelectExerciseAdapter extends BaseAdapter {

    private static final String LOG_TAG = SelectExerciseAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    private int numberOfExercises;
    private RealmResults<Exercise> mExerciseList;

    public SelectExerciseAdapter(Context context, int count, RealmResults<Exercise> exercises) {
        mContext = context;
        numberOfExercises = count;
        mExerciseList = exercises;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return numberOfExercises;
    }

    public Object getItem(int position) {
        return mExerciseList.toArray()[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LinearLayout linearLayout;
        if (convertView == null) {
            linearLayout = (LinearLayout) mInflater.inflate(R.layout.list_item_exercise, parent, false);
            Log.d(LOG_TAG, "convertView was null; LinearLayout is " + linearLayout);
        } else {
            linearLayout = (LinearLayout) convertView;
            Log.d(LOG_TAG, "convertView was not null; LinearLayout is " + linearLayout);
        }
        Log.d(LOG_TAG, "Setting textview value to " + mExerciseList.get(position).getName());
        TextView textView = (TextView) linearLayout.findViewById(R.id.select_exercise_exercise_name);
        TextView muscleTextView = (TextView) linearLayout.findViewById(R.id.select_exercise_primary_muscle);
        textView.setText(mExerciseList.get(position).getName());
        Log.d(LOG_TAG, "Exercise primary muscle is " + mExerciseList.get(position).getPrimaryMuscles());
        muscleTextView.setText(mExerciseList.get(position).getPrimaryMuscles());
        return linearLayout;

        //TextView textView;
        //if (convertView == null) {
        //    textView = new TextView(mContext);
        //    textView.setPadding(48, 18, 48, 18);
        //} else {
        //    textView = (TextView) convertView;
        //}
        //textView.setText(mExerciseList.get(position).getName());
        //return textView;
    }
}
