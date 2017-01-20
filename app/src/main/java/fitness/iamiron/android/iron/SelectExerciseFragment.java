package fitness.iamiron.android.iron;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import fitness.iamiron.android.iron.adapters.SelectExerciseAdapter;
import fitness.iamiron.android.iron.models.Exercise;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectExerciseFragment.OnExerciseSelectedListener} interface
 * to handle interaction events.
 * Use the {@link SelectExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectExerciseFragment extends Fragment {

    private static final String LOG_TAG = SelectExerciseFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RealmResults<Exercise> mExercises;
    private SelectExerciseAdapter mSelectExerciseAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Instance of the callback to verify implementation
    private OnExerciseSelectedListener mListener;

    public SelectExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectExerciseFragment newInstance(String param1, String param2) {
        SelectExerciseFragment fragment = new SelectExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_exercise, container, false);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Exercise> query = realm.where(Exercise.class);
        mExercises = query.findAll();
        Log.d(LOG_TAG, "mExercises returned value of " + mExercises);

        Log.d(LOG_TAG, "Passing " + mExercises.size() + " exercises to adapter");
        mSelectExerciseAdapter = new SelectExerciseAdapter(getContext(), mExercises.size(), mExercises);
        Log.d(LOG_TAG, "Select exercise adapater is " + mSelectExerciseAdapter);

        ListView listView = (ListView) view.findViewById(R.id.list_view_select_exercise);
        Log.d(LOG_TAG, "List view is " + listView);
        listView.setAdapter(mSelectExerciseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onExerciseSelected(position, mExercises.get(position).getName());
            }
        });
        // Make sure the list view is tall enough to show all options
        setListViewHeightBasedOnItems(listView);
        return view;
    }

    //@Override
    //public Dialog onCreateDialog(Bundle savedInstanceState) {
    //    Dialog dialog = super.onCreateDialog(savedInstanceState);
    //    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //    return dialog;
    //}

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    //}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExerciseSelectedListener) {
            mListener = (OnExerciseSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnExerciseSelectedListener");
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

    public interface OnExerciseSelectedListener {
        public void onExerciseSelected(int position, String name);
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

            return true;
        }
        else {
            return false;
        }
    }
}
