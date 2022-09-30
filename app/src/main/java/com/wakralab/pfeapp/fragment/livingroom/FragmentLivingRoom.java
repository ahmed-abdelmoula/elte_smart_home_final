package com.wakralab.pfeapp.fragment.livingroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakralab.pfeapp.R;

public class FragmentLivingRoom extends Fragment implements View.OnClickListener {

    private ImageView mLight, mPlugIn, mHeating, mWindows,mStore;
    private FirebaseDatabase database;
    private DatabaseReference mRef, myRef2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_livingcomponent, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("BedRoom");
        ;

        mLight = view.findViewById(R.id.light_bed);
        mPlugIn = view.findViewById(R.id.plugin_bed);
        mHeating = view.findViewById(R.id.heating_bed);
        mWindows = view.findViewById(R.id.window_bed);


        mLight.setOnClickListener(this);
        mPlugIn.setOnClickListener(this);
        mHeating.setOnClickListener(this);
        mWindows.setOnClickListener(this);
        mStore = view.findViewById(R.id.window_store);

        mStore.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.light_bed:
                showTopLevelFragment(new FragmentBedRoomLight2());
                break;

            case R.id.plugin_bed:
                showTopLevelFragment(new FragmentBedRoomPlugIn2());

                break;


            case R.id.heating_bed:

                showTopLevelFragment(new FragmentHeating2());

                break;

            case R.id.window_bed:

                showTopLevelFragment(new FragmentWindow2());

                break;
            case R.id.window_store:

                showTopLevelFragment(new FragmentStoreL());

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