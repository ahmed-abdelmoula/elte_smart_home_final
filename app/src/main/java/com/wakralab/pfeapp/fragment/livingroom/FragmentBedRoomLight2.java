package com.wakralab.pfeapp.fragment.livingroom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

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

public class FragmentBedRoomLight2 extends Fragment {
    private Switch mSwitch,mSwitchDark;
    private DatabaseReference mRef, mRef2,mRefB,mRefK,mRefL;
    private FirebaseDatabase database;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.blub_activity, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom").child("Lampe1");
        mRef2 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom");
        //mRef = database.getReference("BedRoom");

        mSwitch = view.findViewById(R.id.openlight);



        mSwitchDark = view.findViewById(R.id.darkmode);
        mRefB = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom");
        mRefK = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("BedRoom");
        mRefL = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen");


        mSwitchDark.setOnClickListener(view1 ->
        {
            if (!mSwitchDark.isChecked())
            {
                mRef2.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1,"ALL Lamps oFF");

                mRefB.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });
                popUpClass.showPopupWindow(view1,"ALL Lamps OFF");
                mRefK.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });
                popUpClass.showPopupWindow(view1,"ALL Lamps OFF");
                mRefL.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });
                popUpClass.showPopupWindow(view1,"ALL Lamps OFF");
            }
        });



        mSwitch.setOnClickListener(view1 -> {
           if (mSwitch.isChecked()){
                    mRef2.child("Lampe1").setValue(1).addOnSuccessListener(aVoid -> {

                    });
               PopUpClass popUpClass = new PopUpClass();
               popUpClass.showPopupWindow(view1,"Lampe ON");


                } else {
                    mRef2.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {
                        PopUpClass popUpClass = new PopUpClass();
                        popUpClass.showPopupWindow(view1,"Lampe OFF");

                });
            }
        });


        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);

                if (user.toString().equals("1")) {
                    mSwitch.setChecked(true);
                } else {
                    mSwitch.setChecked(false);

                }
                //  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TAG", "onCancelled", databaseError.toException());
            }
        });


    }


}
