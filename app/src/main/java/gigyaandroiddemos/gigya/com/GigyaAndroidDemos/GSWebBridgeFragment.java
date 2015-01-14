package gigyaandroiddemos.gigya.com.GigyaAndroidDemos;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.GSWebBridge;
import com.gigya.socialize.android.event.GSWebBridgeListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GSWebBridgeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GSWebBridgeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GSWebBridgeFragment extends Fragment {
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
     * @return A new instance of fragment GSWebBridgeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GSWebBridgeFragment newInstance(String param1, String param2) {
        GSWebBridgeFragment fragment = new GSWebBridgeFragment();
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

        View layout = inflater.inflate(R.layout.fragment_gsweb_bridge, container, false);
        WebView webView = (WebView) layout.findViewById(R.id.webView);
        String url = "http://demos.gigya-cs.com/main_demo.html";
        GSWebBridge.attach(webView, new GSWebBridgeListener() {
            @Override
            public void onPluginEvent(WebView webView, GSObject gsObject, String s) {

            }
        });
        GSWebBridge.handleUrl(webView, url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new webClient());
        webView.loadUrl(url);

        // Inflate the layout for this fragment
        return layout;
    }

    private class webClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            if (GSWebBridge.handleUrl(webView, url)) {
                return true;
            }
            return false;
        }
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
