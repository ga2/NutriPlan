package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientWithAppointments;
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

    private List<PatientWithAppointments> retrievedData;
    private List<PatientWithAppointments> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;

    // data is passed into the constructor
    public PatientWithAppointmentsRecyclerViewAdapter(Context context, List<PatientWithAppointments> retrievedData) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
        this.retrievedData_copy = new ArrayList<PatientWithAppointments>();
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
        PatientWithAppointments dataGot = retrievedData.get(position);
        holder.rowPatients_textView_name.setText(dataGot.patientEntity.getName() + " " + dataGot.patientEntity.getSurname());
        holder.rowPatients_textView_bdate.setText(Utils.convertDateFormat(dataGot.patientEntity.getBirthDate(), DATEFORMAT_DISPLAY));
        List<AppointmentEntity> appointments = dataGot.appointments;
        if(appointments != null && !appointments.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            Date now = calendar.getTime();
            Collections.sort(appointments, Collections.reverseOrder()); //get the highest

            Date nearestVisit = null;
            if(appointments.get(0).getAppointmentTime().before(now)) { //If the last visit has passed is sure is part of "last visits"
                nearestVisit = appointments.get(0).getAppointmentTime();
                holder.rowPatients_textView_visitStatus_label.setText(context.getString(R.string.activitypatients_string_patient_lastVisit));
                holder.rowPatients_textView_visitStatus.setTextColor(Color.BLACK);
            } else {
                holder.rowPatients_textView_visitStatus_label.setText(context.getString(R.string.activitypatients_string_patient_nextVisit));
                int size = appointments.size();

                for (ListIterator iterator = appointments.listIterator(); iterator.hasNext();) {
                    AppointmentEntity appointmentEntity = (AppointmentEntity)iterator.next();
                    if(appointmentEntity.getAppointmentTime().after(now)) {
                        nearestVisit = appointmentEntity.getAppointmentTime();
                    } else {
                        break;
                    }
                }
            }
            holder.rowPatients_textView_visitStatus.setText(Utils.convertDateFormat(nearestVisit, DATEFORMAT_DISPLAY));
        } else {
            holder.rowPatients_textView_visitStatus.setText(LONG_DASH);
            holder.rowPatients_textView_visitStatus.setTextColor(Color.BLACK);
        }
        holder.rowPatients_imageView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneClickListener.onItemClick(dataGot.patientEntity.getPhone());
            }
        });
        holder.rowPatients_imageView_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsapClickListener.onItemClick(dataGot.patientEntity.getName());
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
    PatientWithAppointments getItem(int id) {
        return retrievedData.get(id);
    }

    public void addToRetrievedData(PatientWithAppointments patientEntity) {
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
            for(PatientWithAppointments item: retrievedData_copy){
                if(item.patientEntity.getName().toLowerCase().contains(text) || item.patientEntity.getSurname().toLowerCase().contains(text)){
                    retrievedData.add(item);
                }
            }
        }
        notifyDataSetChanged();
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
