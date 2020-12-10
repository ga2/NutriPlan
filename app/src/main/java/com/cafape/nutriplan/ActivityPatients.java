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

import com.cafape.nutriplan.adapters.PatientsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.cafape.nutriplan.Globals.REQCODE_NEWPATIENT_ADDED;

public class ActivityPatients extends AppCompatActivity
{
    private Context context;
    private MenuItem searchMenuItem;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE_NEWPATIENT_ADDED) {
            if (resultCode == RESULT_OK) {
                PatientEntity patientEntity_new = (PatientEntity)data.getSerializableExtra("newPatientEntity");
                patientsRecyclerViewAdapter.addToRetrievedData(patientEntity_new);
                patientsRecyclerViewAdapter.notifyDataSetChanged();
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
                Intent intent_goToActivity = new Intent(context, ActivityAddPatient.class);
                startActivityForResult(intent_goToActivity, REQCODE_NEWPATIENT_ADDED);
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
                patientsRecyclerViewAdapter.setContext(context);
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
                //Call click method
                patientsRecyclerViewAdapter.setWhatsapClickListener(new PatientsRecyclerViewAdapter.WhatsapClickListener() {
                    @Override
                    public void onItemClick(String name) {
                        openWhatsapp(name);
                    }
                });

                patientsRecyclerViewAdapter.setPhoneClickListener(new PatientsRecyclerViewAdapter.PhoneClickListener() {
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