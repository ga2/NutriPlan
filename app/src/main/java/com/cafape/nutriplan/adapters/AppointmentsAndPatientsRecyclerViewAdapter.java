package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.objects.SimpleAppointment;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsAndPatientsRecyclerViewAdapter extends RecyclerView.Adapter<AppointmentsAndPatientsRecyclerViewAdapter.ViewHolder>
{
    private Context context;

    private List<SimpleAppointment> retrievedData;
    private List<SimpleAppointment> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;

    // data is passed into the constructor
    public AppointmentsAndPatientsRecyclerViewAdapter(Context context, List<SimpleAppointment> retrievedData) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
        this.retrievedData_copy = new ArrayList<SimpleAppointment>();
        this.retrievedData_copy.addAll(retrievedData);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_appointment, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleAppointment dataGot = retrievedData.get(position);
        holder.rowAppointment_textView_infouser.setText(dataGot.getDisplayText());
        holder.rowAppointment_imageView_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                deleteAppointmentClickListener.onItemClick(retrievedData.get(position));
            }
        });
        //holder.rowAppointment_textView_datetime.setText(Utils.convertDateFormat(dataGot.patientEntity.getBirthDate(), DATEFORMAT_DISPLAY));
        //holder.rowAppointment_textView_datetime.setText(Utils.convertDateFormat(dataGot.get, DATEFORMAT_DISPLAY));


        /*
        holder.rowPatients_imageView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneClickListener.onItemClick(dataGot.patientEntity.getPhone());
            }
        });
        */

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return retrievedData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowAppointment_textView_infouser;
        TextView rowAppointment_textView_datetime;
        ImageView rowAppointment_imageView_delete;

        ViewHolder(View itemView) {
            super(itemView);
            rowAppointment_textView_infouser = itemView.findViewById(R.id.rowAppointment_textView_infouser);
            rowAppointment_textView_datetime = itemView.findViewById(R.id.rowAppointment_textView_datetime);
            rowAppointment_imageView_delete = itemView.findViewById(R.id.rowAppointment_imageView_delete);
        }
    }

    // convenience method for getting data at click position
    SimpleAppointment getItem(int id) {
        return retrievedData.get(id);
    }

    public void addToRetrievedData(SimpleAppointment patientEntity) {
        retrievedData.clear();
        retrievedData.add(patientEntity);
        retrievedData_copy.add(patientEntity);
        retrievedData.addAll(retrievedData_copy);
    }

    public void deleteFromRetrievedData(SimpleAppointment patientEntity) {
        retrievedData_copy.clear();
        retrievedData.remove(patientEntity);
        retrievedData.addAll(retrievedData_copy);
    }

    //Declarative interface
    private AppointmentsAndPatientsRecyclerViewAdapter.EditAppointmentClickListener editAppointmentClickListener;
    //set method
    public void setEditAppointmentClickListener(AppointmentsAndPatientsRecyclerViewAdapter.EditAppointmentClickListener editAppointmentClickListener) {
        this.editAppointmentClickListener = editAppointmentClickListener;
    }
    //Defining interface
    public interface EditAppointmentClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(String appointmentID);
    }

    private AppointmentsAndPatientsRecyclerViewAdapter.DeleteAppointmentClickListener deleteAppointmentClickListener;
    public void setDeleteAppointmentClickListener(AppointmentsAndPatientsRecyclerViewAdapter.DeleteAppointmentClickListener deleteAppointmentClickListener) {
        this.deleteAppointmentClickListener = deleteAppointmentClickListener;
    }
    public interface DeleteAppointmentClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(SimpleAppointment simpleAppointment);
    }
}
