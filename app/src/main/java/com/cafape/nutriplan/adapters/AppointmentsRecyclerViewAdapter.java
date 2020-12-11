package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.AppointmentEntity;

import java.util.List;

public class AppointmentsRecyclerViewAdapter  extends RecyclerView.Adapter<AppointmentsRecyclerViewAdapter.ViewHolder>
{
    private List<AppointmentEntity> retrievedData;
    private LayoutInflater layoutInflater;

    public AppointmentsRecyclerViewAdapter(Context context, List<AppointmentEntity> retrievedData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_appointment, parent, false);
        return new AppointmentsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return retrievedData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowPatients_textView_name;


        ViewHolder(View itemView) {
            super(itemView);
            rowPatients_textView_name = itemView.findViewById(R.id.rowPatients_textView_name);
        }
    }

    public void addToRetrievedData(AppointmentEntity appointmentEntity) {
        retrievedData.add(appointmentEntity);
    }

    //Declarative interface
    private EditAppointmentClickListener editAppointmentClickListener;
    //set method
    public void setEditAppointmentClickListener(AppointmentsRecyclerViewAdapter.EditAppointmentClickListener editAppointmentClickListener) {
        this.editAppointmentClickListener = editAppointmentClickListener;
    }
    //Defining interface
    public interface EditAppointmentClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(String appointmentID);
    }
}
