package com.isha.delhi.attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegularVisitorFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RegularVisitorFragment extends Fragment {


    private RegularVisitorFragmentInteractionListener mListener;
    private EditText mPhoneEditText;
    private Button mSubmitPhone;
    private IshaMainActivity activity;

    public RegularVisitorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (IshaMainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regular_visitor, container, false);
        mPhoneEditText = view.findViewById(R.id.regular_visitor_phone);
        mSubmitPhone = view.findViewById(R.id.submit_regular_visitors);
        getActivity().setTitle("Isha");
        mSubmitPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhoneEditText.getText().length() == 0) {
                    Toast.makeText(activity, "Please enter your Mobile Number.", Toast.LENGTH_LONG).show();
                    mPhoneEditText.requestFocus();
                    return;
                }
                if (Utils.isNetworkAvailable(activity)) {
                    new FetchVisitorByPhone().execute(mPhoneEditText.getText().toString());
                }else{
                    Toast.makeText(activity,"Error: No Internet Connectivity.",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegularVisitorFragmentInteractionListener) {
            mListener = (RegularVisitorFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement VisitorFragmentListener");
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
    public interface RegularVisitorFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSubmitDetails(AttendanceDetails attendanceDetails);
    }

    private class FetchVisitorByPhone extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog;

        public FetchVisitorByPhone() {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Fetching Details");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... phone) {
            boolean recordUpdated = false;

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("id", "1MQCPjWcVU16U5hEQkiNe5aH2wKoLkZiNL-pastq3Erg");
            queryParams.put("phone", phone[0]);
            JsonObject jsonResponse = IshaHttp.getexecute(queryParams);
            if (jsonResponse != null) {
                recordUpdated = jsonResponse.get("userFound").getAsBoolean();

            }
            return recordUpdated;
        }

        @Override
        protected void onPostExecute(Boolean recordUpdated) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            String message="";
            if(recordUpdated)
                message = "Details Saved.";
            else
                message = "No records found with this number.";


            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Isha")
                    .setMessage(message)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            if(recordUpdated)
                mPhoneEditText.setText("");
        }
    }
}
