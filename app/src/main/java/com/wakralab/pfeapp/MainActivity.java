package com.wakralab.pfeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mConnect;
    private EditText mEmail, mPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView mCreateAccount;
    public static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
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

    private void initView() {


        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mConnect = findViewById(R.id.activity_login_connect);
        mEmail = findViewById(R.id.login_email);
        mPass = findViewById(R.id.login_mdp);
        mCreateAccount = findViewById(R.id.create_account);

        mConnect.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_connect:
                login();
                break;

            case R.id.create_account:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    private void login() {


        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext



        // Setting up message in progressDialog.
        progressDialog = new ProgressDialog(MainActivity.this);

      //  mEmail.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
      //  mPass.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);

        if (TextUtils.isEmpty(mPass.getText().toString().trim()) && TextUtils.isEmpty(mPass.getText().toString().trim())) {
            showToast("Entrez votre adresse e-mail et votre mot de passe!");
            mEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            mPass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            return;
        }


        if (TextUtils.isEmpty(mEmail.getText().toString().trim())) {
            showToast("Entrer l'adresse e-mail!");
            mEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            return;
        }
        if (!mEmail.getText().toString().trim().matches(emailPattern)) {

            showToast("Adresse e-mail non valide");
            mEmail.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            return;
        }

        if (TextUtils.isEmpty(mPass.getText().toString().trim())) {
            showToast("Entrer le mot de passe !");
            mPass.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            return;
        }


        progressDialog.setMessage("S'il vous plaît, attendez");

        // Showing progressDialog.
        progressDialog.show();
        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        firebaseAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(), mPass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If task done Successful.
                        if (task.isSuccessful()) {

                            // Hiding the progress dialog.
                            progressDialog.dismiss();
                            // Closing the current Login Activity.
                            myEdit.putString("firstTime", "yes");

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                            myEdit.commit();
                            finish();

                            // Opening the UserProfileActivity.
                            Intent intent = new Intent(MainActivity.this, MainBottomActivity.class);
                            startActivity(intent);
                        } else {

                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(MainActivity.this, "Courriel ou mot de passe invalide, veuillez réessayer", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }


}


