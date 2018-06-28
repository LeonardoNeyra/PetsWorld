package com.pw.dam.petsworld.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pw.dam.petsworld.HomeActivity;
import com.pw.dam.petsworld.PetsWorldSharedPreferences;
import com.pw.dam.petsworld.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
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
        final Spinner Peso = view.findViewById(R.id.spnPeso);
        final Spinner Raza = view.findViewById(R.id.snpRaza);
        final Spinner Tipo = view.findViewById(R.id.spnTipo);
        final RadioButton rbMacho = view.findViewById(R.id.rbtnMacho);
        final RadioButton rbHembra = view.findViewById(R.id.rbtnHembra);
        final TextView txtColor = view.findViewById(R.id.edtColor);
        final TextView txtContacto = view.findViewById(R.id.edtTelefono);
        final TextView txtComentario = view.findViewById(R.id.edtComentario);

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

                //Insertar uid en nodo post
                DatabaseReference postRef = db.getReference("post");
                DatabaseReference userPostRef = postRef.child(uid);
                DatabaseReference post = userPostRef.push();
                post.child("id").setValue(post.getKey());

                //Insertar mascota en nodo post
                DatabaseReference mascota = post.child("mascota");
                mascota.child("nombre").setValue(txtNombre.getText().toString());
                if (rbHembra.isChecked()){
                    mascota.child("genero").setValue(rbHembra.getText().toString());
                }
                else if (rbMacho.isChecked()){
                    mascota.child("genero").setValue(rbMacho.getText().toString());
                }
                mascota.child("tipo").setValue(Tipo.getSelectedItem().toString());
                mascota.child("peso").setValue(Peso.getSelectedItem().toString());
                mascota.child("raza").setValue(Raza.getSelectedItem().toString());
                mascota.child("color").setValue(txtColor.getText().toString());
                mascota.child("contacto").setValue(txtContacto.getText().toString());
                mascota.child("comentario").setValue(txtComentario.getText().toString());

                //Insertar usuario en nodo post
                DatabaseReference userRef = post.child("user");
                userRef.child("username").setValue(user.getDisplayName());

                //Insertar mascota en nodo mascota
                DatabaseReference mascotaRef = db.getReference("mascota");
                DatabaseReference mascota2 = mascotaRef.child(post.getKey());
                mascota2.child("nombre").setValue(txtNombre.getText().toString());
                if (rbHembra.isChecked()){
                    mascota2.child("genero").setValue(rbHembra.getText().toString());
                }
                else if (rbMacho.isChecked()){
                    mascota2.child("genero").setValue(rbMacho.getText().toString());
                }
                mascota2.child("tipo").setValue(Tipo.getSelectedItem().toString());
                mascota2.child("peso").setValue(Peso.getSelectedItem().toString());
                mascota2.child("raza").setValue(Raza.getSelectedItem().toString());
                mascota2.child("color").setValue(txtColor.getText().toString());
                mascota2.child("contacto").setValue(txtContacto.getText().toString());
                mascota2.child("comentario").setValue(txtComentario.getText().toString());

                //Insertar mascota y usuario en nodo posts
                DatabaseReference postsRef = db.getReference("posts");
                DatabaseReference posts = postsRef.child(post.getKey());
                posts.child("username").setValue(user.getDisplayName());
                posts.child("nombre").setValue(txtNombre.getText().toString());
                if (rbHembra.isChecked()){
                    posts.child("genero").setValue(rbHembra.getText().toString());
                }
                else if (rbMacho.isChecked()){
                    posts.child("genero").setValue(rbMacho.getText().toString());
                }
                posts.child("tipo").setValue(Tipo.getSelectedItem().toString());
                posts.child("peso").setValue(Peso.getSelectedItem().toString());
                posts.child("raza").setValue(Raza.getSelectedItem().toString());
                posts.child("color").setValue(txtColor.getText().toString());
                posts.child("contacto").setValue(txtContacto.getText().toString());
                posts.child("comentario").setValue(txtComentario.getText().toString());

                //Confirmacion de la accion
                Toast.makeText(getActivity(), "Se Guardo Correctamente", Toast.LENGTH_LONG).show();

                //Limpiar campos
                txtNombre.setText("");
                rbHembra.setChecked(false);
                rbMacho.setChecked(false);
                Tipo.setSelected(true);
                Peso.setSelected(true);
                Raza.setSelected(true);
                txtColor.setText("");
                txtContacto.setText("");
                txtComentario.setText("");
            }
        });

        //Acciones del boton cancelar
        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Limpiar campos
                txtNombre.setText("");
                rbHembra.setChecked(false);
                rbMacho.setChecked(false);
                Tipo.setSelected(true);
                Peso.setSelected(true);
                Raza.setSelected(true);
                txtColor.setText("");
                txtContacto.setText("");
                txtComentario.setText("");

                //Ir al HomeFragment
                //Intent intent = new Intent(getActivity(), HomeFragment.class);
                //startActivity(intent);
            }
        });

        return view;
    }
}
