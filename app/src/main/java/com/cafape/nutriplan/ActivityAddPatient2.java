package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_addpatient;
import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_calc;

import java.text.DecimalFormat;

import static com.cafape.nutriplan.Globals.LONG_DASH;
import static com.cafape.nutriplan.support.Utils.myprint;

public class ActivityAddPatient2 extends AppCompatActivity
{
    private PatientEntity patientEntity;
    private PatientAnamnesisEntity patientAnamnesisEntity;
    private PatientAntropometryEntity patientAntropometryEntity;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient2);
        patientEntity = new PatientEntity();

        SectionsPagerAdapter_addpatient sectionsPagerAdapter_addpatient = new SectionsPagerAdapter_addpatient(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter_addpatient);

        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(viewPager != null) {
            int currentPage = viewPager.getCurrentItem();
            if(currentPage == 0) {
                finish();
            } else {
                int previousPage = currentPage - 1;
                viewPager.setCurrentItem(previousPage);
            }
        } else {
            this.finish();
        }
    }

    public void saveData() {
        if((patientEntity != null) && (patientAnamnesisEntity != null) && (patientAntropometryEntity != null)) {
            new SavePatient().execute();
        }
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public void setPatientAnamnesisEntity(PatientAnamnesisEntity patientAnamnesisEntity) {
        this.patientAnamnesisEntity = patientAnamnesisEntity;
    }

    public void setPatientAntropometryEntity(PatientAntropometryEntity patientAntropometryEntity) {
        this.patientAntropometryEntity = patientAntropometryEntity;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public PatientAnamnesisEntity getPatientAnamnesisEntity() {
        return patientAnamnesisEntity;
    }

    public PatientAntropometryEntity getPatientAntropometryEntity() {
        return patientAntropometryEntity;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    class SavePatient extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            //adding to database
            long patientID = DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase()
                    .patientDao()
                    .insert(patientEntity);

            patientAnamnesisEntity.setPatient_ref_ID(patientID);
            DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase()
                    .patientAnamnesisDao().insert(patientAnamnesisEntity);

            patientAntropometryEntity.setPatient_ref_ID(patientID);
            DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase()
                    .patientAntropometryDao().insert(patientAntropometryEntity);
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

    public String calculatePI(String height) {
        String res = "";
        if(patientEntity != null) {
            String sex = patientEntity.getSex();

            float height_float = Float.parseFloat(height);
            float divisor = 2;
            //LORENZ FORMULA

            if (sex.equals(getString(R.string.male_short))) {
                divisor = 4;
            }

            float result = height_float - 100 - (height_float - 150) / divisor;
            if (result > 0 && result < 1000) {
                return String.valueOf(result);
            }
        }
        return res;
    }

    public static String calculateBAI(String height, String hipscirc) {
        float height_float = Float.parseFloat(height);
        float hipscirc_float = Float.parseFloat(hipscirc);

        float result = (hipscirc_float / (float)Math.pow(height_float / 100, 1.5f)) - 18;

        if(result > 0 && result < 100) {
            DecimalFormat df = new DecimalFormat("###.##");
            return df.format(result);} else {
            return "0";
        }
    }

    public static String calculateBMI(String height, String weight) {
        float height_float = Float.parseFloat(height);
        float weight_float = Float.parseFloat(weight);

        float result = (1.3f * weight_float) / (float)Math.pow(height_float / 100, 2.5f);

        if(result > 0 && result < 100) {
            DecimalFormat df = new DecimalFormat("###.##");
            return df.format(result);
        } else {
            return "0";
        }
    }
}