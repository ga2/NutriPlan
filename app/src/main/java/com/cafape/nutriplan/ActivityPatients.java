package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.adapters.PatientWithAppointmentsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.PatientsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.objects.SimplePatientWithAppointment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cafape.nutriplan.Globals.REQCODE_NEWPATIENT_ADDED;

public class ActivityPatients extends AppCompatActivity
{
    private Context context;
    private MenuItem searchMenuItem;
    private FloatingActionButton activitParents_fab_patient_add;
    private RecyclerView activityPatients_recycleView_patients;
    private PatientWithAppointmentsRecyclerViewAdapter patientsRecyclerViewAdapter;

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
                patientsRecyclerViewAdapter.addToRetrievedData(new SimplePatientWithAppointment(patientEntity_new, null));
                patientsRecyclerViewAdapter.notifyDataSetChanged();

                eventuallyHideNoDataMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activitypatients, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textToSearch) {
                patientsRecyclerViewAdapter.filterPatients(textToSearch);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchMenuItem.collapseActionView();

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    public void setUIComponents() {
        activitParents_fab_patient_add = findViewById(R.id.activityPatients_fab_patient_add);
        activityPatients_recycleView_patients = findViewById(R.id.activityPatients_recycleView_patients);
    }

    public void setListeners() {
        activitParents_fab_patient_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SearchView)searchMenuItem.getActionView()).setQuery("",true);

                Intent intent_goToActivity = new Intent(context, ActivityAddPatient2.class);
                startActivityForResult(intent_goToActivity, REQCODE_NEWPATIENT_ADDED);
            }
        });
    }

    private void getPatients() {
        class GetPatients extends AsyncTask<Void, Void, List<SimplePatientWithAppointment>> {
            @Override
            protected List<SimplePatientWithAppointment> doInBackground(Void... voids) {
                ArrayList<SimplePatientWithAppointment> arrayList_simplePatientWithAppointment = new ArrayList<>();

                List<PatientEntity> patientsList = DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .patientDao()
                        .getAllPatients();

                for(PatientEntity patientEntity : patientsList) {
                    AppointmentEntity appointmentEntity = DatabaseRepository
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .appointmentDao()
                            .getLastAppointmentByPatientID(patientEntity.getPatiendID());
                    SimplePatientWithAppointment simplePatientWithAppointment = new SimplePatientWithAppointment(patientEntity, appointmentEntity);
                    arrayList_simplePatientWithAppointment.add(simplePatientWithAppointment);
                }

                return arrayList_simplePatientWithAppointment;
            }

            @Override
            protected void onPostExecute(List<SimplePatientWithAppointment> patients) {
                super.onPostExecute(patients);
                patientsRecyclerViewAdapter = new PatientWithAppointmentsRecyclerViewAdapter(context, patients);
                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                patientsRecyclerViewAdapter.setNow(now);

                activityPatients_recycleView_patients.setLayoutManager(new LinearLayoutManager(context));
                activityPatients_recycleView_patients.setAdapter(patientsRecyclerViewAdapter);

                eventuallyHideNoDataMessage();

                patientsRecyclerViewAdapter.notifyDataSetChanged();

                patientsRecyclerViewAdapter.setPatientAccountClickListener(new PatientsRecyclerViewAdapter.PatientAccountClickListener()
                {
                    @Override
                    public void onItemClick(PatientEntity patientID) {
                        openPatientAccount(patientID);
                    }
                });

                //Call click method
                patientsRecyclerViewAdapter.setWhatsapClickListener(new PatientWithAppointmentsRecyclerViewAdapter.WhatsapClickListener() {
                    @Override
                    public void onItemClick(String name) {
                        openWhatsapp(name);
                    }
                });

                patientsRecyclerViewAdapter.setPhoneClickListener(new PatientWithAppointmentsRecyclerViewAdapter.PhoneClickListener() {
                    @Override
                    public void onItemClick(String stringPhoneNumber) {
                        openCall(stringPhoneNumber);
                    }
                });

            }
        }

        GetPatients getPatients = new GetPatients();
        getPatients.execute();
    }

    public void eventuallyHideNoDataMessage() {
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

    public void openPatientAccount(PatientEntity patientEntity) {
        Intent intent_goToActivity = new Intent(context, ActivityPatientAccount.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("patientObject", patientEntity);
        intent_goToActivity.putExtra("args", bundle);
        startActivity(intent_goToActivity);
    }

    public void openCall(String stringPhoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + stringPhoneNumber));
        startActivity(intent);
    }

    public void openWhatsapp(String name) {
        PackageManager pm = context.getPackageManager();
        try {
            Intent intent_openWhatsapp = new Intent(Intent.ACTION_SEND);
            intent_openWhatsapp.setType("text/plain");
            @SuppressLint("StringFormatMatches") String text = context.getString(R.string.dear, name);

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            intent_openWhatsapp.setPackage("com.whatsapp");

            intent_openWhatsapp.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(intent_openWhatsapp, context.getString(R.string.share_with)));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, R.string.no_whatsapp, Toast.LENGTH_SHORT).show();
        }
    }
}