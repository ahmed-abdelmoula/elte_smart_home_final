package com.wakralab.pfeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wakralab.pfeapp.MainActivity;
import com.wakralab.pfeapp.R;

public class FragmentProfil extends Fragment {
    private TextView mEmail;
    private Button mSignOut;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_userprofil, container, false);
        initView(view);
        return view;


    }

    private void initView(View view) {

        mEmail = view.findViewById(R.id.useremail_profil);
        mSignOut = view.findViewById(R.id.logout_profil);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            mEmail.setText(userEmail);
        } else {
            // No user is signed in
        }
        mSignOut.setOnClickListener(view1 ->
                {
                    FirebaseAuth.getInstance().signOut();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                }
                );

    }
}
