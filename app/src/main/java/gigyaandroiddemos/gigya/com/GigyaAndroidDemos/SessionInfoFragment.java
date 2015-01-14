package gigyaandroiddemos.gigya.com.GigyaAndroidDemos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.gigya.socialize.*;
import com.gigya.socialize.android.*;

import com.squareup.picasso.*;

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
        final TextView statusText = (TextView) rootView.findViewById(R.id.status_value);
        final TextView nameText = (TextView) rootView.findViewById(R.id.name_value);
        final TextView emailText = (TextView) rootView.findViewById(R.id.email_value);
        final ImageView avatarView = (ImageView) rootView.findViewById(R.id.avatar);

        if (gigya.getSession() != null){
            if (gigya.getSession().isValid()) {
                MainActivity parent = (MainActivity) getActivity();
                GSObject user = parent.getUser();

                // Retrieve the user if it's not set. (Reloaded app with active session)
                if (user == null) {
                    GSResponseListener resListener = new GSResponseListener() {
                        @Override
                        public void onGSResponse(String method, GSResponse response, Object context) {
                            try {
                                if (response.getErrorCode()==0) { // SUCCESS! response status = OK
                                    MainActivity parent = (MainActivity) getActivity();
                                    Log.w("Gigya-Android-Demos", "Successfully set user");
                                    parent.setUser(response.getData());
                                    setLoggedIn(statusText, nameText, emailText, avatarView, response.getData());
                                } else {  // Error
                                    Log.w("Gigya-Android-Demos", "GSResponse: 'getAccountInfo' returned an error");
                                    Log.w("Gigya-Android-Demos", response.getErrorMessage());
                                }
                            } catch (Exception ex) {  ex.printStackTrace();  }
                        }
                    };
                    GSAPI.getInstance()
                            .sendRequest("accounts.getAccountInfo", null, resListener, null );
                } else {
                    // Grab the user data
                    setLoggedIn(statusText, nameText, emailText, avatarView, user);
                }

            } else {
                setLoggedOut(statusText, nameText, emailText, avatarView);
            }
        } else {
            setLoggedOut(statusText, nameText, emailText, avatarView);
        }
    }

    public void setLoggedOut(TextView status, TextView name, TextView email, ImageView avatar) {
        status.setText(getString(R.string.logged_out));
        name.setText(getString(R.string.null_value));
        email.setText(getString(R.string.null_value));
        setUnknownAvatar(avatar);
    }

    public void setLoggedIn(TextView status, TextView name, TextView emailView, ImageView avatar, GSObject user) {
        status.setText(getString(R.string.logged_in));
        try {
            GSObject profile = user.getObject("profile");
            String first = profile.getString("firstName");
            String last = profile.getString("lastName");
            String email = profile.getString("email");
            if (profile.containsKey("photoURL")) {
                setAvatar(avatar,profile.getString("photoURL"));
            } else {
                setUnknownAvatar(avatar);
            }
            name.setText(first + " " + last);
            emailView.setText(email);
        } catch (Exception ex) {
            Log.w("Gigya-Android-Demos", "Something went horribly wrong with the user!");
            ex.printStackTrace();
        }
    }

    public void setAvatar(ImageView avatar, String url) {
        Log.w("Gigya-Android-Demos", "Loading Image from: " + url);
        Picasso.with(avatar.getContext())
                .load(url)
                .placeholder(R.drawable.help128)
                .error(R.drawable.help128)
                .into(avatar);
    }

    public void setUnknownAvatar(ImageView avatar) {
        Picasso.with(avatar.getContext()).load(R.drawable.help128).into(avatar);
    }

}
