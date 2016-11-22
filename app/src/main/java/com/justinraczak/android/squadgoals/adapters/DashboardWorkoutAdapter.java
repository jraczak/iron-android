package com.justinraczak.android.squadgoals.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.justinraczak.android.squadgoals.models.Workout;

import java.util.ArrayList;

/**
 * Created by Justin on 11/21/16.
 */

public class DashboardWorkoutAdapter extends BaseAdapter {

    private Context mContext;
    public int numberOfWorkouts;
    public ArrayList<Workout> mWorkoutsList;
    public LayoutInflater mInflater;

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
        return null;
    }
}
