package com.wakralab.pfeapp.fragment.kitcheen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakralab.pfeapp.R;
import com.wakralab.pfeapp.utils.PopUpClass;

import java.util.concurrent.ThreadLocalRandom;

import vn.nms.dynamic_seekbar.DynamicSeekBarView;

public class FragmentHeatingKitchen extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private DynamicSeekBarView mSwitch;
    private DatabaseReference mRef, mRef2, mRefAC, mRefHeating;
    private FirebaseDatabase database;
    private TextView mCurrentTemp;
    private Switch mSwitchAC, mSwitchHeating;
    private ImageView mRefresh;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_temperature, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        mSwitch = view.findViewById(R.id.temp);
        mCurrentTemp = view.findViewById(R.id.current_temp);
        mRefresh = view.findViewById(R.id.refreshTemp);
        mSwitchAC = view.findViewById(R.id.openac);
        mSwitchHeating = view.findViewById(R.id.opneHeater);

        mRefresh.setOnClickListener(view1 ->
                randomTemp(18, 35));

        Integer randomValue = randomTemp(18, 35);
        mCurrentTemp.setText(randomValue.toString());


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen");


        mRefAC = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen")
               .child("AC");
        mRefHeating = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen")
               .child("Heating");

        //mRef = database.getReference("BedRoom");
        mSwitch.setMax(40);
        mSwitch.setSeekBarChangeListener(this);

        // mSwitch.s


        mSwitchAC.setOnClickListener(view1 -> {
            if (mSwitchAC.isChecked()) {

                mRef.child("AC").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "AC ON");
            } else {

                mRef.child("AC").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "AC OFF");

            }
        });
        mSwitchHeating.setOnClickListener(view1 -> {
            if (mSwitchHeating.isChecked()) {

                mRef.child("Heating").setValue(1).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Heating ON");
            } else {

                mRef.child("Heating").setValue(0).addOnSuccessListener(runnable -> {

                });
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(view1, "Heating OF");

            }
        });


    }

    private Integer randomTemp(Integer min, Integer max) {

        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        Integer number = randomNum;
        mCurrentTemp.setText(number.toString());
        if (randomNum > 20 && randomNum < 26) {
            mSwitchAC.setEnabled(true);
        } else {
            mSwitchAC.setEnabled(false);

        }
        if (randomNum> 25 )
        {
            mSwitchAC.setEnabled(true);
            mSwitchAC.setChecked(true);
        }
        if (randomNum<22)
        {
            mSwitchHeating.setEnabled(true);
            mSwitchHeating.setChecked(true);
        }
        if (randomNum> 25 )
        {
            if (mSwitchHeating.isChecked())
            {
                mSwitchHeating.setChecked(false);
                mSwitchAC.setChecked(true);
            }
            else {
                mSwitchAC.setEnabled(true);
                mSwitchAC.setChecked(true);
            }
        }
        if ( 22 < randomNum && randomNum <26)
        {
            mSwitchAC.setChecked(false);
            mSwitchHeating.setChecked(false);

        }
        return randomNum;

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mSwitch.setInfoText("Temperature " + i + "%", i);
        mRefHeating.child("Temp").setValue(i).addOnSuccessListener(aVoid -> {
        });
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
