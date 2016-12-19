package com.justinraczak.android.squadgoals;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.justinraczak.android.squadgoals.adapters.DashboardWorkoutAdapter;
import com.justinraczak.android.squadgoals.models.Exercise;
import com.justinraczak.android.squadgoals.models.Workout;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private DashboardWorkoutAdapter mWorkoutAdapter;
    private RealmResults<Workout> mWorkoutRealmResults;
    public Realm mRealm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        onCreateDrawer();


        RealmQuery<Exercise> query = mRealm.where(Exercise.class);
        RealmResults<Exercise> result = query.findAll();
        Log.d(TAG, "There are " + result.size() + " exercises ready for use.");

        RealmQuery<Workout> workoutRealmQuery = mRealm.where(Workout.class);
        mWorkoutRealmResults = workoutRealmQuery.findAll().sort("date", Sort.DESCENDING);
        Log.d(TAG, "There were " + mWorkoutRealmResults.size() + " workouts found.");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_workout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logWorkoutIntent = new Intent(getApplicationContext(), LogWorkoutActivity.class);
                startActivity(logWorkoutIntent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        //TODO: Remove this sign out button
        Button signOutButton = (Button) findViewById(R.id.button_sign_out);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_LONG).show();
            }
        });

        //TODO: Remove this data tester
        updateUserName();

        //TODO: Remove this debugging datetime field
        SimpleDateFormat timeFormat = new SimpleDateFormat("K:mm a");
        TextView dateTimeTextView = (TextView) findViewById(R.id.datetime_textview);
        TextView timeTextView = (TextView) findViewById(R.id.time_textview);
        Date date = new Date();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(date);
        String currentTimeString = timeFormat.format(date);
        dateTimeTextView.setText(currentDateTimeString);
        timeTextView.setText(currentTimeString);

        //TODO: Grab the workout listview and set the adapter on it
        mWorkoutAdapter = new DashboardWorkoutAdapter(this, mWorkoutRealmResults.size(),
                mWorkoutRealmResults);
        final ListView workoutListView = (ListView) findViewById(R.id.dashboard_list_workouts_listview);
        workoutListView.setAdapter(mWorkoutAdapter);


        Log.d(TAG, "About to set click listener on workout card listview");
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: Change this to load the selected workout in log mode
                Intent intent = new Intent(getApplicationContext(), LogWorkoutActivity.class);
                intent.putExtra("workoutId", mWorkoutRealmResults.get(position).getId());
                Log.d(TAG, "Putting workout " + mWorkoutRealmResults.get(position).getId() +
                " into intent.");
                startActivity(intent);
                //TODO: Remove the toast once workout is successfully passed to log activity
                Toast.makeText(getApplicationContext(), "You tapped the " +
                        mWorkoutRealmResults.get(position).getName() +
                        " workout.", Toast.LENGTH_LONG).show();
            }
        });

        setListViewHeightBasedOnItems(workoutListView);
    }

    //@Override
    protected void onCreateDrawer() {
        Log.d(TAG, "onCreateDrawer called");
        //super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //TODO: Figure out if this should work or remove it
        //if (mDrawerLayout == null) {
        //    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        //    mDrawerLayout = (DrawerLayout) inflater.inflate(R.layout.activity_dashboard, null);
        //}
        //Log.d(TAG, "Drawer is " + mDrawerLayout);

        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.nav_user_name);
        TextView userEmail = (TextView) header.findViewById(R.id.nav_user_email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Protect this from null pointer exceptions
        if (user != null) {
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
        }

    }

    public void updateUserName() {
        TextView userGreetingTextView = (TextView) findViewById(R.id.signed_in_text_view);
        Log.d(TAG, "User's name is: " + getIntent().getStringExtra("user_name"));
        userGreetingTextView.setText(
                "Woo, you're signed in, " +
                        getIntent().getStringExtra("user_name"));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRestart() {
        Log.d(TAG, "calling through to base onResume");
        super.onResume();
        Log.d(TAG, "trying to update listview with new workout query");
        updateWorkoutList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {
            int numberOfItems = listAdapter.getCount();
            int totalItemsHeight = 0;

            for (int itemPosition = 0; itemPosition < numberOfItems; itemPosition++) {
                View item = listAdapter.getView(itemPosition, null, listView);
                item.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                totalItemsHeight += item.getMeasuredHeight();
            }

            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems -1 );

            ViewGroup.LayoutParams params =
                    listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
            listView.setFocusable(false);

            return true;
        }
        else {
            return false;
        }
    }

    public void updateWorkoutList() {
        RealmQuery<Workout> workoutRealmQuery = mRealm.where(Workout.class);
        mWorkoutRealmResults = workoutRealmQuery.findAll().sort("date", Sort.DESCENDING);
        Log.d(TAG, "There were " + mWorkoutRealmResults.size() + " workouts found during activity restart.");

        mWorkoutAdapter = new DashboardWorkoutAdapter(this, mWorkoutRealmResults.size(), mWorkoutRealmResults);
        ListView listView = (ListView) findViewById(R.id.dashboard_list_workouts_listview);
        listView.setAdapter(mWorkoutAdapter);
        setListViewHeightBasedOnItems(listView);
    }
}
