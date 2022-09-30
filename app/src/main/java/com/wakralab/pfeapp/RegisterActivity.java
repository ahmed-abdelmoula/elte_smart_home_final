package com.wakralab.pfeapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPass, mSecondPass;
    private Button mRegister;
    private ProgressDialog progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mRegister.setOnClickListener(this);

    }

    private void initView() {

        firebaseAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.register_pseudo);
        mPass = findViewById(R.id.register_mdp);
        mSecondPass = findViewById(R.id.register_mdp2);
        mRegister = findViewById(R.id.activity_login_connect);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String userProvider = firebaseAuth.getCurrentUser().getProviderId();
            List<? extends UserInfo> infos = currentUser.getProviderData();


            startActivity(new Intent(this, MainBottomActivity.class));
//            startActivity(new Intent(this, ActivityMainSearch.class).setFlags(FLAG_ACTIVITY_CLEAR_TASK));

        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_connect:
                registerFirebase();
                break;


        }
    }

    private void registerFirebase() {
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        final String userEmail = mEmail.getText().toString().trim();
        final String userPassword = mPass.getText().toString().trim();
        final String userPass2 = mSecondPass.getText().toString().trim();

        mEmail.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        mPass.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
        mSecondPass.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);


        if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userPass2)) {
            showToast("Remplissez les champs");
            mEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            mPass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            mSecondPass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            return;

        }


        progressBar = new ProgressDialog(RegisterActivity.this);
        progressBar.setMessage("S'il vous plaît, attendez...");
        progressBar.show();

        //register user
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register Activity", "New user registration: " + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            RegisterActivity.this.showToast("Authentification échouée " + task.getException());

                        } else {
//                            saveToDataBase(userName, userEmail, userPassword);
                            Intent intent = new Intent(getApplicationContext(), MainBottomActivity.class);
                            intent.putExtra("userprofile", "ttt");



                            myEdit.putString("firstTime", "yes");

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                            myEdit.commit();
                            Map<String, Object> user = new HashMap<>();

                            user.put("surname", "");
                            user.put("address", "");
                            user.put("email", mEmail.getText().toString());

                            user.put("fcmToken", "");
                            user.put("fcmTokenUpdate", "");
                            user.put("creationDate", "");


                            user.put("codepostal", "");

                            user.put("pays", "");
                            user.put("villes", "");
                            user.put("photoURL", "");

                            // Add a new document with a generated ID
                            db.collection("usersData").document(firebaseAuth.getUid()).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    });


                            startActivity(intent);


                            RegisterActivity.this.finish();
                        }
                        progressBar.dismiss();

                    }
                });

    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

}

