package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.Globals;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.Utils;

import java.util.ArrayList;
import java.util.List;

public class PatientsRecyclerViewAdapter extends RecyclerView.Adapter<PatientsRecyclerViewAdapter.ViewHolder>
{
    private List<PatientEntity> retrievedData;
    private List<PatientEntity> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;
    private ItemClickListener mClickListener;

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
        holder.rowPatients_textView_bdate.setText(Utils.convertDateFormat(dataGot.getBirthDate(), Globals.DATEFORMAT_DISPLAY));
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

    public List<PatientEntity> getRetrievedData() {
        return retrievedData;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void filterPatients(String text) {
        //todo doesn work with patient just added
        //todo implement the widget in the bar
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
}
