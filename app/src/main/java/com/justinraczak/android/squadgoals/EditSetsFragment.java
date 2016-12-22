package com.justinraczak.android.squadgoals;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.justinraczak.android.squadgoals.models.Exercise;
import com.justinraczak.android.squadgoals.models.Set;
import com.justinraczak.android.squadgoals.models.Workout;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditSetsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditSetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSetsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "exercise";
    private static final String ARG_PARAM2 = "workout";
    private static final String LOG_TAG = EditSetsFragment.class.getSimpleName();

    // Text fields and controls for the logger
    public EditText mRepsEditText;
    public EditText mWeightEditText;
    public Button mSaveButton;
    public Button mClearButton;

    // TODO: Rename and change types of parameters
    private Exercise mExercise;
    private Workout mWorkout;

    private OnFragmentInteractionListener mListener;

    public EditSetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param workout The workout for which sets are being edited.
     * @param exercise The exercise of which sets are being created.
     * @return A new instance of fragment EditSetsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditSetsFragment newInstance(Workout workout, Exercise exercise) {
        EditSetsFragment fragment = new EditSetsFragment();
        Bundle args = new Bundle();
        //TODO: Figure out how to make these serializable
        args.putParcelable("exercise", exercise);
        args.putParcelable("workout", workout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mExercise = getArguments().getParcelable("exercise");
            mWorkout = getArguments().getParcelable("workout");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_sets, container, false);
        mSaveButton = (Button) view.findViewById(R.id.button_save_set);
        mRepsEditText = (EditText) view.findViewById(R.id.edit_text_reps);
        mRepsEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mWeightEditText = (EditText) view.findViewById(R.id.edit_text_weight);
        mWeightEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonPressed(mExercise, mWorkout);
            }
        });

        // Make sure any existing sets are loaded into the view
        RealmResults<Set> sets = mWorkout.getSetsForExercise(mExercise);
        if (sets.size() > 0) {
            FragmentManager fragmentManager = getFragmentManager();
            for (Set set : sets) {
                SetFragment setFragment = SetFragment.newInstance(mExercise, mWorkout, set, set.getReps(), set.getWeight());
                fragmentManager.beginTransaction()
                        .add(R.id.container_saved_sets, setFragment)
                        .commit();
            }
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSaveButtonPressed(Exercise exercise, Workout workout) {
        if (mListener != null) {

            Log.d(LOG_TAG, "Fetching int value of reps");
            int reps = Integer.parseInt(String.valueOf(mRepsEditText.getText()));
            Log.d(LOG_TAG, "Reps value is " + reps);

            Log.d(LOG_TAG, "Fetching int value of weight");
            int weight = Integer.parseInt(String.valueOf(mWeightEditText.getText()));
            Log.d(LOG_TAG, "Weight value is " + weight);

            Log.d(LOG_TAG, "Sending parsed input values to activity");
            mListener.onSetsSaved(exercise, workout, reps, weight);
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
        void onSetsSaved(Exercise exercise, Workout workout, int reps, float weight);
    }
}
