package com.pw.dam.petsworld.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pw.dam.petsworld.PetsWorldSharedPreferences;
import com.pw.dam.petsworld.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private Spinner Peso;
    private Spinner Raza;
    private Spinner Tipo;
    private FirebaseDatabase db;
    private PetsWorldSharedPreferences pref;

    public AddFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        pref = new PetsWorldSharedPreferences(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        final TextView txtNombre = view.findViewById(R.id.edtNombre);
        Peso = (Spinner) view.findViewById(R.id.spnPeso);
        Raza = (Spinner) view.findViewById(R.id.snpRaza);
        Tipo = (Spinner) view.findViewById(R.id.spnTipo);

        //Llenar los spinner
        ArrayAdapter<CharSequence> adapterPeso = ArrayAdapter.createFromResource(view.getContext()
                ,R.array.Peso,R.layout.support_simple_spinner_dropdown_item);
        Peso.setAdapter(adapterPeso);
        ArrayAdapter<CharSequence> adapterRaza = ArrayAdapter.createFromResource(view.getContext()
                ,R.array.Raza,R.layout.support_simple_spinner_dropdown_item);
        Raza.setAdapter(adapterRaza);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(view.getContext()
                ,R.array.Tipo,R.layout.support_simple_spinner_dropdown_item);
        Tipo.setAdapter(adapterTipo);

        //Acciones del Botón Aceptar
        view.findViewById(R.id.btnAceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                pref.setString(pref.UID_LOGUED_USER, user.getUid());
                String uid = pref.getString(pref.UID_LOGUED_USER);
                DatabaseReference usersRef = db.getReference("post");
                DatabaseReference loguedUserRef = usersRef.child(user.getUid());
                DatabaseReference users = loguedUserRef.push();

                DatabaseReference postRef = db.getReference("post");
                DatabaseReference userPostRef = postRef.child(uid);
                DatabaseReference post = userPostRef.push();

                DatabaseReference mascotaRef = db.getReference("mascota");
                DatabaseReference userMascotaRef = mascotaRef.child(uid);
                DatabaseReference mascota = userMascotaRef.push();

                mascota.child("nombre").setValue(txtNombre.getText().toString());
                post.child("mascota/nombre").setValue(txtNombre.getText().toString());
                users.child("user/username").setValue(user.getDisplayName());
            }
        });
        return view;
    }
}
