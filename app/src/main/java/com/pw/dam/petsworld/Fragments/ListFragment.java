package com.pw.dam.petsworld.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pw.dam.petsworld.Clases.Mascota;
import com.pw.dam.petsworld.Clases.Post;
import com.pw.dam.petsworld.PetsWorldSharedPreferences;
import com.pw.dam.petsworld.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private FirebaseDatabase db;
    private PetsWorldSharedPreferences pref;

    public ListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_list, container, false);
        db = FirebaseDatabase.getInstance();
        pref = new PetsWorldSharedPreferences(this.getActivity());
        String uid = pref.getString(pref.UID_LOGUED_USER);
        if (uid!=null){
            final DatabaseReference postRef = db.getReference("post/" + uid);
            FirebaseListOptions<Post> options = new FirebaseListOptions.Builder<Post>()
                    .setLayout(R.layout.post_layout)
                    .setQuery(postRef.orderByKey(), Post.class)
                    .setLifecycleOwner(getActivity())
                    .build();

            ListView listView = rootview.findViewById(R.id.listViewPost);
            ListAdapter adapter = new FirebaseListAdapter<Post>(options) {
                protected void populateView(View view, final Post post, int v) {
                    ((TextView) view.findViewById(R.id.txtPost)).setText(String.valueOf(post.getId()));
                    /*((TextView) view.findViewById(R.id.txtRaza)).setText(String.valueOf(post.getMascota().getRaza()));
                    ((ImageButton) view.findViewById(R.id.btnEliminar)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            postRef.child(post.getId()).removeValue();
                        }
                    });*/
                }
            };
            listView.setAdapter(adapter);
        }
        return rootview;
    }
}
