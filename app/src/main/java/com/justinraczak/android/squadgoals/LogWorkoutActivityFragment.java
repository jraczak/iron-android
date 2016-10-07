package com.justinraczak.android.squadgoals;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class LogWorkoutActivityFragment extends Fragment {

    public LogWorkoutActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_workout, container, false);
        Button addExerciseButton = (Button) view.findViewById(R.id.button_add_exercise);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tried to add exercise", Toast.LENGTH_LONG).show();
            }
        });
        return view;

    }

}
