package com.pw.dam.petsworld.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.*;
import com.pw.dam.petsworld.PetsWorldSharedPreferences;
import com.pw.dam.petsworld.Post;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance();
        pref = new PetsWorldSharedPreferences(this.getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        String uid = pref.getString(pref.UID_LOGUED_USER);

        if (uid!=null){
            DatabaseReference postRef = db.getReference("post/" + uid);
            FirebaseOptions<Post> options = new FirebaseOptions.Builder<Post>()
                    .setLayout(R.layout.post_layout)
                    .setQuery(postRef.orderByKey(), Post.class)
                    .setLifecycleOwner(this)
                    .build();
            ListView listView = view.findViewById(R.id.listViewPost);
            //ListAdapter adapter = new FirebaseListAdapter
        }

        return view;
    }

}
