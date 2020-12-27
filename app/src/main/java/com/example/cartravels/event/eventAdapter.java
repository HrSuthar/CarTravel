package com.example.cartravels.event;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cartravels.PaymentActivity;
import com.example.cartravels.R;

import java.util.ArrayList;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.eventViewHolder> {
    private ArrayList<eventDesc> mEventDesc;
    SharedPreferences pref;

    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            pref = parent.getContext().getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_event_card,parent,false);
        return new eventViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {

        eventDesc eventDesc = mEventDesc.get(position);
        holder.textViewVehicleNo.setText(eventDesc.getEventVehicleNo());
        holder.textViewFrom.setText("From: "+eventDesc.getEventFrom());
        holder.textViewTo.setText("To: "+eventDesc.getEventTo());
        holder.textViewDate.setText(eventDesc.getEventDate());
        holder.textViewVehicle.setText(eventDesc.getEventVehicle());
        holder.textViewSeat.setText("Seat Available: "+eventDesc.getSeat_Nums());
        if(pref.getString("UserType","-").equals("Customer"))
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), PaymentActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventDesc.size();
    }

    public static class eventViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFrom,textViewDate,textViewTo,textViewVehicle,textViewSeat,textViewVehicleNo;
        CardView cardView;

        public eventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFrom = itemView.findViewById(R.id.textViewEventFrom);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTo = itemView.findViewById(R.id.textViewEventTo);
            textViewVehicle = itemView.findViewById(R.id.Vehicle);
            textViewSeat = itemView.findViewById(R.id.Seat_aval);
            textViewVehicleNo = itemView.findViewById(R.id.textViewEventVehicleNo);
            cardView = itemView.findViewById(R.id.card_View);
        }
    }

    public eventAdapter(ArrayList<eventDesc> exampleList){
        mEventDesc = exampleList;
    }
}
