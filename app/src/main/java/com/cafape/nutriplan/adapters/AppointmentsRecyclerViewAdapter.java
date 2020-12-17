package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.objects.SimpleAppointment;

import java.util.List;

public class AppointmentsRecyclerViewAdapter  extends RecyclerView.Adapter<AppointmentsRecyclerViewAdapter.ViewHolder>
{
    private List<SimpleAppointment> retrievedData;
    private LayoutInflater layoutInflater;

    public AppointmentsRecyclerViewAdapter(Context context, List<SimpleAppointment> retrievedData) {
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
        SimpleAppointment dataGot = retrievedData.get(position);
        holder.rowAppointment_textView_infouser.setText(dataGot.getDisplayText());
        holder.rowAppointment_imageView_edit.setVisibility(View.GONE);
        holder.rowAppointment_imageView_delete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return retrievedData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowAppointment_textView_infouser;
        ImageView rowAppointment_imageView_edit;
        ImageView rowAppointment_imageView_delete;


        ViewHolder(View itemView) {
            super(itemView);
            rowAppointment_textView_infouser = itemView.findViewById(R.id.rowAppointment_textView_infouser);
            rowAppointment_imageView_edit = itemView.findViewById(R.id.rowAppointment_imageView_edit);
            rowAppointment_imageView_delete = itemView.findViewById(R.id.rowAppointment_imageView_delete);
        }
    }
}
