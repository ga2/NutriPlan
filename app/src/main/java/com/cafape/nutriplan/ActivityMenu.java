package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cafape.nutriplan.support.Globals;
import com.cafape.nutriplan.support.Unzipper;
import com.cafape.nutriplan.support.Utils;
import com.cafape.nutriplan.support.Zipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.cafape.nutriplan.support.Globals.DBNAME;
import static com.cafape.nutriplan.support.Globals.EXTENSIONE_BACKUP;
import static com.cafape.nutriplan.support.Globals.FORMAT_DATE_SAVEDB;
import static com.cafape.nutriplan.support.Globals.PERMISSION_WRITEEXTERNALSTORAGE;
import static com.cafape.nutriplan.support.Globals.REQCODE_OVERWRITEDB;
import static com.cafape.nutriplan.support.Globals.REQCODE_WRITEFILE;

public class ActivityMenu extends AppCompatActivity
{
    private Context context;
    private ConstraintLayout activitymanu_constraintLayout_card_calc;
    private ConstraintLayout activitymanu_constraintLayout_card_patient;
    private ConstraintLayout activitymanu_constraintLayout_card_appointment;
    private ConstraintLayout activitymanu_constraintLayout_card_importexport;

    private Dialog dialog_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = getApplicationContext();

        setUiComponents();
        setListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if ((requestCode == REQCODE_OVERWRITEDB)) {
                if (data != null) {
                    Uri uri = data.getData();
                    importDatabase(uri);
                }
            }
            else if ((requestCode == REQCODE_WRITEFILE)) {
                if (data != null) {
                    Uri uri = data.getData();
                    writeDBFile(uri);
                }
            }
        }
    }

    public void setUiComponents() {
        activitymanu_constraintLayout_card_calc = findViewById(R.id.activitymanu_constraintLayout_card_calc);
        activitymanu_constraintLayout_card_patient = findViewById(R.id.activitymanu_constraintLayout_card_patient);
        activitymanu_constraintLayout_card_appointment = findViewById(R.id.activitymanu_constraintLayout_card_appointment);
        activitymanu_constraintLayout_card_importexport = findViewById(R.id.activitymanu_constraintLayout_card_importexport);
    }

    public void setListeners() {
        activitymanu_constraintLayout_card_calc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityCalc.class);
                startActivity(intent_goToActivity);
            }
        });

        activitymanu_constraintLayout_card_patient.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityPatients.class);
                startActivity(intent_goToActivity);
            }
        });

        activitymanu_constraintLayout_card_appointment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityAppointments.class);
                startActivity(intent_goToActivity);
            }
        });

        activitymanu_constraintLayout_card_importexport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_importexport = new AlertDialog.Builder(ActivityMenu.this);
                LayoutInflater factory = LayoutInflater.from(ActivityMenu.this);
                final View view_importexport = factory.inflate(R.layout.dialog_importexport, null);
                alert_importexport.setView(view_importexport);
                alert_importexport.show();
                ImageView dialogimportexport_imageButton_export = view_importexport.findViewById(R.id.dialogimportexport_imageButton_export);
                dialogimportexport_imageButton_export.setOnClickListener(view_export ->
                {
                    initializeEmptyBackupile();
                });
                ImageView dialogimportexport_imageButton_import = view_importexport.findViewById(R.id.dialogimportexport_imageButton_import);
                dialogimportexport_imageButton_import.setOnClickListener(view_import ->
                {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("*/*");
                    startActivityForResult(intent, REQCODE_OVERWRITEDB);
                });
            }
        });
    }

    public void initializeEmptyBackupile() {
        if((Utils.askForPermission(ActivityMenu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_WRITEEXTERNALSTORAGE))) {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

            // Filter to only show results that can be "opened", such as
            // a file (as opposed to a list of contacts or timezones).
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            // Create a file with the requested MIME type.
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_TITLE, Utils.convertDateFormat(Utils.getCurrentTimeStamp(), FORMAT_DATE_SAVEDB) + "." + EXTENSIONE_BACKUP);
            startActivityForResult(intent, REQCODE_WRITEFILE);
        }

        //todo chiedere di abilitare
        //todo abiitare e proseguire
        //todo chiuedere alla fine
    }

    public void writeDBFile(Uri uri) {
        class AsyncTaskExportFile extends AsyncTask<Void, Void, Boolean>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... voids)
            {
                try {
                    ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
                    if(pfd != null) {
                        //DB SECTION
                        File fileDatabase = context.getDatabasePath(DBNAME);
                        String fileDatabasePath = fileDatabase.getAbsolutePath();
                        String fileDatabasePath_shm = fileDatabasePath + "-shm";
                        String fileDatabasePath_wal = fileDatabasePath + "-wal";
                        //FILES SECTIONS
                        File docsDirectory = new File(context.getFilesDir(), Globals.DOCUMENT_FOLDER_NAME);
                        String[] filesPaths_toZip = new String[] {fileDatabasePath, fileDatabasePath_shm, fileDatabasePath_wal, docsDirectory.getAbsolutePath()};
                        //String[] filesPaths_toZip = new String[] {fileDatabasePath, fileDatabasePath_shm, fileDatabasePath_wal};
                        Zipper.zip(filesPaths_toZip, new FileOutputStream(pfd.getFileDescriptor()));
                        return true;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    return false;
                }

                return false;
            }

            @Override
            protected void onPostExecute(Boolean result)
            {
                super.onPostExecute(result);
                progressDialog_hide();
                String message = getString(R.string.saved);
                if(!result) {
                    message = getString(R.string.activitymenu_string_importing_failed);
                } Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }

        new AsyncTaskExportFile().execute();
    }

    public void importDatabase(Uri uri) {
        class AsyncTaskImportFile extends AsyncTask<Void, Void, Boolean>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(Void... voids)
            {
                try {
                    ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "r");
                    if(pfd != null) {
                        if(Utils.getFileExtension(Utils.getFileUriName(context, uri)).equals(EXTENSIONE_BACKUP)) {
                            File fileDatabase = context.getDatabasePath(DBNAME);
                            String baseDataBaseName = fileDatabase.getName();
                            String fileDatabasePath = fileDatabase.getAbsolutePath();
                            String fileDatabasePath_shm = fileDatabasePath + "-shm";
                            String fileDatabasePath_wal = fileDatabasePath + "-wal";
                            File dirtemp_db = new File(getCacheDir(), "db_backup");
                            if (!dirtemp_db.exists()) {
                                dirtemp_db.mkdir();
                            }

                            if(Unzipper.unzip(new FileInputStream(pfd.getFileDescriptor()), dirtemp_db) > 0) {
                                String db_cached_base = dirtemp_db.getAbsolutePath() + "/" + baseDataBaseName;
                                String db_cached_base_shm = dirtemp_db.getAbsolutePath() + "/" + baseDataBaseName + "-shm";
                                String db_cached_base_wal = dirtemp_db.getAbsolutePath() + "/" + baseDataBaseName + "-wal";

                                Utils.copyFile(db_cached_base, fileDatabasePath);
                                Utils.copyFile(db_cached_base_shm, fileDatabasePath_shm);
                                Utils.copyFile(db_cached_base_wal, fileDatabasePath_wal);

                                ///copyfolder

                            } else {
                                return false;
                            }

                            return true;
                        }
                        return false;
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean result)
            {
                super.onPostExecute(result);
                progressDialog_hide();
                String message = getString(R.string.loading_complete);
                if(!result) {
                    message = getString(R.string.activitymenu_string_importing_failed);
                } Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }

        new AsyncTaskImportFile().execute();
    }

    public void progressDialog_show(String message)
    {
        if((dialog_progress == null) || !dialog_progress.isShowing())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.progress);
            if (message != null)
            {
                builder.setMessage(message);
            }
            else
            {
                builder.setMessage(R.string.loading);
            }
            dialog_progress = builder.create();
            dialog_progress.setCanceledOnTouchOutside(false);
            dialog_progress.show();
        }
    }

    public void progressDialog_hide()
    {
        if((dialog_progress != null) && (dialog_progress.isShowing()))
        {
            dialog_progress.dismiss();
        }
    }
}