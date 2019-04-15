package com.isha.delhi.attendance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class VisitorFragment extends Fragment {


    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPhoneEditText;
    private EditText mPincodeEditText;
    private Spinner mVisitPurpose;
    private Button submit;
    private CheckBox isFrequent;


    private VisitorFragmentListener mListener;

    public VisitorFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visitor, container, false);
        getActivity().setTitle("Isha");
        mNameEditText = view.findViewById(R.id.name);
        mEmailEditText = view.findViewById(R.id.email);
        mPhoneEditText = view.findViewById(R.id.phone);
        mPincodeEditText = view.findViewById(R.id.pincode);
        mVisitPurpose = view.findViewById(R.id.purpose_of_visit);
        submit = view.findViewById(R.id.submit);
        isFrequent = view.findViewById(R.id.checkbox_frequent);

        String[] items = new String[]{"Volunteer", "Devi", "Visitor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        mVisitPurpose.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEditTextValid(mNameEditText))
                    return;
                if (!isEditTextValid(mEmailEditText))
                    return;
                if (!isEditTextValid(mPhoneEditText))
                    return;
                if (!isEditTextValid(mPincodeEditText))
                    return;

                submit();

            }
        });

        return view;
    }

    private void submit() {
        String isFrequentVal = "N";
        if (isFrequent.isChecked())
            isFrequentVal = "Y";
        AttendanceDetails attendanceDetails = new AttendanceDetails(mNameEditText.getText().toString(), mEmailEditText.getText().toString(), mPhoneEditText.getText().toString(), mPincodeEditText.getText().toString(), mVisitPurpose.getSelectedItem().toString(), isFrequentVal);
        mListener.onSubmitDetails(attendanceDetails);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VisitorFragmentListener) {
            mListener = (VisitorFragmentListener) context;
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

    public void clearData() {
        mNameEditText.setText("");
        mEmailEditText.setText("");
        mPhoneEditText.setText("");
        mPincodeEditText.setText("");
        mNameEditText.setText("");
        mVisitPurpose.setSelection(0);
        isFrequent.setChecked(false);
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
    public interface VisitorFragmentListener {
        // TODO: Update argument type and name
        void onSubmitDetails(AttendanceDetails attendanceDetails);
    }

    private boolean isEditTextValid(EditText editText) {
        if (editText.getText().length() == 0) {

            Toast.makeText(getActivity(),editText.getHint() + " cannot be blank.",Toast.LENGTH_LONG).show();
            //editText.setError(editText.getHint() + " cannot be blank.");
            editText.requestFocus();
            return false;
        }
        return true;
    }

}
