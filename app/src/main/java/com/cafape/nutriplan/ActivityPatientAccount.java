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
import com.cafape.nutriplan.fragments.Fragment_PatientAccount_Visits;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cafape.nutriplan.Globals.REQCODE_NEWAPPOINTMENT_ADDED;
import static com.cafape.nutriplan.Globals.REQCODE_NEWVISIT;

public class ActivityPatientAccount extends AppCompatActivity
{
    private Context context;
    private PatientEntity patientEntity;
    private PatientAnamnesisEntity patientAnamnesisEntity;
    private TextView activitypatientaccount_appBarLayout_textView_namesurname;
    private List<PatientAntropometryEntity> patientAntropometryEntities;
    private SectionsPagerAdapter_patientaccount sectionsPagerAdapter;

    private VisitsRecyclerViewAdapter visitsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account);
        context = getApplicationContext();

        setUiComponents();
        setListeners();
        initPatient(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQCODE_NEWVISIT)) {
            if (resultCode == RESULT_OK) {
                PatientAntropometryEntity patientAntropometryEntity = (PatientAntropometryEntity) data.getSerializableExtra("newVisitEntity");
                patientAntropometryEntities.add(patientAntropometryEntity);

                //((Fragment_PatientAccount_Visits)(sectionsPagerAdapter.getItem(1))).updateVisitsList(patientAntropometryEntity);
                initPatient(1);


                /*String patient_info = (String) data.getSerializableExtra("patient_info");
                appointmentsRecyclerViewAdapter.addToRetrievedData(new SimpleAppointment(appointmentEntity, patient_info));
                appointmentsRecyclerViewAdapter.notifyDataSetChanged();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(appointmentEntity.getAppointmentTime());
                arrayList_appointmentEntity_ofTheDay.add(new SimpleAppointment(appointmentEntity, patient_info));
                activityappointments_calendarView.addDecorator(new ActivityAppointments.EventDecoratorMonth(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))));
                getAppointmentsOfTheDay();

                 */
            }
        }
    }

    public void initFrames(int framePageToShow) {
        sectionsPagerAdapter = new SectionsPagerAdapter_patientaccount(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(framePageToShow);
    }

    public void initPatient(int framePageToShow) {
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("args");
        patientEntity = (PatientEntity)args.getSerializable("patientObject");
        activitypatientaccount_appBarLayout_textView_namesurname.setText(patientEntity.getNameSurnameBday(getString(R.string.of_the)));
        getPatientAnamnesis(framePageToShow);
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

    private void getPatientAnamnesis(int framePageToShow) {
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
                getPatientAntropometry(framePageToShow);
            }
        }

        GetPatientAnamnesis getPatientAnamnesis = new GetPatientAnamnesis();
        getPatientAnamnesis.execute();
    }

    private void getPatientAntropometry(int framePageToShow) {
        class GetPatientAntropometries extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
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
                initFrames(framePageToShow);
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
                bundle.putSerializable("name_surname", patientEntity.getName() + " " + patientEntity.getSurname());
                intent_goToActivity.putExtra("args", bundle);
                startActivity(intent_goToActivity);
            }
        });
    }

    public void openNewVisit() {
        Intent intent_goToActivity = new Intent(context, ActivityAddVisit.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("patientObject", patientEntity);
        intent_goToActivity.putExtra("args", bundle);
        startActivityForResult(intent_goToActivity, REQCODE_NEWVISIT);
    }
}