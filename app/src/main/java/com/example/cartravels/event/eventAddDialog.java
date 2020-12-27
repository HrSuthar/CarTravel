package com.example.cartravels.event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.cartravels.R;
import com.google.android.material.snackbar.Snackbar;

public class eventAddDialog extends AppCompatDialogFragment {

   public eventAddDialog(){
    }

    public interface eventAddListener{
     void getEventInfo(String eventFrom,String eventTo,String eventDate,String eventVehicle,String eventVehicleNo,long seat_Nums);
    }
    public eventAddListener listener;

    public void onAttachParentFragment(@NonNull Fragment context) {
        try {
            listener= (eventAddListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement getEventInfo ");
        }
    }
    private EditText newDate,newFrom,newTo,newVehicle,newSeat,newVehicleNo;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getParentFragment() != null;
        onAttachParentFragment(getParentFragment());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_event, null);

            newDate = view.findViewById(R.id.dialogEditDate);
            newFrom = view.findViewById(R.id.dialogEditFrom);
            newTo = view.findViewById(R.id.dialogEditTo);
            newVehicle=view.findViewById(R.id.dialogEditVehicle);
            newSeat=view.findViewById(R.id.dialogEditSeat);
            newVehicleNo=view.findViewById(R.id.dialogEditVehicleNo);

            builder.setView(view)
                    .setTitle("New Event")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                String from = newFrom.getText().toString();
                                String to = newTo.getText().toString();
                                String vehicle = newVehicle.getText().toString();
                                long seat = Long.parseLong(newSeat.getText().toString());
                                String date = newDate.getText().toString();
                                String vehicleNo = newVehicleNo.getText().toString();
                                    listener.getEventInfo(from, to, date, vehicle, vehicleNo, seat);
                            }catch (Exception e){
                                Toast.makeText(getContext(), e +"Invalid Input ! Please Enter Again", Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }
                    });

        return builder.create();
    }

}
