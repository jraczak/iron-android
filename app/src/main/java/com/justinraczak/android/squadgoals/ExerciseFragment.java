package com.justinraczak.android.squadgoals;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justinraczak.android.squadgoals.models.Exercise;
import com.justinraczak.android.squadgoals.models.Workout;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "exercise";
    private static final String ARG_PARAM2 = "setCount";
    private static final String LOG_TAG = ExerciseFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String exerciseName;
    private int setCount;
    private Exercise mExercise;
    private Realm mRealm;

    private OnFragmentInteractionListener mListener;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param exercise The exercise object being logged to the workout.
     * @param setCount The number of sets logged to the exercise.
     * @return A new instance of fragment ExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(Exercise exercise, int setCount) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, exercise);
        args.putInt(ARG_PARAM2, setCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mRealm = Realm.getDefaultInstance();
        if (getArguments() != null) {
            mExercise = getArguments().getParcelable(ARG_PARAM1);
            setCount = getArguments().getInt(ARG_PARAM2);
        }
        //RealmResults<Exercise> exerciseRealmResults = mRealm.where(Exercise.class)
        //        .equalTo("name", exerciseName)
        //        .findAll();
        //mExercise = exerciseRealmResults.first();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup parentContainer = (ViewGroup) getActivity().findViewById(R.id.exercise_fragment_container);
        CardView view = (CardView) inflater.inflate(R.layout.fragment_exercise, container, false);
        TextView textView = (TextView) view.findViewById(R.id.exercise_name);
        TextView setCountTextView = (TextView) view.findViewById(R.id.set_count);
        textView.setText(this.mExercise.getName());
        setCountTextView.setText(((LogWorkoutActivity)getActivity()).getWorkout().getSetsForExercise(mExercise) + " " + getResources().getString(R.string.sets_label));

        Log.d(LOG_TAG, "setting layout params of card");
        //view.setPadding(16, 24, 16, 24);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        //params.setMargins(8, 8, 8, 8);
        //view.setLayoutParams(params);

        //view.setCardElevation(2);
        //view.setCardBackgroundColor(getResources().getColor(R.color.primary_light));
        //view.setRadius(2);
        Log.d(LOG_TAG, "card elevation is set to " + view.getCardElevation());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Load an edit sets fragment with the selected exercise
                onExerciseCardSelected(mExercise, ((LogWorkoutActivity)getActivity()).getWorkout());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onExerciseCardSelected(Exercise exercise, Workout workout) {
        if (mListener != null) {
            mListener.onExerciseCardSelected(exercise, workout);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onExerciseCardSelected(Exercise exercise, Workout workout);
    }
}
