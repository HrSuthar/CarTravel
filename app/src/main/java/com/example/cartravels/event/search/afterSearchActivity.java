package com.example.cartravels.event.search;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartravels.R;
import com.example.cartravels.event.eventAdapter;
import com.example.cartravels.event.eventDesc;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class afterSearchActivity extends AppCompatActivity {
    DatabaseReference myRef;
    ArrayList<eventDesc> viewCard;
    eventAdapter mAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String dept_place,arr_place,date_travel;
    TextView notFound;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myRef = FirebaseDatabase.getInstance().getReference().child("Events");
        constraintLayout =findViewById(R.id.For_notFound);
        notFound = findViewById(R.id.TextView_Not_Found);

        dept_place = getIntent().getStringExtra("DeptPlace");
        arr_place = getIntent().getStringExtra("ArriPlace");
        date_travel = getIntent().getStringExtra("DateForTravel");

        createView();
    }

    public void createView(){
        viewCard = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (date_travel.equals(ds.child("eventDate").getValue())) {
                        if (dept_place.equals(ds.child("eventFrom").getValue()) & arr_place.equals(ds.child("eventTo").getValue())) {
                            constraintLayout.setVisibility(View.GONE);
                            viewCard.add(ds.getValue(eventDesc.class));
                        }
                    }
                }
                if (viewCard.size() == 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (date_travel.equals(ds.child("eventDate").getValue()) & dept_place.equals(ds.child("eventFrom").getValue()))
                            viewCard.add(ds.getValue(eventDesc.class));
                    }
                    notFound.setTextSize(40);
                    notFound.setTextColor(rgb(220, 220, 220));
                    notFound.setText("Destination Not Found");
                }
                if (viewCard.size() == 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (date_travel.equals(ds.child("eventDate").getValue()))
                            viewCard.add(ds.getValue(eventDesc.class));
                    }
                    notFound.setTextSize(40);
                    notFound.setTextColor(rgb(128, 0, 0));
                    notFound.setText("Route Not Found");
                }

                if (viewCard.size() == 0) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (dept_place.equals(ds.child("eventFrom").getValue()) & arr_place.equals(ds.child("eventTo").getValue()))
                            viewCard.add(ds.getValue(eventDesc.class));
                    }
                    notFound.setTextSize(40);
                    notFound.setTextColor(rgb(160,0,0));
                    notFound.setText("Date Not Found");
                }
                if (viewCard.size() == 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (dept_place.equals(ds.child("eventFrom").getValue()))
                            viewCard.add(ds.getValue(eventDesc.class));
                    }
                    notFound.setTextSize(40);
                    notFound.setTextColor(rgb(200, 0, 0));
                    notFound.setText("Date & Destination Not Found");
                }
                if(viewCard.size()==0) {
                    notFound.setTextSize(40);
                    notFound.setTextColor(rgb(225,0,0));
                    notFound.setText("No Result Found");
                }
                recyclerBuild();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void recyclerBuild(){
        recyclerView = findViewById(R.id.RecyclerEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new eventAdapter(viewCard);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}