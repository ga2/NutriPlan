package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.Utils;

import org.apache.commons.lang3.StringUtils;

import static com.cafape.nutriplan.Globals.COMMA;

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

    private CheckBox activityeditpatientnotes_checkbox_hypercolesterolemia;
    private CheckBox activityeditpatientnotes_checkbox_thyroidism;
    private CheckBox activityeditpatientnotes_checkbox_diabetes;
    private CheckBox activityeditpatientnotes_checkbox_kidneylaziness;
    private CheckBox activityeditpatientnotes_checkbox_osteoporosis;
    private CheckBox activityeditpatientnotes_checkbox_prostatitis;

    private EditText activityeditpatientnotes_editText_physicalactivity;
    private EditText activityeditpatientnotes_editText_workingactivity;
    private EditText activityeditpatientnotes_editText_welcomefood;
    private EditText activityeditpatientnotes_editText_nonwelcomefood;

    private LinearLayout activityeditpatientnotes_linearLayout_foodsuggestions;
    private TextView activityeditpatientnotes_textView_yesfood;
    private TextView activityeditpatientnotes_textView_nofood;

    private Button activityeditpatientnotes_button_save;

    private String yesfood = "";
    private String nofood = "";

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

            activityeditpatientnotes_checkbox_hypercolesterolemia = findViewById(R.id.activityeditpatientnotes_checkbox_hypercolesterolemia);
            activityeditpatientnotes_checkbox_thyroidism = findViewById(R.id.activityeditpatientnotes_checkbox_thyroidism);
            activityeditpatientnotes_checkbox_diabetes = findViewById(R.id.activityeditpatientnotes_checkbox_diabetes);
            activityeditpatientnotes_checkbox_kidneylaziness = findViewById(R.id.activityeditpatientnotes_checkbox_kidneylaziness);
            activityeditpatientnotes_checkbox_osteoporosis = findViewById(R.id.activityeditpatientnotes_checkbox_osteoporosis);
            activityeditpatientnotes_checkbox_prostatitis = findViewById(R.id.activityeditpatientnotes_checkbox_prostatitis);

            activityeditpatientnotes_linearLayout_foodsuggestions = findViewById(R.id.activityeditpatientnotes_linearLayout_foodsuggestions);
            activityeditpatientnotes_textView_yesfood = findViewById(R.id.activityeditpatientnotes_textView_yesfood);
            activityeditpatientnotes_textView_nofood = findViewById(R.id.activityeditpatientnotes_textView_nofood);

            setCheckboxes();
            activityeditpatientnotes_checkbox_hypercolesterolemia.setChecked(patientEntity.isPathologiesHasHypercholesterolaemia());
            activityeditpatientnotes_checkbox_thyroidism.setChecked(patientEntity.isPathologiesHasThyroidism());
            activityeditpatientnotes_checkbox_diabetes.setChecked(patientEntity.isPathologiesHasDiabetes());
            activityeditpatientnotes_checkbox_kidneylaziness.setChecked(patientEntity.isPathologiesHasKidneysLaziness());
            activityeditpatientnotes_checkbox_osteoporosis.setChecked(patientEntity.isPathologiesHasOsteoporosis());
            activityeditpatientnotes_checkbox_prostatitis.setChecked(patientEntity.isPathologiesHasProstatitis());

            setIntestine(patientEntity.getInstestine());
            setMenstrualCycle(patientEntity.getMenstrualCycle());
            activityeditpatientnotes_editText_physicalactivity.setText(patientEntity.getPhysicalActivity_details());
            activityeditpatientnotes_editText_workingactivity.setText(patientEntity.getWorkinglActivity_details());
            if(patientAnamnesisEntity != null) {
                activityeditpatientnotes_editText_welcomefood.setText(patientAnamnesisEntity.getWelcomefood());
                activityeditpatientnotes_editText_nonwelcomefood.setText(patientAnamnesisEntity.getNonwelcomefood());
            }
        } else {
            finish();
        }
    }

    public void setCheckboxes() {
        activityeditpatientnotes_checkbox_hypercolesterolemia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patientEntity.setPathologiesHasHypercholesterolaemia(b);
                if(b) {
                    yesfood = yesfood + getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_y) + COMMA;
                    nofood = nofood + getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_n) + COMMA;
                } else {
                    yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_y), "");
                    nofood = nofood.replace(getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_n), "");
                }
                updateYesNoFood();
                checkUncheckFoodTextBox(b);
            }
        });

        activityeditpatientnotes_checkbox_thyroidism.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patientEntity.setPathologiesHasThyroidism(b);
                if(b) {
                    //yesfood = yesfood + getString(R.string.activityaddpatient_string_thyroidism_food_y) + COMMA;
                    nofood = nofood + getString(R.string.activityaddpatient_string_thyroidism_food_n) + COMMA;
                } else {
                    //yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_thyroidism_food_y), "");
                    nofood = nofood.replace(getString(R.string.activityaddpatient_string_thyroidism_food_n), "");
                }
                updateYesNoFood();
                checkUncheckFoodTextBox(b);
            }
        });


        activityeditpatientnotes_checkbox_diabetes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patientEntity.setPathologiesHasDiabetes(b);
                /*if(b) {
                    yesfood = yesfood + getString(R.string.activityaddpatient_string_diabetes_food_y) + COMMA;
                    nofood = nofood + getString(R.string.activityaddpatient_string_diabetes_food_n) + COMMA;
                } else {
                    yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_diabetes_food_y), "");
                    nofood = nofood.replace(getString(R.string.activityaddpatient_string_diabetes_food_n), "");
                }
                updateYesNoFood();
                checkUncheckFoodTextBox(b);*/
            }
        });

        activityeditpatientnotes_checkbox_kidneylaziness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patientEntity.setPathologiesHasKidneysLaziness(b);
                if(b) {
                    //yesfood = yesfood + getString(R.string.activityaddpatient_string_kidneyslaziness_food_y) + COMMA;
                    nofood = nofood + getString(R.string.activityaddpatient_string_kidneyslaziness_food_n) + COMMA;
                } else {
                    //yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_kidneyslaziness_food_y), "");
                    nofood = nofood.replace(getString(R.string.activityaddpatient_string_kidneyslaziness_food_n), "");
                }
                updateYesNoFood();
                checkUncheckFoodTextBox(b);
            }
        });

        activityeditpatientnotes_checkbox_osteoporosis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patientEntity.setPathologiesHasOsteoporosis(b);
                if(b) {
                    yesfood = yesfood + getString(R.string.activityaddpatient_string_osteoporosis_food_y) + COMMA;
                    //nofood = nofood + getString(R.string.activityaddpatient_string_osteoporosis_food_n) + COMMA;
                } else {
                    yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_osteoporosis_food_y), "");
                    //nofood = nofood.replace(getString(R.string.activityaddpatient_string_osteoporosis_food_n), "");
                }
                updateYesNoFood();
                checkUncheckFoodTextBox(b);
            }
        });

        activityeditpatientnotes_checkbox_prostatitis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                patientEntity.setPathologiesHasProstatitis(b);
                if(b) {
                    yesfood = yesfood + getString(R.string.activityaddpatient_string_prostatitis_food_y) + COMMA;
                    nofood = nofood + getString(R.string.activityaddpatient_string_prostatitis_food_n) + COMMA;
                } else {
                    yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_prostatitis_food_y), "");
                    nofood = nofood.replace(getString(R.string.activityaddpatient_string_prostatitis_food_n), "");
                }
                updateYesNoFood();
                checkUncheckFoodTextBox(b);
            }
        });
    }

    public void updateYesNoFood() {
        activityeditpatientnotes_textView_yesfood.setText(StringUtils.stripEnd(StringUtils.strip(yesfood.replace(", ,", ",")), COMMA));
        activityeditpatientnotes_textView_nofood.setText(StringUtils.stripEnd(StringUtils.strip(nofood.replace(", ,", ",")), COMMA));
    }

    public void setUiComponents() {
        activityeditpatientnotes_button_save = findViewById(R.id.activityeditpatientnotes_button_save);

        activityeditpatientnotes_button_save.setOnClickListener(view -> {
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
        if(patientAnamnesisEntity != null) {
            patientAnamnesisEntity.setWelcomefood(Utils.getStringFromEditText(activityeditpatientnotes_editText_welcomefood));
            patientAnamnesisEntity.setNonwelcomefood(Utils.getStringFromEditText(activityeditpatientnotes_editText_nonwelcomefood));
        }

        updateDataEntity();
        });
    }

    public void checkUncheckFoodTextBox(boolean activation) {
        if(activation) {
            if(nofood.isEmpty() || yesfood.isEmpty()){
                 if(activityeditpatientnotes_linearLayout_foodsuggestions.getVisibility() != View.VISIBLE) {
                    activityeditpatientnotes_linearLayout_foodsuggestions.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if(activityeditpatientnotes_linearLayout_foodsuggestions.getVisibility() == View.VISIBLE) {
                if(nofood.isEmpty() && yesfood.isEmpty()) {
                    activityeditpatientnotes_linearLayout_foodsuggestions.setVisibility(View.GONE);
                }
            }
        }

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