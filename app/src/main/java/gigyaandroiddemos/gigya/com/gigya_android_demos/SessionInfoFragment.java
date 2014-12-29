package gigyaandroiddemos.gigya.com.gigya_android_demos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gigya.socialize.*;
import com.gigya.socialize.android.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SessionInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SessionInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SessionInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private View rootView;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Gigya Instance
     * @return A new instance of fragment SessionInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SessionInfoFragment newInstance(String param1) {
        SessionInfoFragment fragment = new SessionInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public SessionInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_session_info, container, false);
        refreshView();
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        refreshView();
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void refreshView() {
        GSAPI gigya = GSAPI.getInstance();
        TextView statusText = (TextView) rootView.findViewById(R.id.status_value);
        TextView nameText = (TextView) rootView.findViewById(R.id.name_value);
        TextView emailText = (TextView) rootView.findViewById(R.id.email_value);

        if (gigya.getSession() != null){
            if (gigya.getSession().isValid()) {
                statusText.setText(getString(R.string.logged_in));
                MainActivity parent = (MainActivity) getActivity();
                GSObject user = parent.getUser();
                try {
                    String first = user.getString("profile.firstName");
                    String last = user.getString("profile.lastName");
                    String email = user.getString("profile.email");
                    nameText.setText(first + " " + last);
                    emailText.setText(email);

                } catch (Exception ex) {

                }
            } else {
                setLoggedOut(statusText, nameText, emailText);
            }
        } else {
            setLoggedOut(statusText, nameText, emailText);
        }
    }

    public void setLoggedOut(TextView status, TextView name, TextView email) {
        status.setText(getString(R.string.logged_out));
        name.setText(getString(R.string.null_value));
        email.setText(getString(R.string.null_value));
    }
}
