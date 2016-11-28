package com.justinraczak.android.squadgoals;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justinraczak.android.squadgoals.models.Exercise;
import com.justinraczak.android.squadgoals.models.Workout;


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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
        return inflater.inflate(R.layout.fragment_edit_sets, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
