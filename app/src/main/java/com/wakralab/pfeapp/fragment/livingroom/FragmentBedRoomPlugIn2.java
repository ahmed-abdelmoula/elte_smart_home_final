package com.wakralab.pfeapp.fragment.livingroom;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakralab.pfeapp.R;
import com.wakralab.pfeapp.utils.PopUpClass;

import static android.content.Context.MODE_PRIVATE;

public class FragmentBedRoomPlugIn2 extends Fragment {
    private Switch mSwitchTV, mSwitchLapTop, mDesktop;
    private DatabaseReference mRef, mRefTV, mRefLaptop, mRefDesktop;
    private FirebaseDatabase database;
    private Boolean isOpened;
    private ImageView m2,m4,m6;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.plugin_activity, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom").child("PlugIn");
        mRefTV = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("PlugIn").child("TV");
        mRefLaptop = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("PlugIn").child("Laptop");
        mRefDesktop = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom")
                .child("PlugIn").child("Desktop");


        m2 = view.findViewById(R.id.timertv);
        m4 = view.findViewById(R.id.timerdes);
        m6 = view.findViewById(R.id.timerlaptop);



        m2.setOnClickListener(view1 ->
        {
            showPopupWindow("Bedtv",view1);

        });


        m4.setOnClickListener(view1 ->
        {
            showPopupWindow("Beddeskt",view1);



        });
        m6.setOnClickListener(view1 ->
        {

            showPopupWindow("Bedlaptop",view1);

        });


        mSwitchTV =view.findViewById(R.id.opentv);
        mSwitchLapTop = view.findViewById(R.id.openlaptop);
        mDesktop = view.findViewById(R.id.openDesktop);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();
        Long TVtime = sharedPreferences.getLong("BedTV",0);
        Long TVdesktop = sharedPreferences.getLong("BedDesktop",0);
        Long TVlaptop = sharedPreferences.getLong("BedLaptop",0);

        if (currentTime> TVtime)
        {
            mSwitchTV.setChecked(false);
            sharedPreferences.edit().putLong("BedTV",0);
            sharedPreferences.edit().apply();

        }

        if (currentTime> TVdesktop)
        {
            mDesktop.setChecked(false);
            sharedPreferences.edit().putLong("BedDesktop",0);
            sharedPreferences.edit().apply();
        }

        if (currentTime> TVlaptop)
        {
            mSwitchLapTop.setChecked(false);
            sharedPreferences.edit().putLong("BedLaptop",0);
            sharedPreferences.edit().apply();

        }
        mSwitchTV.setOnClickListener(view1 -> {
            if (mSwitchTV.isChecked()) {

                mRef.child("TV").setValue(1).addOnSuccessListener(runnable -> {
                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1,"TV is opened");
            } else {

                mRef.child("TV").setValue(0).addOnSuccessListener(runnable -> {
                });

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1,"TV is closed");
            }
        });


        mSwitchLapTop.setOnClickListener(view1 -> {
            if (mSwitchLapTop.isChecked()) {

                mRef.child("Laptop").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1,"Laptop is ON");
            } else {

                mRef.child("Laptop").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1,"Laptop is OFF");
                });

            }
        });

        mDesktop.setOnClickListener(view1 -> {
            if (mDesktop.isChecked()) {

                mRef.child("Desktop").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1,"Desktop ON");
            } else {

                mRef.child("Desktop").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1,"Desktop Off");

            }
        });


        mRefTV.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mSwitchTV.setChecked(true);
                    isOpened = true;
                } else {
                    mSwitchTV.setChecked(false);
                    isOpened = false;

                }
                //  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


        mRefLaptop.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mSwitchLapTop.setChecked(true);
                } else {
                    mSwitchLapTop.setChecked(false);
                    isOpened = false;

                }
                //  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });

        mRefDesktop.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mDesktop.setChecked(true);
                } else {
                    mDesktop.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


    }




    public void showPopupWindow(String test, View view) {


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_timer, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler



        Button buttonEdit = popupView.findViewById(R.id.timer2);
        buttonEdit.setOnClickListener(v -> {

            //As an example, display the message
            if (test.equals("Bedtv"))
            {
                if (!mSwitchTV.isChecked())
                {
                    mSwitchTV.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();


                sharedPreferences.edit().putLong("BedTV", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("Beddeskt"))
            {
                if (!mDesktop.isChecked())
                {
                    mDesktop.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BedDesktop", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else {
                if (!mSwitchLapTop.isChecked())
                {
                    mSwitchLapTop.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BedLaptop", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }

        });

        Button timer4 = popupView.findViewById(R.id.timer4);
        timer4.setOnClickListener(v -> {

            if (test.equals("Bedtv"))
            {
                if (!mSwitchTV.isChecked())
                {
                    mSwitchTV.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedTV", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("Beddeskt"))
            {
                if (!mDesktop.isChecked())
                {
                    mDesktop.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedDesktop", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else {
                if (!mSwitchLapTop.isChecked())
                {
                    mSwitchLapTop.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedLaptop", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }

        });

        Button timer6 = popupView.findViewById(R.id.timer6);
        timer6.setOnClickListener(v -> {

            if (test.equals("Bedtv"))
            {
                if (!mSwitchTV.isChecked())
                {
                    mSwitchTV.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedTV", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("Beddeskt"))
            {
                if (!mDesktop.isChecked())
                {
                    mDesktop.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedDesktop", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else {
                if (!mSwitchLapTop.isChecked())
                {
                    mSwitchLapTop.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedLaptop", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();
            }

        });

        if (test.equals("Bedlaptop"))
        {
            timer4.setVisibility(View.GONE);
            timer6.setVisibility(View.GONE);
        }
        else {
            timer4.setVisibility(View.VISIBLE);
            timer6.setVisibility(View.VISIBLE);
        }

        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
