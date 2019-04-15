package com.isha.delhi.attendance;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IshaMainActivity extends AppCompatActivity implements VisitorFragment.VisitorFragmentListener, RegularVisitorFragment.RegularVisitorFragmentInteractionListener {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private IshaMainActivity activity;
    private IshaApp ishaApp;
    private VisitorFragment visitorFragment;
    private RegularVisitorFragment regularVisitorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isha_main);
        activity = this;
        visitorFragment = new VisitorFragment();
        regularVisitorFragment = new RegularVisitorFragment();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);


        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(visitorFragment, "Visitor");
        adapter.addFragment(regularVisitorFragment, "Regular Visitor");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSubmitDetails(AttendanceDetails attendanceDetails) {
        if (Utils.isNetworkAvailable(activity)) {
            SaveDetailsTask task = new SaveDetailsTask(attendanceDetails);
            task.execute();
        }else{
            Toast.makeText(activity,"Error: No Internet Connectivity.",Toast.LENGTH_LONG).show();
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private class SaveDetailsTask extends AsyncTask<Void, Void, Boolean> {

        private AttendanceDetails attendanceDetails;
        private ProgressDialog dialog;

        public SaveDetailsTask(AttendanceDetails attendanceDetails) {
            this.attendanceDetails = attendanceDetails;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Saving Details.");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean userUpdated = false;
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("id", "1MQCPjWcVU16U5hEQkiNe5aH2wKoLkZiNL-pastq3Erg");
            JsonObject jsonResponse = IshaHttp.postexecute(attendanceDetails, queryParams);
            if (jsonResponse != null) {
                Boolean isUserAdded = jsonResponse.get("userAdded").getAsBoolean();
                if (isUserAdded)
                    userUpdated = true;
            }

            return userUpdated;
        }

        @Override
        protected void onPostExecute(Boolean userAdded) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            String message = "";
            if (userAdded)
                message = "Details Saved.";
            else
                message = "Error: Please try again later";


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
            if(userAdded)
                visitorFragment.clearData();

        }
    }
}
