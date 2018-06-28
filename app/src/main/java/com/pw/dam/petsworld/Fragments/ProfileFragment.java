package com.pw.dam.petsworld.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pw.dam.petsworld.MainActivity;
import com.pw.dam.petsworld.PetsWorldSharedPreferences;
import com.pw.dam.petsworld.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FirebaseDatabase db;
    private PetsWorldSharedPreferences pref;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);
        db = FirebaseDatabase.getInstance();
        pref = new PetsWorldSharedPreferences(this.getActivity());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        pref.setString(pref.UID_LOGUED_USER,user.getUid());

        ((TextView) rootview.findViewById(R.id.txtProfName)).setText(String.valueOf(user.getDisplayName()));
        ((TextView) rootview.findViewById(R.id.txtProfEmail)).setText(String.valueOf(user.getEmail()));

        rootview.findViewById(R.id.btnProfCerrarSesion).setOnClickListener(this);
        return rootview;
    }

    @Override
    public void onClick(View v) {
        cerrarSesion();
    }

    public void cerrarSesion(){
        pref.setString(pref.UID_LOGUED_USER, null);
        onResume();
    }
}
