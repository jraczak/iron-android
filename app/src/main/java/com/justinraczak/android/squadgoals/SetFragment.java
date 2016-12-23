package com.justinraczak.android.squadgoals;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justinraczak.android.squadgoals.models.Exercise;
import com.justinraczak.android.squadgoals.models.Set;
import com.justinraczak.android.squadgoals.models.Workout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Exercise mExercise;
    private Workout mWorkout;
    private Set mSet;
    private int mReps;
    private float mWeight;
    //private String mTag;

    private TextView mRepsTextView;
    private TextView mWeightTextview;

    private OnFragmentInteractionListener mListener;

    public SetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param exercise The exercise performed during the set.
     * @param workout The workout the set is to be saved to.
     * @param reps The number of reps for the set.
     * @param weight The weight for the set
     * @return A new instance of fragment SetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetFragment newInstance(Exercise exercise, Workout workout, Set set, int reps, float weight) {
        SetFragment fragment = new SetFragment();
        Bundle args = new Bundle();
        args.putParcelable("exercise", exercise);
        args.putParcelable("workout", workout);
        args.putParcelable("set", set);
        args.putInt("reps", reps);
        args.putFloat("weight", weight);
        //args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSet = getArguments().getParcelable("set");
            mExercise = getArguments().getParcelable("exercise");
            mWorkout = getArguments().getParcelable("workout");
            mReps = getArguments().getInt("reps");
            mWeight = getArguments().getFloat("weight");
            //mTag = getArguments().getString("tag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        mRepsTextView = (TextView) view.findViewById(R.id.rep_count);
        mWeightTextview = (TextView) view.findViewById(R.id.weight);
        Log.d("SetFragment", "TextView is " + mRepsTextView);
        mRepsTextView.setText(String.valueOf(this.mReps));
        mWeightTextview.setText(String.valueOf(this.mWeight));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSetSelected(mSet);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSetSelected(Set set) {
        if (mListener != null) {
            Log.d("SetFragment", "Fragment's tag is " + this.getTag());
            mListener.onSetSelected(set, this.getTag());
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

    public void updateDisplayValues() {
        mRepsTextView.setText(String.valueOf(this.mSet.getReps()));
        mWeightTextview.setText(String.valueOf(this.mSet.getWeight()));
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
        void onSetSelected(Set set, String fragmentTag);
    }
}
