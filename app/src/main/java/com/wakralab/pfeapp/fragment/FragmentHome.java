package com.wakralab.pfeapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakralab.pfeapp.R;
import com.wakralab.pfeapp.fragment.bathroom.FragmentBathRoomPlugIn;
import com.wakralab.pfeapp.fragment.bathroom.FragmentbathroomM;
import com.wakralab.pfeapp.fragment.bedroom.FragmentBedRoom;
import com.wakralab.pfeapp.fragment.kitcheen.FragmentKitcheenM;
import com.wakralab.pfeapp.fragment.livingroom.FragmentLivingRoom;
import com.wakralab.pfeapp.utils.PopUpClass;

public class FragmentHome extends Fragment implements View.OnClickListener {
    private ImageView mBedRoom, mLivingRoom, mKitchen, mBathroom;
    private DatabaseReference mRef,mRef2,mRef3,mRef4;
    private FirebaseDatabase database;
    private Switch mSwitchDark;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        initView(view);
        return view;


    }


    private void initView(View view) {


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("BedRoom");
        mRef2 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen");
        mRef3 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom");
        mRef4 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom");



        mBathroom = view.findViewById(R.id.bathroom);
        mLivingRoom = view.findViewById(R.id.livingroom);
        mKitchen = view.findViewById(R.id.kitchenn);
        mBedRoom = view.findViewById(R.id.bedroom);

        mBedRoom.setOnClickListener(this);
        mLivingRoom.setOnClickListener(this);
        mKitchen.setOnClickListener(this);
        mBathroom.setOnClickListener(this);
        mSwitchDark = view.findViewById(R.id.darkmode);

        mSwitchDark.setOnClickListener(view1 ->
        {
            if (mSwitchDark.isChecked())
            {
                mRef2.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });

                mRef3.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });
                mRef4.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {

                });
                mRef.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view1,"All lamp  are closed");
                });
            }
        

        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bathroom:
                showTopLevelFragment(new FragmentbathroomM());

                break;

            case (R.id.livingroom):
                showTopLevelFragment(new FragmentLivingRoom());

                break;

            case R.id.kitchenn:
                showTopLevelFragment(new FragmentKitcheenM());


                break;


            case R.id.bedroom:
                showTopLevelFragment(new FragmentBedRoom());
                break;
        }
    }

    public void showTopLevelFragment(Fragment fragment) {
        // Use the fragment manager to dynamically change the fragment displayed in the FrameLayout.
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_host_main, fragment)
                .commit();


    }
}
