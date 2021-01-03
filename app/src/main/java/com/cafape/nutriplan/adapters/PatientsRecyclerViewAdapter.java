package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.support.Globals;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.Utils;

import java.util.ArrayList;
import java.util.List;

public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder>
{
    private List<PatientEntity> retrievedData;
    private List<PatientEntity> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;

    // data is passed into the constructor
    public PatientsRecyclerViewAdapter(Context context, List<PatientEntity> retrievedData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
        this.retrievedData_copy = new ArrayList<PatientEntity>();
        this.retrievedData_copy.addAll(retrievedData);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_patients, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PatientEntity dataGot = retrievedData.get(position);
        holder.rowPatients_textView_name.setText(dataGot.getName() + " " + dataGot.getSurname());
        holder.rowPatients_textView_name.setPaintFlags(holder.rowPatients_textView_name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.rowPatients_textView_bdate.setText(Utils.convertDateFormat(dataGot.getBirthDate(), Globals.DATEFORMAT_DISPLAY));
        holder.rowPatients_textView_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                patientAccountClickListener.onItemClick(dataGot);
            }
        });
        holder.rowPatients_imageView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneClickListener.onItemClick(dataGot.getPhone());
            }
        });
        holder.rowPatients_imageView_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsapClickListener.onItemClick(dataGot.getName());
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return retrievedData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowPatients_textView_name;
        TextView rowPatients_textView_bdate;
        ImageView rowPatients_imageView_whatsapp;
        ImageView rowPatients_imageView_call;

        ViewHolder(View itemView) {
            super(itemView);
            rowPatients_textView_name = itemView.findViewById(R.id.rowPatients_textView_name);
            rowPatients_textView_bdate = itemView.findViewById(R.id.rowPatients_textView_bdate);
            rowPatients_imageView_whatsapp = itemView.findViewById(R.id.rowPatients_imageView_whatsapp);
            rowPatients_imageView_call = itemView.findViewById(R.id.rowPatients_imageView_call);
        }
    }

    // convenience method for getting data at click position
    PatientEntity getItem(int id) {
        return retrievedData.get(id);
    }

    public void addToRetrievedData(PatientEntity patientEntity) {
        retrievedData.clear();
        retrievedData.add(patientEntity);
        retrievedData_copy.add(patientEntity);
        retrievedData.addAll(retrievedData_copy);
    }

    public void filterPatients(String text) {
        retrievedData.clear();
        if(text.isEmpty()){
            retrievedData.addAll(retrievedData_copy);
        } else{
            text = text.toLowerCase();
            for(PatientEntity item: retrievedData_copy){
                if(item.getName().toLowerCase().contains(text) || item.getSurname().toLowerCase().contains(text)){
                    retrievedData.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    //Declarative interface
    private PatientAccountClickListener patientAccountClickListener;
    //set method
    public void setPatientAccountClickListener(PatientAccountClickListener patientAccountClickListener) {
        this.patientAccountClickListener = patientAccountClickListener;
    }
    //Defining interface
    public interface PatientAccountClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(PatientEntity patientID);
    }

    //Declarative interface
    private WhatsapClickListener whatsapClickListener;
    //set method
    public void setWhatsapClickListener(WhatsapClickListener whatsapClickListener) {
        this.whatsapClickListener = whatsapClickListener;
    }
    //Defining interface
    public interface WhatsapClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(String name);
    }

    //Declarative interface
    private PhoneClickListener phoneClickListener;
    //set method
    public void setPhoneClickListener(PhoneClickListener phoneClickListener) {
        this.phoneClickListener = phoneClickListener;
    }
    //Defining interface
    public interface PhoneClickListener
    {
        //Achieve the click method, passing the subscript.
        void onItemClick(String stringPhoneNumber);
    }
}
