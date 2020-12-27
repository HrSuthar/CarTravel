package com.example.cartravels.fragmentUi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartravels.R;
import com.example.cartravels.event.eventAdapter;
import com.example.cartravels.event.eventAddDialog;
import com.example.cartravels.event.eventDesc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements eventAddDialog.eventAddListener {
    DatabaseReference myRef;
    ArrayList<eventDesc> viewCard;
    eventAdapter mAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences getPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getPref = HomeFragment.this.requireActivity().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        myRef= FirebaseDatabase.getInstance().getReference().child("Events");

        createView(root);

        if(getPref.getString("UserType","-").equals("Travel Agent")) {
            FloatingActionButton fab = root.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDialog();
                }
            });
        }
        return root;
    }

    private void OpenDialog(){
        eventAddDialog eventDialog =new eventAddDialog();
        eventDialog.show(getChildFragmentManager(),"eventDialog");
    }

    public void createView(final View root) {
        viewCard = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    viewCard.add(ds.getValue(eventDesc.class));
                    recyclerBuild(root);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void recyclerBuild(View root) {
        recyclerView = root.findViewById(R.id.RecyclerEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new eventAdapter(viewCard);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getEventInfo(final String eventFrom, final String eventTo, final String eventDate, final String eventVehicle, final String eventVehicleNo, final long seat_Nums) {
        final long[] cnt_event = {0};
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cnt_event[0] = dataSnapshot.getChildrenCount();
                ++cnt_event[0];
                myRef.child("event: 0"+cnt_event[0]).setValue(new eventDesc(eventFrom, eventTo, eventDate, eventVehicle, eventVehicleNo, seat_Nums));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        viewCard.add(new eventDesc(eventFrom, eventTo, eventDate, eventVehicle, eventVehicleNo, seat_Nums));
    }

}