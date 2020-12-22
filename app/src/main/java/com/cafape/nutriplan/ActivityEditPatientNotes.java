package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.Utils;

public class ActivityEditPatientNotes extends AppCompatActivity
{
    private PatientEntity patientEntity;
    private PatientAnamnesisEntity patientAnamnesisEntity;

    private EditText activityeditpatientnotes_editText_visitreason;
    private EditText activityeditpatientnotes_editText_previouspathologies;
    private EditText activityeditpatientnotes_editText_hereditarypathologies;
    private EditText activityeditpatientnotes_editText_allergies;
    private EditText activityeditpatientnotes_editText_productsassumption;
    private RadioButton activityeditpatientnotes_radio_menstrualcycle_regular;
    private RadioButton activityeditpatientnotes_radio_menstrualcycle_irregular;
    private RadioButton activityeditpatientnotes_radio_menstrualcycle_absent;
    private RadioButton activityeditpatientnotes_radio_intestine_diarrheal;
    private RadioButton activityeditpatientnotes_radio_intestine_constipated;
    private RadioButton activityeditpatientnotes_radio_intestine_alternate;
    private RadioButton activityeditpatientnotes_radio_intestine_regular;
    private EditText activityeditpatientnotes_editText_physicalactivity;
    private EditText activityeditpatientnotes_editText_workingactivity;
    private EditText activityeditpatientnotes_editText_welcomefood;
    private EditText activityeditpatientnotes_editText_nonwelcomefood;

