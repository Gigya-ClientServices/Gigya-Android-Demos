package gigyaandroiddemos.gigya.com.GigyaAndroidDemos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.GSAPI;
import com.gigya.socialize.android.GSPermissionResultHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublishUserActionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublishUserActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishUserActionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishUserActionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublishUserActionFragment newInstance(String param1, String param2) {
        PublishUserActionFragment fragment = new PublishUserActionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PublishUserActionFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_publish_user_action, container, false);
        final GSAPI gigya = GSAPI.getInstance();
        final EditText editText = (EditText) view.findViewById(R.id.editText);
        Button publishButton = (Button) view.findViewById(R.id.publishButton);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity parent = (MainActivity) getActivity();
                GSObject params = new GSObject();
                GSObject userAction = new GSObject();

                if (gigya.getLastLoginProvider() == "facebook") {
                    ArrayList<String> permissions = new ArrayList<String>();
                    permissions.add("publish_actions");
                    try {
                        gigya.requestNewFacebookPublishPermissions(permissions, new GSPermissionResultHandler() {
                            @Override
                            public void onResult(boolean b, Exception e, List<String> strings) {
                                if (!b) {
                                    // Handle Error
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("Facebook permissions failed, returned an error:\n" + e.getMessage())
                                            .setTitle(R.string.error_dialog)
                                            .setCancelable(false)
                                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                }
                                            });
                                    return;
                                }
                            }
                        });
                    } catch (Exception ex) {

                    }
                }

                userAction.put("title", "Gigya Android Demos");
                userAction.put("description", editText.getText().toString());

                params.put("userAction", userAction);

                gigya.sendRequest("socialize.publishUserAction", params, true, null, null);
            }
        });

        return view;
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

}
