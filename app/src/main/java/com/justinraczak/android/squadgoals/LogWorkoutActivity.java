package com.justinraczak.android.squadgoals;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class LogWorkoutActivity extends DashboardActivity
implements SelectExerciseFragment.OnExerciseSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_log_workout);
        setContentView(R.layout.activity_log_workout);
        super.onCreateDrawer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        Toast.makeText(this, "Tapped on " + name, Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .remove(getFragmentManager().findFragmentByTag("SELECT_EXERCISE_FRAGMENT"))
                .commit();
    }

}