    private Button activityeditpatientnotes_button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_notes);

        initPatientInfo();
        setUiComponents();
    }

    public void initPatientInfo() {
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("args");
        if((args != null) && args.containsKey("patientObject") && args.containsKey("patientAnamnesisObject")) {
            patientEntity = (PatientEntity) args.getSerializable("patientObject");
            patientAnamnesisEntity = (PatientAnamnesisEntity) args.getSerializable("patientAnamnesisObject");

            activityeditpatientnotes_editText_visitreason = findViewById(R.id.activityeditpatientnotes_editText_visitreason);
            activityeditpatientnotes_editText_previouspathologies = findViewById(R.id.activityeditpatientnotes_editText_previouspathologies);
            activityeditpatientnotes_editText_hereditarypathologies = findViewById(R.id.activityeditpatientnotes_editText_hereditarypathologies);
            activityeditpatientnotes_editText_allergies = findViewById(R.id.activityeditpatientnotes_editText_allergies);
            activityeditpatientnotes_editText_productsassumption = findViewById(R.id.activityeditpatientnotes_editText_productsassumption);

            activityeditpatientnotes_radio_menstrualcycle_regular = findViewById(R.id.activityeditpatientnotes_radio_menstrualcycle_regular);
            activityeditpatientnotes_radio_menstrualcycle_irregular = findViewById(R.id.activityeditpatientnotes_radio_menstrualcycle_irregular);
            activityeditpatientnotes_radio_menstrualcycle_absent = findViewById(R.id.activityeditpatientnotes_radio_menstrualcycle_absent);
            activityeditpatientnotes_editText_physicalactivity = findViewById(R.id.activityeditpatientnotes_editText_physicalactivity);
            activityeditpatientnotes_editText_workingactivity = findViewById(R.id.activityeditpatientnotes_editText_workingactivity);
            activityeditpatientnotes_editText_welcomefood = findViewById(R.id.activityeditpatientnotes_editText_welcomefood);
            activityeditpatientnotes_editText_nonwelcomefood = findViewById(R.id.activityeditpatientnotes_editText_nonwelcomefood);

            activityeditpatientnotes_radio_intestine_diarrheal = findViewById(R.id.activityeditpatientnotes_radio_intestine_diarrheal);
            activityeditpatientnotes_radio_intestine_constipated = findViewById(R.id.activityeditpatientnotes_radio_intestine_constipated);
            activityeditpatientnotes_radio_intestine_alternate = findViewById(R.id.activityeditpatientnotes_radio_intestine_alternate);
            activityeditpatientnotes_radio_intestine_regular = findViewById(R.id.activityeditpatientnotes_radio_intestine_regular);

            activityeditpatientnotes_editText_visitreason.setText(patientEntity.getVisitReason());
            activityeditpatientnotes_editText_previouspathologies.setText(patientEntity.getPreviousPathologies_details());
            activityeditpatientnotes_editText_hereditarypathologies.setText(patientEntity.getHereditaryPathologies_details());
            activityeditpatientnotes_editText_allergies.setText(patientEntity.getAllergies_details());
            activityeditpatientnotes_editText_productsassumption.setText(patientEntity.getProductsAssumption_details());

            setIntestine(patientEntity.getInstestine());
            setMenstrualCycle(patientEntity.getMenstrualCycle());
            activityeditpatientnotes_editText_physicalactivity.setText(patientEntity.getPhysicalActivity_details());
            activityeditpatientnotes_editText_workingactivity.setText(patientEntity.getWorkinglActivity_details());
            activityeditpatientnotes_editText_welcomefood.setText(patientAnamnesisEntity.getWelcomefood());
            activityeditpatientnotes_editText_nonwelcomefood.setText(patientAnamnesisEntity.getNonwelcomefood());
        } else {
            finish();
        }
    }

    public void setUiComponents() {
        activityeditpatientnotes_button_save = findViewById(R.id.activityeditpatientnotes_button_save);

        activityeditpatientnotes_button_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                patientEntity.setVisitReason(Utils.getStringFromEditText(activityeditpatientnotes_editText_visitreason));
                String previousPatholgies = Utils.getStringFromEditText(activityeditpatientnotes_editText_previouspathologies);
                patientEntity.setPreviousPathologies_details(previousPatholgies);
                patientEntity.setPreviousPathologies_status(hasText(previousPatholgies));
                String hereditaryPatholgies = Utils.getStringFromEditText(activityeditpatientnotes_editText_hereditarypathologies);
                patientEntity.setHereditaryPathologies_details(hereditaryPatholgies);
                patientEntity.setHereditaryPathologies_status(hasText(hereditaryPatholgies));
                String allergies = Utils.getStringFromEditText(activityeditpatientnotes_editText_allergies);
                patientEntity.setAllergies_details(allergies);
                patientEntity.setAllergies_status(hasText(allergies));
                String productsAssumption = Utils.getStringFromEditText(activityeditpatientnotes_editText_productsassumption);
                patientEntity.setProductsAssumption_details(productsAssumption);
                patientEntity.setProductsAssumption_status(hasText(productsAssumption));
                patientEntity.setInstestine(getTagFromRadioGroup(R.id.activityeditpatientnotes_radioGroup_intestine));
                patientEntity.setMenstrualCycle(getTagFromRadioGroup(R.id.activityeditpatientnotes_radioGroup_menstrualcycle));
                String physiscalActivity = Utils.getStringFromEditText(activityeditpatientnotes_editText_physicalactivity);
                patientEntity.setPhysicalActivity_details(physiscalActivity);
                patientEntity.setPhysicalActivity_status(hasText(physiscalActivity));
                String workingActivity = Utils.getStringFromEditText(activityeditpatientnotes_editText_workingactivity);
                patientEntity.setWorkinglActivity_details(workingActivity);
                patientEntity.setWorkingActivity_status(hasText(workingActivity));
                patientAnamnesisEntity.setWelcomefood(Utils.getStringFromEditText(activityeditpatientnotes_editText_welcomefood));
                patientAnamnesisEntity.setNonwelcomefood(Utils.getStringFromEditText(activityeditpatientnotes_editText_nonwelcomefood));
                updateDataEntity();
            }
        });
    }

    public void updateDataEntity() {
        class updateData extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase().patientDao().updateWithID(
                        patientEntity.getPatiendID(),
                        patientEntity.getVisitReason(),
                        patientEntity.getPreviousPathologies_details(),
                        patientEntity.isPreviousPathologies_status(),
                        patientEntity.getHereditaryPathologies_details(),
                        patientEntity.isHereditaryPathologies_status(),
                        patientEntity.getAllergies_details(),
                        patientEntity.isAllergies_status(),
                        patientEntity.getProductsAssumption_details(),
                        patientEntity.isProductsAssumption_status(),
                        patientEntity.getInstestine(),
                        patientEntity.getMenstrualCycle(),
                        patientEntity.getPhysicalActivity_details(),
                        patientEntity.isPhysicalActivity_status(),
                        patientEntity.getWorkinglActivity_details(),
                        patientEntity.isWorkingActivity_status()
                );
                DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase().patientAnamnesisDao().updateWithID(patientAnamnesisEntity.getAnamnesisID(), patientAnamnesisEntity.getWelcomefood(),
                        patientAnamnesisEntity.getNonwelcomefood());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent data = new Intent();
                data.putExtra("updatedPatientEntity", patientEntity);
                data.putExtra("updatedPatientAnamnesisEntity", patientAnamnesisEntity);
                setResult(RESULT_OK, data);
                finish();
                Toast.makeText(getApplicationContext(), getString(R.string.edited), Toast.LENGTH_LONG).show();
            }
        }

        new updateData().execute();
    }

    public boolean hasText(String string) {
        return !string.isEmpty();
    }

    public void setIntestine(int tagValue) {

        switch (tagValue) {
            case 1: {activityeditpatientnotes_radio_intestine_diarrheal.setChecked(true);}
            case 2: {activityeditpatientnotes_radio_intestine_constipated.setChecked(true);}
            case 3: {activityeditpatientnotes_radio_intestine_alternate.setChecked(true);}
            case 4: {activityeditpatientnotes_radio_intestine_regular.setChecked(true);}
        }
    }

    public void setMenstrualCycle(int tagValue) {
        switch (tagValue) {
            case 1: {activityeditpatientnotes_radio_menstrualcycle_regular.setChecked(true);}
            case 2: {activityeditpatientnotes_radio_menstrualcycle_irregular.setChecked(true);}
            case 3: {activityeditpatientnotes_radio_menstrualcycle_absent.setChecked(true);}
        }
    }

    public int getTagFromRadioGroup(int radioGroupReference) {
        RadioGroup radioGroup = findViewById(radioGroupReference);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        return Integer.parseInt((String) radioButton.getTag());
    }
}