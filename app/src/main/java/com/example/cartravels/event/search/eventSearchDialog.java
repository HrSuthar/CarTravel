package com.example.cartravels.event.search;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cartravels.R;

import java.util.Objects;

public class eventSearchDialog extends AppCompatDialogFragment {
    SharedPreferences pref;
    public eventSearchDialog(){}

    public interface eventSearchListner{
        void getSearchInfo(String dest,String arri, String date);
    }
    eventSearchListner searchListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            searchListener = (eventSearchListner) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must Implement getsearchInfo");
        }
    }

    EditText departure, arrival, date;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.enter_search,null);
        pref = requireActivity().getSharedPreferences("userPreferences",Context.MODE_PRIVATE);

        departure = view.findViewById(R.id.Dept_edit);
        arrival = view.findViewById(R.id.Arr_edit);
        date = view.findViewById(R.id.Date_edit);

        builder.setView(view)
                .setTitle("Search Events")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String dept = departure.getText().toString();
                            String arr = arrival.getText().toString();
                            String  date_travel = date.getText().toString();
                            if(!dept.equals("") && !arr.equals("") && !date_travel.equals("")) {
                                SharedPreferences.Editor editor= pref.edit();
                                editor.putString("Parent_Back","eventSearchDialog");
                                editor.apply();
                                searchListener.getSearchInfo(dept,arr,date_travel);
                            } else
                                Toast.makeText(getContext(),"Invalid Input ! Please Enter Again",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                });
        return builder.create();
    }
}
