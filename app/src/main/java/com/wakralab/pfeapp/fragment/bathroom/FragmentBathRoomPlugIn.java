package com.wakralab.pfeapp.fragment.bathroom;

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

public class FragmentBathRoomPlugIn extends Fragment {
    private Switch mOpenHair, mOpenLight;
    private DatabaseReference mRef, mRefHairD, mRefLight;
    private FirebaseDatabase database;
    private Boolean isOpened;
    private ImageView timer1,timer2;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.plugin_bathroom, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom").child("PlugIn");
        mRefHairD = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIn").child("HairD");
        mRefLight = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom")
                .child("PlugIn").child("Light");


        mOpenHair = view.findViewById(R.id.openhairdryer);
        mOpenLight = view.findViewById(R.id.openlight);
        timer1 = view.findViewById(R.id.timerlight);
        timer2 = view.findViewById(R.id.timerhair);




        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        long currentTime = System.currentTimeMillis();
        Long TVtime = sharedPreferences.getLong("BathLight",0);
        Long TVdesktop = sharedPreferences.getLong("BathHair",0);


        timer1.setOnClickListener(view1 ->
        {
            showPopupWindow("BathLight",view1);

        });


        timer2.setOnClickListener(view1 ->
        {
            showPopupWindow("BathHair",view1);



        });
        if (currentTime> TVtime)
        {
            mOpenLight.setChecked(false);
            sharedPreferences.edit().putLong("BathLight",0);
            sharedPreferences.edit().apply();

        }

        if (currentTime> TVdesktop)
        {
            mOpenHair.setChecked(false);
            sharedPreferences.edit().putLong("BathHair",0);
            sharedPreferences.edit().apply();
        }

        mOpenLight.setOnClickListener(view1 -> {
            if (mOpenLight.isChecked()) {

                mRef.child("Light").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Lampe is opened");
            } else {

                mRef.child("Light").setValue(0).addOnSuccessListener(runnable -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1, "Lampe is closed");
                });

            }
        });

        mOpenHair.setOnClickListener(view1 -> {
            if (mOpenHair.isChecked()) {

                mRef.child("HairD").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Hair Dr is ON");
            } else {

                mRef.child("HairD").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Hair Dr is OFF");

            }
        });


        mRefHairD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenHair.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenHair.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


        mRefLight.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenLight.setChecked(true);
                } else {
                    mOpenLight.setChecked(false);
                    isOpened = false;

                }
                //  }
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
            if (test.equals("BathLight"))
            {
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BathLight", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                if (!mOpenLight.isChecked())
                {
                    mOpenLight.setChecked(true);
                }
                popupWindow.dismiss();

            }
            else if (test.equals("BathHair"))
            {
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("BathHair", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                if (!mOpenHair.isChecked())
                {
                    mOpenHair.setChecked(true);
                }
                popupWindow.dismiss();

            }


        });

        Button timer4 = popupView.findViewById(R.id.timer4);
        timer4.setOnClickListener(v -> {

            if (test.equals("BathLight"))
            {

                sharedPreferences.edit().putString("BedTV", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

                if (!mOpenLight.isChecked())
                {
                    mOpenLight.setChecked(true);
                }

            }
            else if (test.equals("BathHair"))
            {
                sharedPreferences.edit().putString("BedDesktop", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                if (!mOpenHair.isChecked())
                {
                    mOpenHair.setChecked(true);
                }
            }


        });

        Button timer6 = popupView.findViewById(R.id.timer6);
        timer6.setOnClickListener(v -> {

            if (test.equals("BathLight"))
            {

                if (!mOpenLight.isChecked())
                {
                    mOpenLight.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("BedTV", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("BathHair"))
            {
                sharedPreferences.edit().putString("BedDesktop", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                popupWindow.dismiss();
                if (!mOpenHair.isChecked())
                {
                    mOpenHair.setChecked(true);
                }

            }


        });


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
