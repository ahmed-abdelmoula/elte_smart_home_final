package com.wakralab.pfeapp.fragment.bedroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakralab.pfeapp.R;

import vn.nms.dynamic_seekbar.DynamicSeekBarView;

public class FragmentStoreBe extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private DynamicSeekBarView mSwitch;
    private DatabaseReference mRef;
    private FirebaseDatabase database;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_store, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        mSwitch = view.findViewById(R.id.temp);


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("BedRoom");




        mSwitch.setMax(100);
        mSwitch.setSeekBarChangeListener(this);


    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mSwitch.setInfoText("level " + i + "%", i);
        mRef.child("Store").setValue(i).addOnSuccessListener(aVoid -> {
        });
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
