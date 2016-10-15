package com.justinraczak.android.squadgoals.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.justinraczak.android.squadgoals.models.Exercise;

import io.realm.RealmResults;

/**
 * Created by Justin on 10/15/16.
 */

public class SelectExerciseAdapter extends BaseAdapter {

    private Context mContext;
    private int numberOfExercises;
    private RealmResults<Exercise> mExerciseList;

    public SelectExerciseAdapter(Context context, int count, RealmResults<Exercise> exercises) {
        mContext = context;
        numberOfExercises = count;
        mExerciseList = exercises;
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
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setPadding(48, 18, 48, 18);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(mExerciseList.get(position).getName());
        return textView;
    }
}
