package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cafape.nutriplan.adapters.PatientsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityPatients extends AppCompatActivity
{
    private Context context;
    private FloatingActionButton activitParents_fab_patient_add;
    private RecyclerView activityPatients_recycleView_patients;
    private PatientsRecyclerViewAdapter patientsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        context = getApplicationContext();

        setUIComponents();
        setListeners();

        getPatients();
    }

    public void setUIComponents() {
        activitParents_fab_patient_add = findViewById(R.id.activityPatients_fab_patient_add);
        activityPatients_recycleView_patients = findViewById(R.id.activityPatients_recycleView_patients);
        //List<PatientEntity> patientsList = new ArrayList<>();
        //patientsRecyclerViewAdapter = new PatientsRecyclerViewAdapter(context, patientsList);
        //activityPatients_recycleView_patients.setAdapter(patientsRecyclerViewAdapter);
    }

    public void setListeners() {
        activitParents_fab_patient_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                savePatient();
            }
        });
    }

    private void savePatient() {
        /*
        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishBy = editTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty()) {
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            return;
        }
        */

        class SavePatient extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {

                //creating a patient
                PatientEntity patient = new PatientEntity();
                patient.setName("MARCO");

                //adding to database
                DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase()
                        .patientDao()
                        .insertPatient(patient);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SavePatient savePatient = new SavePatient();
        savePatient.execute();
    }

    private void getPatients() {
        class GetPatients extends AsyncTask<Void, Void, List<PatientEntity>> {

            @Override
            protected List<PatientEntity> doInBackground(Void... voids) {
                List<PatientEntity> patientsList = DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .patientDao()
                        .getAllPatients();
                return patientsList;
            }

            @Override
            protected void onPostExecute(List<PatientEntity> patients) {
                super.onPostExecute(patients);
                for (PatientEntity patient : patients) {
                    System.out.println(patient.getName());
                }
                //PatientsRecyclerViewAdapter patientsRecyclerViewAdapter = new PatientsRecyclerViewAdapter(context, patients);
                patientsRecyclerViewAdapter = new PatientsRecyclerViewAdapter(context, patients);
                activityPatients_recycleView_patients.setLayoutManager(new LinearLayoutManager(context));
                activityPatients_recycleView_patients.setAdapter(patientsRecyclerViewAdapter);
                patientsRecyclerViewAdapter.notifyDataSetChanged();
                //TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasks);
                //recyclerView.setAdapter(adapter);
            }
        }

        GetPatients getPatients = new GetPatients();
        getPatients.execute();
    }
}