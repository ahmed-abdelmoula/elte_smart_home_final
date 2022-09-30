package com.wakralab.pfeapp.fragment.bathroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakralab.pfeapp.R;
import com.wakralab.pfeapp.fragment.kitcheen.FragmentHeatingKitchen;
import com.wakralab.pfeapp.fragment.kitcheen.FragmentKitcheenPlugInn;
import com.wakralab.pfeapp.fragment.kitcheen.FragmentKitchenLight;
import com.wakralab.pfeapp.fragment.kitcheen.FragmentWindowKitchen;

public class FragmentbathroomM extends Fragment  implements View.OnClickListener {
    private ImageView mLight,mPlugIn,mHeating,mWindows,mStore;
    private FirebaseDatabase database;
    private DatabaseReference mRef,myRef2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_bathroomcomponent, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        database = FirebaseDatabase.getInstance();

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
        switch (view.getId())
        {

            case R.id.light_bed:
                showTopLevelFragment(new FragmentBathRoomLight());
                break;

            case R.id.plugin_bed:
                showTopLevelFragment(new FragmentBathRoomPlugIn());

                break;


            case R.id.heating_bed:

                showTopLevelFragment(new FragmentHeatingBathroom());

                break;

            case R.id.window_bed:

                showTopLevelFragment(new FragmentWindowBathroom());

                break;
            case R.id.window_store:

                showTopLevelFragment(new FragmentStoreB());

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
