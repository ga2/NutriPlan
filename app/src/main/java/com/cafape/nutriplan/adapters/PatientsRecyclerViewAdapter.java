package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.database.entities.PatientEntity;

import java.util.List;

public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder>
{
    private List<PatientEntity> retrievedData;
    private LayoutInflater layoutInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public PatientsRecyclerViewAdapter(Context context, List<PatientEntity> retrievedData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
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
        holder.rowPatients_textView_bdate.setText(TimestampConverter.dateToTimestamp(dataGot.getBirthDate()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return retrievedData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rowPatients_textView_name;
        TextView rowPatients_textView_bdate;

        ViewHolder(View itemView) {
            super(itemView);
            rowPatients_textView_name = itemView.findViewById(R.id.rowPatients_textView_name);
            rowPatients_textView_bdate = itemView.findViewById(R.id.rowPatients_textView_bdate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    PatientEntity getItem(int id) {
        return retrievedData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
