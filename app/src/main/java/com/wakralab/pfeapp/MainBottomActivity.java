package com.wakralab.pfeapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wakralab.pfeapp.fragment.bedroom.FragmentBedRoom;
import com.wakralab.pfeapp.fragment.FragmentHome;
import com.wakralab.pfeapp.fragment.livingroom.FragmentLivingRoom;
import com.wakralab.pfeapp.fragment.FragmentProfil;
import com.wakralab.pfeapp.utils.PopUpClass;

public class MainBottomActivity extends AppCompatActivity {
    private static final String TAG = "ActivityMainSearch";
    public static BottomNavigationView bottomNavigationView;
    private SharedPreferences mSharedPref;
    private DatabaseReference mRef,mRef2,mRef3,mRef4;
    private FirebaseDatabase database;
    private Switch mSwitchDark;




    public void showTopLevelFragment(Fragment fragment) {
        // Use the fragment manager to dynamically change the fragment displayed in the FrameLayout.
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_host_main, fragment)
                .commit();


        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("BedRoom");
        mRef2 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Kitcheen");
        mRef3 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("Bathroom");
        mRef4 = database.getReference("SmartHome").child(FirebaseAuth.getInstance().getUid()).child("LivingRoom");


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String s1 = sh.getString("firstTime", "");

        if (s1.equals("yes")) {


            mRef.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef2.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef3.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef4.child("Lampe1").setValue(0).addOnSuccessListener(aVoid -> {
            });


            mRef.child("Store").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef2.child("Store").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef3.child("Store").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef4.child("Store").setValue(0).addOnSuccessListener(aVoid -> {
            });





            mRef.child("Temp").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef.child("Window").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef.child("Door").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef3.child("Window").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef3.child("Door").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef2.child("Window").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef2.child("Door").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef4.child("Temp").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef4.child("Window").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef4.child("Door").setValue(0).addOnSuccessListener(aVoid -> {
            });


            mRef2.child("PlugIn").child("Refrigarator").setValue(1).addOnSuccessListener(aVoid -> {
            });

            mRef2.child("PlugIn").child("CoffeeMaker").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef2.child("PlugIn").child("Microwave").setValue(0).addOnSuccessListener(aVoid -> {
            });


            mRef3.child("PlugIn").child("HairD").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef3.child("PlugIn").child("Light").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef.child("PlugIn").child("Desktop").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef.child("PlugIn").child("Laptop").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef.child("PlugIn").child("TV").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef.child("AC").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef.child("Heating").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef3.child("AC").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef3.child("Heating").setValue(0).addOnSuccessListener(aVoid -> {
            });


            mRef2.child("AC").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef2.child("Heating").setValue(0).addOnSuccessListener(aVoid -> {
            });



            mRef4.child("PlugIn").child("Desktop").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef4.child("PlugIn").child("Laptop").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef4.child("PlugIn").child("TV").setValue(0).addOnSuccessListener(aVoid -> {
            });

            mRef4.child("AC").setValue(0).addOnSuccessListener(aVoid -> {
            });
            mRef4.child("Heating").setValue(0).addOnSuccessListener(aVoid -> {
            });

            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("firstTime", "noo");

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
            myEdit.commit();


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        //  getAllJobFromFireBase();
        Log.d("Class", TAG);
        // Retrieve a reference to the BottomNavigationView and listen for click events.
        Intent in = getIntent();
        Bundle content = in.getExtras();
        // check null


        mSharedPref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.bottom_navigation_main);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        String nbFav = mSharedPref.getString("Fav", "");
        Log.d("favnumber", nbFav);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:
                        //   startActivity(new Intent(ActivityMainSearch.this, MainActivity.class));
                        MainBottomActivity.this.showTopLevelFragment(new FragmentHome());
                        return true;

                        case R.id.profil:
                        //   startActivity(new Intent(ActivityMainSearch.this, MainActivity.class));
                        MainBottomActivity.this.showTopLevelFragment(new FragmentProfil());
                        return true;

                    case R.id.bedroom:
                        MainBottomActivity.this.showTopLevelFragment(new FragmentBedRoom());

                        return true;
                    case R.id.living:
                        MainBottomActivity.this.showTopLevelFragment(new FragmentLivingRoom());
                        //   startActivity(new Intent(ActivityMainSearch.this, testScrool.class));

                        return true;


                    default:
                        MainBottomActivity.this.showTopLevelFragment(new FragmentHome());
                }

                return true;
            }
        });
       showTopLevelFragment(new FragmentHome());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
