package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.database.entities.PatientWithAppointments;
import com.cafape.nutriplan.objects.SimplePatientWithAppointment;
import com.cafape.nutriplan.support.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import static com.cafape.nutriplan.Globals.DATEFORMAT_DISPLAY;
import static com.cafape.nutriplan.Globals.LONG_DASH;

public class PatientWithAppointmentsRecyclerViewAdapter extends RecyclerView.Adapter<PatientWithAppointmentsRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private Date date_now;

    private List<SimplePatientWithAppointment> retrievedData;
    private List<SimplePatientWithAppointment> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;

    // data is passed into the constructor
    public PatientWithAppointmentsRecyclerViewAdapter(Context context, List<SimplePatientWithAppointment> retrievedData) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
        this.retrievedData_copy = new ArrayList<SimplePatientWithAppointment>();
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
        SimplePatientWithAppointment dataGot = retrievedData.get(position);

        holder.rowPatients_textView_name.setText(dataGot.getPatientEntity().getName() + " " + dataGot.getPatientEntity().getSurname());
        holder.rowPatients_textView_name.setPaintFlags(holder.rowPatients_textView_name.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.rowPatients_textView_name.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                patientAccountClickListener.onItemClick(dataGot.getPatientEntity());
            }
        });
        holder.rowPatients_textView_bdate.setText(Utils.convertDateFormat(dataGot.getPatientEntity().getBirthDate(), DATEFORMAT_DISPLAY));

        Date dateVisit = dataGot.getVisit();

        if(dateVisit != null) {
            if(dateVisit.before(date_now)) { //If the last visit has passed is sure is part of "last visits"
                holder.rowPatients_textView_visitStatus_label.setText(context.getString(R.string.activitypatients_string_patient_lastVisit));
                holder.rowPatients_textView_visitStatus.setTextColor(Color.parseColor("#666666"));
            } else {
                holder.rowPatients_textView_visitStatus_label.setText(context.getString(R.string.activitypatients_string_patient_nextVisit));
            }
            holder.rowPatients_textView_visitStatus.setText(Utils.convertDateFormat(dateVisit, DATEFORMAT_DISPLAY));
        } else {
            holder.rowPatients_textView_visitStatus.setText(LONG_DASH);
            holder.rowPatients_textView_visitStatus.setTextColor(Color.parseColor("#666666"));
        }

        holder.rowPatients_imageView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneClickListener.onItemClick(dataGot.getPatientEntity().getPhone());
            }
        });
        holder.rowPatients_imageView_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsapClickListener.onItemClick(dataGot.getPatientEntity().getName());
            }
        });
    }

    public void setNow(Date date) {
        date_now = date;
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
        TextView rowPatients_textView_visitStatus_label;
        TextView rowPatients_textView_visitStatus;
        ImageView rowPatients_imageView_whatsapp;
        ImageView rowPatients_imageView_call;

        ViewHolder(View itemView) {
            super(itemView);
            rowPatients_textView_name = itemView.findViewById(R.id.rowPatients_textView_name);
            rowPatients_textView_bdate = itemView.findViewById(R.id.rowPatients_textView_bdate);
            rowPatients_textView_visitStatus = itemView.findViewById(R.id.rowPatients_textView_visitStatus);
            rowPatients_textView_visitStatus_label = itemView.findViewById(R.id.rowPatients_textView_visitStatus_label);
            rowPatients_imageView_whatsapp = itemView.findViewById(R.id.rowPatients_imageView_whatsapp);
            rowPatients_imageView_call = itemView.findViewById(R.id.rowPatients_imageView_call);
        }
    }

    // convenience method for getting data at click position
    SimplePatientWithAppointment getItem(int id) {
        return retrievedData.get(id);
    }

    public void addToRetrievedData(SimplePatientWithAppointment patientEntity) {
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
            for(SimplePatientWithAppointment item: retrievedData_copy){
                if(item.getPatientEntity().getName().toLowerCase().contains(text) || item.getPatientEntity().getSurname().toLowerCase().contains(text)){
                    retrievedData.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    //Declarative interface
    private PatientsRecyclerViewAdapter.PatientAccountClickListener patientAccountClickListener;
    //set method
    public void setPatientAccountClickListener(PatientsRecyclerViewAdapter.PatientAccountClickListener patientAccountClickListener) {
        this.patientAccountClickListener = patientAccountClickListener;
    }
    //Defining interface
    public interface PatientAccountClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(PatientEntity patientEntity);
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
    public interface PhoneClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(String stringPhoneNumber);
    }
}
