package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientEntity;

public class ActivityAddPatient extends AppCompatActivity
{
    Button activityaddpatient_button_save;
    PatientEntity patientEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        patientEntity = new PatientEntity();
        setUIComponents();
        setListeners();
    }

    public void setUIComponents() {
        activityaddpatient_button_save = findViewById(R.id.activityaddpatient_button_save);
    }

    public void setListeners() {
        activityaddpatient_button_save.setOnClickListener(new View.OnClickListener()
        {
            SavePatient savePatient = new SavePatient();

            @Override
            public void onClick(View view) {
                EditText activityaddpatient_editText_name = findViewById(R.id.activityaddpatient_editText_name);
                String name = activityaddpatient_editText_name.getText().toString();
                if(!name.isEmpty()) {
                    patientEntity.setName(name);
                    savePatient.execute();
                }
            }
        });
    }

    class SavePatient extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            //adding to database
            DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase()
                    .patientDao()
                    .insertPatient(patientEntity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent data = new Intent();
            data.putExtra("newPatientEntity",patientEntity);
            setResult(RESULT_OK, data);
            finish();
            Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();
        }
    }
}