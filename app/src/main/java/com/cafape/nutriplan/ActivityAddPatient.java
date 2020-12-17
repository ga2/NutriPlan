package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.AlertBuilderUtils;
import com.cafape.nutriplan.support.Utils;
import com.cafape.nutriplan.ui.CustomDatePicker;

import java.util.Calendar;
import java.util.Date;

public class ActivityAddPatient extends AppCompatActivity
{
    private Context context;

    private CustomDatePicker activityaddpatient_datepicker_bdate;
    private EditText activityaddpatient_editText_age;
    private Button activityaddpatient_button_save;
    private PatientEntity patientEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        context = getApplicationContext();

        setUIComponents();
        setListeners();
    }

    public void setUIComponents() {
        activityaddpatient_editText_age = findViewById(R.id.activityaddpatient_editText_age);
        activityaddpatient_datepicker_bdate = findViewById(R.id.activityaddpatient_datepicker_bdate);
        activityaddpatient_button_save = findViewById(R.id.activityaddpatient_button_save);
    }

    public void setListeners() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        activityaddpatient_datepicker_bdate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar_bday = Calendar.getInstance();
                calendar_bday.set(Calendar.YEAR, year);
                calendar_bday.set(Calendar.MONTH, month);
                calendar_bday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date bdayDate = calendar_bday.getTime();
                int age_int = Utils.calculateAge(bdayDate);
                String age_string = getString(R.string.error).toUpperCase();
                if(age_int > -1) {
                    age_string = String.valueOf(age_int);
                }
                activityaddpatient_editText_age.setText(age_string);
            }
        });

        activityaddpatient_button_save.setOnClickListener(new View.OnClickListener()
        {
            SavePatient savePatient = new SavePatient();

            @Override
            public void onClick(View view) {
                String patientEntity_name = Utils.upperCaseAllFirst(((EditText)findViewById(R.id.activityaddpatient_editText_name)).getText().toString());
                String patientEntity_surname = Utils.upperCaseAllFirst(((EditText)findViewById(R.id.activityaddpatient_editText_surname)).getText().toString());

                Date patientEntity_bdate = Utils.getDateFromDatePicker(activityaddpatient_datepicker_bdate);
                String patientEntity_phone = ((EditText)findViewById(R.id.activityaddpatient_editText_phone)).getText().toString();
                if(!patientEntity_name.isEmpty() && !patientEntity_surname.isEmpty() && !patientEntity_phone.isEmpty()) {
                    String patientEntity_sex = getTagFromRadioGroup(R.id.activityaddpatient_radioGroup_sex);
                    int patientEntity_intestine = Integer.valueOf(getTagFromRadioGroup(R.id.activityaddpatient_radioGroup_intestine));
                    int patientEntity_menstrualCycle = Integer.valueOf(getTagFromRadioGroup(R.id.activityaddpatient_radioGroup_intestine));
                    String patientEntity_visitReason = ((EditText)findViewById(R.id.activityaddpatient_editText_visitReason)).getText().toString();
                    String patientEntity_previousDiets_details = ((EditText)findViewById(R.id.activityaddpatient_editText_previousDiets)).getText().toString();
                    boolean patientEntity_previousDiets_status = !patientEntity_previousDiets_details.isEmpty();
                    String patientEntity_weightHistory = ((EditText)findViewById(R.id.activityaddpatient_editText_weightHistory)).getText().toString();
                    String patientEntity_previousPathologies_details = ((EditText)findViewById(R.id.activityaddpatient_editText_previousPathologies)).getText().toString();
                    boolean patientEntity_previousPathologies_status = !patientEntity_previousPathologies_details.isEmpty();
                    String patientEntity_hereditaryPathologies_details = ((EditText)findViewById(R.id.activityaddpatient_editText_hereditaryPathologies)).getText().toString();
                    boolean patientEntity_hereditaryPathologies_status = !patientEntity_hereditaryPathologies_details.isEmpty();
                    String patientEntity_allergies_details = ((EditText)findViewById(R.id.activityaddpatient_editText_allergies)).getText().toString();
                    boolean patientEntity_allergies_status = !patientEntity_allergies_details.isEmpty();
                    String patientEntity_productsAssumption_details = ((EditText)findViewById(R.id.activityaddpatient_editText_productsAssumption)).getText().toString();
                    boolean patientEntity_productsAssumption_status = !patientEntity_productsAssumption_details.isEmpty();
                    String patientEntity_physicalActivity_details = ((EditText)findViewById(R.id.activityaddpatient_editText_physicalActivity)).getText().toString();
                    boolean patientEntity_physicalActivity_status = !patientEntity_physicalActivity_details.isEmpty();
                    String patientEntity_workingActivity_details = ((EditText)findViewById(R.id.activityaddpatient_editText_workingActivity)).getText().toString();
                    boolean patientEntity_workingActivity_status = !patientEntity_workingActivity_details.isEmpty();
                    patientEntity = new PatientEntity(patientEntity_name, patientEntity_surname, patientEntity_bdate,
                                                    patientEntity_sex, patientEntity_phone,
                                                    patientEntity_visitReason, patientEntity_previousDiets_status, patientEntity_previousDiets_details,
                                                    patientEntity_weightHistory, patientEntity_previousPathologies_status, patientEntity_previousPathologies_details,
                                                    patientEntity_hereditaryPathologies_status, patientEntity_hereditaryPathologies_details,
                                                    patientEntity_allergies_status, patientEntity_allergies_details,
                                                    patientEntity_productsAssumption_status, patientEntity_productsAssumption_details,
                                                    patientEntity_intestine, patientEntity_menstrualCycle,
                                                    patientEntity_physicalActivity_status, patientEntity_physicalActivity_details,
                                                    patientEntity_workingActivity_status, patientEntity_workingActivity_details);
                    savePatient.execute();
                } else {
                    String message = getString(R.string.activityaddpatient_string_alertTitle_formCheck);
                    if(patientEntity_name.isEmpty()) {
                        message += getString(R.string.activityaddpatient_string_alertMessage_formCheck_name);
                    }
                    if(patientEntity_surname.isEmpty()) {
                        message += getString(R.string.activityaddpatient_string_alertMessage_formCheck_surname);
                    }
                    if(patientEntity_phone.isEmpty()) {
                        message += getString(R.string.activityaddpatient_string_alertMessage_formCheck_phone);
                    }

                    AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(ActivityAddPatient.this, R.string.error, message);
                    builder.setPositiveButton(R.string.back, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    public String getTagFromRadioGroup(int radioGroupReference) {
        RadioGroup radioGroup = findViewById(radioGroupReference);
        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        return radioButton.getTag().toString();
    }

    class SavePatient extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            //adding to database
            DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase()
                    .patientDao()
                    .insert(patientEntity);
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