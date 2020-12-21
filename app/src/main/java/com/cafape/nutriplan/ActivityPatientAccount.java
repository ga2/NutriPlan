package com.cafape.nutriplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cafape.nutriplan.adapters.AppointmentsAndPatientsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.PatientWithAppointmentsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.PatientsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.VisitsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.objects.SimpleAppointment;
import com.cafape.nutriplan.objects.SimplePatientWithAppointment;
import com.cafape.nutriplan.support.AlertBuilderUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_patientaccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityPatientAccount extends AppCompatActivity
{
    private Context context;
    private PatientEntity patientEntity;
    private PatientAnamnesisEntity patientAnamnesisEntity;
    private TextView activitypatientaccount_appBarLayout_textView_namesurname;
    private List<PatientAntropometryEntity> patientAntropometryEntities;

    private VisitsRecyclerViewAdapter visitsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account);
        context = getApplicationContext();

        setUiComponents();
        setListeners();
        initPatient();
    }

    public void initFrames() {
        SectionsPagerAdapter_patientaccount sectionsPagerAdapter = new SectionsPagerAdapter_patientaccount(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public void initPatient() {
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("args");
        patientEntity = (PatientEntity)args.getSerializable("patientObject");
        activitypatientaccount_appBarLayout_textView_namesurname.setText(patientEntity.getNameSurnameBday(getString(R.string.of_the)));
        getPatientAnamnesis();
    }

    public void setUiComponents() {
        activitypatientaccount_appBarLayout_textView_namesurname = findViewById(R.id.activitypatientaccount_appBarLayout_textView_namesurname);
    }

    public void setListeners() {

    }

    public PatientEntity getPatient() {
        return patientEntity;
    }

    public PatientAnamnesisEntity getPatientAnamnesisEntity() {
        return patientAnamnesisEntity;
    }

    public List<PatientAntropometryEntity> getPatientAntropometryEntities() {
        return patientAntropometryEntities;
    }

    private void getPatientAnamnesis() {
        class GetPatientAnamnesis extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                ArrayList<SimplePatientWithAppointment> arrayList_simplePatientWithAppointment = new ArrayList<>();

                patientAnamnesisEntity = DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .patientAnamnesisDao()
                        .getPatientAnamnesis(patientEntity.getPatiendID());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getPatientAntropometry();
            }
        }

        GetPatientAnamnesis getPatientAnamnesis = new GetPatientAnamnesis();
        getPatientAnamnesis.execute();
    }

    private void getPatientAntropometry() {
        class GetPatientAntropometries extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                ArrayList<SimplePatientWithAppointment> arrayList_simplePatientWithAppointment = new ArrayList<>();

                patientAntropometryEntities = DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .patientAntropometryDao()
                        .getAllPatientAntropometry(patientEntity.getPatiendID());

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                initFrames();
            }
        }

        GetPatientAntropometries getPatientAntropometries = new GetPatientAntropometries();
        getPatientAntropometries.execute();
    }

    public void setVisitsRecyclerViewAdapter(VisitsRecyclerViewAdapter visitsRecyclerViewAdapter) {
        this.visitsRecyclerViewAdapter = visitsRecyclerViewAdapter;
        this.visitsRecyclerViewAdapter.setVisitClickListener(new VisitsRecyclerViewAdapter.VisitClickListener()
        {
            @Override
            public void onItemClick(PatientAntropometryEntity patientAntropometryEntity) {
                Intent intent_goToActivity = new Intent(context, ActivityVisitShow.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("visitObject", patientAntropometryEntity);
                intent_goToActivity.putExtra("args", bundle);
                startActivity(intent_goToActivity);
            }
        });
    }
}