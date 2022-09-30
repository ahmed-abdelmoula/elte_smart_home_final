package com.wakralab.pfeapp.fragment.bathroom;

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

public class FragmentWindowBathroom extends Fragment {
    private Switch mSwitch,mSwitchDoor;
    private DatabaseReference mRef, mRef2,mRefD;
    private FirebaseDatabase database;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.window_activity, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom").child("Window");
        mRefD = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom").child("Door");
        mRef2 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom");
        //mRef = database.getReference("BedRoom");

        mSwitch = view.findViewById(R.id.openwindow);
        mSwitchDoor = view.findViewById(R.id.opendoor);
        mSwitch.setOnClickListener(view1 -> {
           if (mSwitch.isChecked()){
                    mRef2.child("Window").setValue(1).addOnSuccessListener(aVoid -> {
                    });
               PopUpClass popUpClass = new PopUpClass();
               popUpClass.showPopupWindow(view1,"Window  is opened");


                } else {
                    mRef2.child("Window").setValue(0).addOnSuccessListener(aVoid -> {


                });
               PopUpClass popUpClass = new PopUpClass();
               popUpClass.showPopupWindow(view1,"Window is closed");
            }
        });

        mSwitchDoor.setOnClickListener(view1 -> {
           if (mSwitchDoor.isChecked()){
               mRef2.child("Door").setValue(1).addOnSuccessListener(aVoid -> {
                    });
               PopUpClass popUpClass = new PopUpClass();
               popUpClass.showPopupWindow(view1,"Door is opened");


                } else {
               mRef2.child("Door").setValue(0).addOnSuccessListener(aVoid -> {


                });
               PopUpClass popUpClass = new PopUpClass();
               popUpClass.showPopupWindow(view1,"Door is closed");
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

        mRefD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                Long user = dataSnapshot.getValue(Long.class);
                if (user.toString().equals("1")) {
                    mSwitchDoor.setChecked(true);
                } else {
                    mSwitchDoor.setChecked(false);

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
