package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cafape.nutriplan.adapters.PatientsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.cafape.nutriplan.Globals.REQCODE_NEWPATIENT_ADDED;

public class ActivityPatients extends AppCompatActivity
{
    private Context context;
    private FloatingActionButton activitParents_fab_patient_add;
    private EditText activityPatients_editText_patient_search;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE_NEWPATIENT_ADDED) {
            if (resultCode == RESULT_OK) {
                PatientEntity patientEntity_new = (PatientEntity)data.getSerializableExtra("newPatientEntity");
                patientsRecyclerViewAdapter.getRetrievedData().add(patientEntity_new);
                patientsRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setUIComponents() {
        activitParents_fab_patient_add = findViewById(R.id.activityPatients_fab_patient_add);
        activityPatients_recycleView_patients = findViewById(R.id.activityPatients_recycleView_patients);
        activityPatients_editText_patient_search = findViewById(R.id.activityPatients_editText_patient_search);
    }

    public void setListeners() {
        activitParents_fab_patient_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityAddPatient.class);
                startActivityForResult(intent_goToActivity, REQCODE_NEWPATIENT_ADDED);
            }
        });

        activityPatients_editText_patient_search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String textToSearch = editable.toString();
                patientsRecyclerViewAdapter.filterPatients(textToSearch);
            }
        });
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
                patientsRecyclerViewAdapter = new PatientsRecyclerViewAdapter(context, patients);
                activityPatients_recycleView_patients.setLayoutManager(new LinearLayoutManager(context));
                activityPatients_recycleView_patients.setAdapter(patientsRecyclerViewAdapter);
                patientsRecyclerViewAdapter.notifyDataSetChanged();
                TextView activityPatients_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                ConstraintLayout activityPatients_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                if(patientsRecyclerViewAdapter.getItemCount() == 0) {
                    activityPatients_textView_nodata_details.setText(getString(R.string.activityaddpatient_string_nodata_details));
                    activityPatients_textView_nodata_details.setVisibility(View.VISIBLE);
                    activityPatients_constraintLayout_nodata.setVisibility(View.VISIBLE);
                } else {
                    activityPatients_textView_nodata_details.setText("");
                    activityPatients_constraintLayout_nodata.setVisibility(View.GONE);
                }
            }
        }

        GetPatients getPatients = new GetPatients();
        getPatients.execute();
    }
}