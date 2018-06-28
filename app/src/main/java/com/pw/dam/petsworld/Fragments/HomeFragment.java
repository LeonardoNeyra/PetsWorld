package com.pw.dam.petsworld.Fragments;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
import com.pw.dam.petsworld.Clases.Posts;
import com.pw.dam.petsworld.PetsWorldSharedPreferences;
import com.pw.dam.petsworld.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FirebaseDatabase db;
    private PetsWorldSharedPreferences pref;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_list, container, false);
        db = FirebaseDatabase.getInstance();
        pref = new PetsWorldSharedPreferences(this.getActivity());
        String uid = pref.getString(pref.UID_LOGUED_USER);
        if (uid!=null){
            final DatabaseReference postRef = db.getReference("posts");
            FirebaseListOptions<Posts> options = new FirebaseListOptions.Builder<Posts>()
                    .setLayout(R.layout.posts_layout)
                    .setQuery(postRef.orderByKey(), Posts.class)
                    .setLifecycleOwner(getActivity())
                    .build();

            ListView listView = rootview.findViewById(R.id.listViewPost);
            ListAdapter adapter = new FirebaseListAdapter<Posts>(options) {
                protected void populateView(View view, final Posts posts, int v) {
                    ((TextView) view.findViewById(R.id.txtPostsNombre)).setText(String.valueOf(posts.getNombre()));
                    ((TextView) view.findViewById(R.id.txtPostsRaza)).setText(String.valueOf(posts.getRaza()));
                    ((TextView) view.findViewById(R.id.txtPostsUsername)).setText(String.valueOf(posts.getUsername()));
                    ((ImageButton) view.findViewById(R.id.btnPostsDetail)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            frag();
                            Toast.makeText(getActivity(),"Jelou",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            listView.setAdapter(adapter);
        }
        return rootview;
    }

    public void frag (){
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DetailFragment dialogFragment = new DetailFragment();
        dialogFragment.show(ft, "dialog");
    }
}
