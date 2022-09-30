package com.wakralab.pfeapp.fragment.kitcheen;

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

public class FragmentKitcheenPlugInn extends Fragment {
    private Switch mOpenCofee, mMicroWave, mRefg;
    private DatabaseReference mRef, mRefCofee, mRefMicroWave;
    private FirebaseDatabase database;
    private Boolean isOpened;
    private ImageView mCoffee,mRefri,mMicro;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.plugin_kitcheen, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen").child("PlugIn");
        mRefCofee = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen")
                .child("PlugIn").child("CoffeeMaker");
        mRefMicroWave = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen")
                .child("PlugIn").child("Microwave");


        mOpenCofee = view.findViewById(R.id.opencoffe);
        mMicroWave = view.findViewById(R.id.openmicrowave);
        mRefg = view.findViewById(R.id.openref);



        mCoffee = view.findViewById(R.id.timercofee);
        mMicro = view.findViewById(R.id.timerwave);
        mRefri = view.findViewById(R.id.timerref);


        mCoffee.setOnClickListener(view1 ->
        {
            showPopupWindow("KitchenC",view1);

        });


        mMicro.setOnClickListener(view1 ->
        {
            showPopupWindow("KitchenM",view1);



        });
        mRefri.setOnClickListener(view1 ->
        {
            showPopupWindow("KitchenR",view1);



        });

        mOpenCofee.setOnClickListener(view1 -> {
            if (mOpenCofee.isChecked()) {

                mRef.child("CoffeeMaker").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "CoffeeMaker ON");
            } else {

                mRef.child("CoffeeMaker").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "CoffeeMaker OFF");

            }
        });

        mMicroWave.setOnClickListener(view1 -> {
            if (mMicroWave.isChecked()) {

                mRef.child("Microwave").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Microwave ON");
            } else {

                mRef.child("Microwave").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "MicroOnde OFF");

            }
        });


        mRefCofee.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mOpenCofee.setChecked(true);
                    isOpened = true;
                } else {
                    mOpenCofee.setChecked(false);
                    isOpened = false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


        mRefMicroWave.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mMicroWave.setChecked(true);
                } else {
                    mMicroWave.setChecked(false);
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
            if (test.equals("KitchenM"))
            {

                if (!mMicroWave.isChecked())
                {
                    mMicroWave.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("KitchenM", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            if (test.equals("KitchenC"))
            {
                if (!mOpenCofee.isChecked())
                {
                    mOpenCofee.setChecked(true);
                }

                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("KitchenC", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("KitchenR"))
            {
                if (!mRefg.isChecked())
                {
                    mRefg.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 2 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putLong("KitchenR", (System.currentTimeMillis()+(60*60*2000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }


        });

        Button timer4 = popupView.findViewById(R.id.timer4);
        timer4.setOnClickListener(v -> {

            if (test.equals("KitchenC"))
            {
                if (!mOpenCofee.isChecked())
                {
                    mOpenCofee.setChecked(true);
                }

                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("KitchenC", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("KitchenR"))
            {
                if (!mRefg.isChecked())
                {
                    mRefg.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("KitchenR", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }

            else if (test.equals("KitchenM"))
            {
                if (!mMicroWave.isChecked())
                {
                    mMicroWave.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 4 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("KitchenM", String.valueOf(System.currentTimeMillis()+(60*60*4000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }


        });

        Button timer6 = popupView.findViewById(R.id.timer6);
        timer6.setOnClickListener(v -> {

            if (test.equals("KitchenC"))
            {
                if (!mOpenCofee.isChecked())
                {
                    mOpenCofee.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();


                sharedPreferences.edit().putString("KitchenC", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("KitchenM"))
            {
                if (!mMicroWave.isChecked())
                {
                    mMicroWave.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("KitchenM", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

            }
            else if (test.equals("KitchenR"))
            {
                if (!mRefg.isChecked())
                {
                    mRefg.setChecked(true);
                }
                Toast.makeText(getActivity(),"TimerSet for 6 Hours",Toast.LENGTH_SHORT).show();

                sharedPreferences.edit().putString("KitchenR", String.valueOf(System.currentTimeMillis()+(60*60*6000)));
                sharedPreferences.edit().commit();
                popupWindow.dismiss();

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
