package com.cafape.nutriplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cafape.nutriplan.adapters.AppointmentsAndPatientsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.FilesRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.PatientWithAppointmentsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.PatientsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.VisitsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.FileEntity;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.fragments.Fragment_PatientAccount_Visits;
import com.cafape.nutriplan.objects.SimpleAppointment;
import com.cafape.nutriplan.objects.SimplePatientWithAppointment;
import com.cafape.nutriplan.support.AlertBuilderUtils;
import com.cafape.nutriplan.support.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.OpenableColumns;
import android.view.View;
import android.widget.TextView;

import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_patientaccount;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cafape.nutriplan.Globals.MIME_JPG;
import static com.cafape.nutriplan.Globals.MIME_PDF;
import static com.cafape.nutriplan.Globals.MIME_PNG;
import static com.cafape.nutriplan.Globals.REQCODE_EDITPATIENTNOTES;
import static com.cafape.nutriplan.Globals.REQCODE_NEWAPPOINTMENT_ADDED;
import static com.cafape.nutriplan.Globals.REQCODE_NEWVISIT;
import static com.cafape.nutriplan.Globals.REQUEST_CODE_LOADFILE;
import static com.cafape.nutriplan.support.Utils.myprint;

public class ActivityPatientAccount extends AppCompatActivity
{
    private Context context;
    private PatientEntity patientEntity;
    private PatientAnamnesisEntity patientAnamnesisEntity;
    private TextView activitypatientaccount_appBarLayout_textView_namesurname;
    private List<PatientAntropometryEntity> patientAntropometryEntities;
    private List<FileEntity> fileEntities;
    private SectionsPagerAdapter_patientaccount sectionsPagerAdapter;

    private VisitsRecyclerViewAdapter visitsRecyclerViewAdapter;
    private FilesRecyclerViewAdapter filesRecyclerViewAdapter;

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

                initPatient(1);
            }
        } else if((requestCode == REQCODE_EDITPATIENTNOTES)) {
            if (resultCode == RESULT_OK) {
                patientAnamnesisEntity = (PatientAnamnesisEntity) data.getSerializableExtra("updatedPatientAnamnesisEntity");
                patientEntity = (PatientEntity) data.getSerializableExtra("updatedPatientEntity");

                getPatientAnamnesis(0);
            }
        } else if(requestCode == REQUEST_CODE_LOADFILE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    importFile(uri);
                }
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

    public List<FileEntity> getPatientFilesEntities() {
        return fileEntities;
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
                getPatientFiles(framePageToShow);
            }
        }

        GetPatientAntropometries getPatientAntropometries = new GetPatientAntropometries();
        getPatientAntropometries.execute();
    }

    private void getPatientFiles(int framePageToShow) {
        class GetPatientFiles extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                fileEntities = DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .fileDao()
                        .getFilesByPatient(patientEntity.getPatiendID());

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                initFrames(framePageToShow);
            }
        }

        GetPatientFiles getPatientFiles = new GetPatientFiles();
        getPatientFiles.execute();
    }

    public void importFile(Uri uri) {
        class AsyncTaskImportFile extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                String originalFileName = Utils.getFileUriName(context, uri);
                String pseudoName = Utils.generateRandomAlphanumericString() + "." + Utils.getFileExtension(originalFileName);
                try {
                    InputStream inputStream =  getContentResolver().openInputStream(uri);
                    File docsDirectory = new File(context.getFilesDir(), Globals.DOCUMENT_FOLDER_NAME);
                    if(!docsDirectory.exists()) {
                        docsDirectory.mkdir();
                    }
                    File userDirectory = new File(docsDirectory, String.valueOf(patientEntity.getPatiendID()));
                    if(!userDirectory.exists()) {
                        userDirectory.mkdir();
                    }
                    File userFile = new File(userDirectory, pseudoName);
                    Utils.copyFile(inputStream, userFile);
                    FileEntity fileEntity = new FileEntity(originalFileName, "", pseudoName, patientEntity.getPatiendID());
                    DatabaseRepository
                            .getInstance(getApplicationContext())
                            .getAppDatabase()
                            .fileDao()
                            .insert(fileEntity);
                    fileEntities.add(fileEntity);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                /*DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .fileDao()
                        .getFilesByPatient(patientEntity.getPatiendID());
                 */

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                initPatient(2);
            }
        }

        new AsyncTaskImportFile().execute();
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

    public void setFilesRecyclerViewAdapter(FilesRecyclerViewAdapter filesRecyclerViewAdapter) {
        this.filesRecyclerViewAdapter = filesRecyclerViewAdapter;
        this.filesRecyclerViewAdapter.setFileopeningClickListener(new FilesRecyclerViewAdapter.FileopeningClickListener()
        {
            @Override
            public void onItemClick(FileEntity fileEntity) {
                File docsDirectory = new File(context.getFilesDir(), Globals.DOCUMENT_FOLDER_NAME);
                File userDirectory = new File(docsDirectory, String.valueOf(patientEntity.getPatiendID()));
                File userFile = new File(userDirectory, fileEntity.getFilePseudoname());

                // Get URI and MIME type of file
                Uri uri = FileProvider.getUriForFile(context, getPackageName() + ".fileprovider", userFile);
                //String mime = getContentResolver().getType(uri);

                /*
                // Open file with user selected app
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, mime);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);


                Intent intent_goToActivity = new Intent(context, ActivityVisitShow.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("visitObject", patientAntropometryEntity);
                bundle.putSerializable("name_surname", patientEntity.getName() + " " + patientEntity.getSurname());
                intent_goToActivity.putExtra("args", bundle);
                startActivity(intent_goToActivity);*/

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivity(intent);


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

    public void openEditPatientNotes() {
        Intent intent_goToActivity = new Intent(context, ActivityEditPatientNotes.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("patientObject", patientEntity);
        bundle.putSerializable("patientAnamnesisObject", patientAnamnesisEntity);
        intent_goToActivity.putExtra("args", bundle);
        startActivityForResult(intent_goToActivity, REQCODE_EDITPATIENTNOTES);
    }

    public void performFileSearch()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        String [] mimeTypes = {MIME_JPG, MIME_PDF, MIME_PNG};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, REQUEST_CODE_LOADFILE);
    }


}