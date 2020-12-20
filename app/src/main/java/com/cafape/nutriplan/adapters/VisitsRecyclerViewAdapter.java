package com.cafape.nutriplan.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.Globals;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.support.Utils;

import java.util.ArrayList;
import java.util.List;

public class VisitsRecyclerViewAdapter extends RecyclerView.Adapter<VisitsRecyclerViewAdapter.ViewHolder>
{
    private List<PatientAntropometryEntity> retrievedData;
    private List<PatientAntropometryEntity> retrievedData_copy; //needed for filtering
    private LayoutInflater layoutInflater;

    // data is passed into the constructor
    public VisitsRecyclerViewAdapter(Context context, List<PatientAntropometryEntity> retrievedData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.retrievedData = retrievedData;
        this.retrievedData_copy = new ArrayList<PatientAntropometryEntity>();
        this.retrievedData_copy.addAll(retrievedData);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_visit, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PatientAntropometryEntity dataGot = retrievedData.get(position);
        holder.rowVisits_textView_visitdate.setText(Utils.convertDateFormat(dataGot.getAntropometryTime(), Globals.DATEFORMAT_DISPLAY));
        holder.rowVisits_textView_visitdate.setPaintFlags(holder.rowVisits_textView_visitdate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.rowVisits_textView_visitdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                visitClickListener.onItemClick(dataGot);
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
        TextView rowVisits_textView_visitdate;

        ViewHolder(View itemView) {
            super(itemView);
            rowVisits_textView_visitdate = itemView.findViewById(R.id.rowVisits_textView_visitdate);
        }
    }

    // convenience method for getting data at click position
    PatientAntropometryEntity getItem(int id) {
        return retrievedData.get(id);
    }

    public void addToRetrievedData(PatientAntropometryEntity patientAntropometryEntity) {
        retrievedData.clear();
        retrievedData.add(patientAntropometryEntity);
        retrievedData_copy.add(patientAntropometryEntity);
        retrievedData.addAll(retrievedData_copy);
    }

    //Declarative interface
    private VisitClickListener visitClickListener;
    //set method
    public void setVisitClickListener(VisitClickListener visitClickListener) {
        this.visitClickListener = visitClickListener;
    }
    //Defining interface
    public interface VisitClickListener {
        //Achieve the click method, passing the subscript.
        void onItemClick(PatientAntropometryEntity patientAntropometryEntity);
    }
}
