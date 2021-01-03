package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cafape.nutriplan.adapters.AppointmentsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.database.entities.PatientWithAppointments;
import com.cafape.nutriplan.objects.SimpleAppointment;
import com.cafape.nutriplan.support.Globals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import static com.cafape.nutriplan.support.Globals.COMMA;

public class ActivityAddAppointment extends AppCompatActivity
{
    private Context context;

    private LocalDate activityaddappointment_localDate;
    private TimePicker activityaddappointment_timePicker;
    private AutoCompleteTextView activityaddappointment_autocompleteTextView;
    private EditText activityaddappointment_editText_visitReason;
    private RecyclerView activityaddappointment_recyclerView;
    private AppointmentsRecyclerViewAdapter appointmentAddRecyclerViewAdapter;
    private ArrayList<SimpleAppointment> arrayList_appointmentEntity_ofTheDay;
    private TextView activityaddappointment_textView_nodata_details;
    private ConstraintLayout activityaddappointment_constraintLayout_nodata;
    private HashMap<String, PatientEntity> activityaddappontment_hashMap_patients;
    private PatientEntity patientEntity_toInsert;
    private Button activityaddappointment_button_add;
    private Button activityaddappointment_button_edit;
    private ArrayAdapter<String> activityaddappointment_adapter_patients;

    boolean editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        context = getApplicationContext();

        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("appointmentsOfTheDay");
        editMode = args.getBoolean("editMode");
        SimpleAppointment simpleAppointment_toEdit = null;
        if(editMode) {
            simpleAppointment_toEdit = (SimpleAppointment)args.getSerializable("appointmentToEdit");
        }

        initDate(args);
        setUiComponents();
        initAppointmentoOfTheDay(args);
        if(!editMode) {
            getPatients();
        } else {
            initEditMode(simpleAppointment_toEdit);
        }
        setListeners(simpleAppointment_toEdit);
    }

    //todo non aprire se non ci sono pazienti

    public void setUiComponents() {
        activityaddappointment_timePicker = findViewById(R.id.activityaddappointment_timePicker);
        activityaddappointment_timePicker.setIs24HourView(true);
        activityaddappointment_autocompleteTextView = findViewById(R.id.activityaddappointment_autocompleteTextView);
        activityaddappointment_editText_visitReason = findViewById(R.id.activityaddappointment_editText_visitReason);
        activityaddappointment_recyclerView = findViewById(R.id.activityaddappointment_recyclerView);
        activityaddappointment_button_add = findViewById(R.id.activityaddappointment_button_add);
        activityaddappointment_button_edit = findViewById(R.id.activityaddappointment_button_edit);
    }

    public void setListeners(SimpleAppointment simpleAppointment_toEdit) {
        activityaddappointment_button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientString = activityaddappointment_autocompleteTextView.getText().toString();
                int patientPosition = activityaddappointment_adapter_patients.getPosition(patientString);
                if(patientPosition > - 1) {
                    patientEntity_toInsert = activityaddappontment_hashMap_patients.get(patientString);
                    saveAppointment(activityaddappointment_timePicker.getHour(), activityaddappointment_timePicker.getMinute(), activityaddappointment_localDate, activityaddappointment_editText_visitReason.getText().toString(), patientEntity_toInsert.getPatiendID(), patientString);
                } else {
                    saveAppointment(activityaddappointment_timePicker.getHour(), activityaddappointment_timePicker.getMinute(), activityaddappointment_localDate, activityaddappointment_editText_visitReason.getText().toString(), 0, patientString);
                }


                /* else {
                    String message = getString(R.string.activityaddpatient_string_alertTitle_formCheck);
                    message += getString(R.string.activityaddappointment_string_alertMessage_formCheck_name);

                    AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(ActivityAddAppointment.this, R.string.error, message);
                    builder.setPositiveButton(R.string.back, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }*/
            }
        });
        if(editMode) {
            activityaddappointment_button_edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    editAppointment(simpleAppointment_toEdit, activityaddappointment_editText_visitReason.getText().toString(), activityaddappointment_timePicker.getHour(), activityaddappointment_timePicker.getMinute());
                }
            });
        }
    }

    public void initAppointmentoOfTheDay(Bundle args) {
        arrayList_appointmentEntity_ofTheDay = (ArrayList<SimpleAppointment>) args.getSerializable("arrayList");

        if(arrayList_appointmentEntity_ofTheDay != null && arrayList_appointmentEntity_ofTheDay.size() > 0) {
            appointmentAddRecyclerViewAdapter = new AppointmentsRecyclerViewAdapter(context, arrayList_appointmentEntity_ofTheDay);
            activityaddappointment_recyclerView.setLayoutManager(new LinearLayoutManager(context));
            activityaddappointment_recyclerView.setAdapter(appointmentAddRecyclerViewAdapter);
            appointmentAddRecyclerViewAdapter.notifyDataSetChanged();

            if (appointmentAddRecyclerViewAdapter.getItemCount() == 0) {
                activityaddappointment_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                activityaddappointment_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                activityaddappointment_textView_nodata_details.setText(getString(R.string.activityappointments_string_nodata_details));
                activityaddappointment_textView_nodata_details.setVisibility(View.VISIBLE);
                activityaddappointment_constraintLayout_nodata.setVisibility(View.VISIBLE);
            } else {
                if (activityaddappointment_textView_nodata_details == null) {
                    activityaddappointment_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                    activityaddappointment_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                }
                activityaddappointment_textView_nodata_details.setText("");
                activityaddappointment_constraintLayout_nodata.setVisibility(View.GONE);
            }
        } else {
            activityaddappointment_textView_nodata_details = findViewById(R.id.textView_nodata_details);
            activityaddappointment_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
            activityaddappointment_textView_nodata_details.setText(getString(R.string.activityappointments_string_nodata_details));
            activityaddappointment_textView_nodata_details.setVisibility(View.VISIBLE);
            activityaddappointment_constraintLayout_nodata.setVisibility(View.VISIBLE);
        }
    }

    public void initDate(Bundle args) {
        activityaddappointment_localDate = (LocalDate) args.getSerializable("dateSelected");
        setDate();
    }

    public void initEditMode(SimpleAppointment simpleAppointment_toEdit) {
        activityaddappointment_autocompleteTextView.setEnabled(false);
        activityaddappointment_button_add.setVisibility(View.GONE);
        activityaddappointment_button_edit.setVisibility(View.VISIBLE);

        activityaddappointment_timePicker.setHour(simpleAppointment_toEdit.getHour());
        activityaddappointment_timePicker.setMinute(simpleAppointment_toEdit.getMinutes());
        activityaddappointment_editText_visitReason.setText(simpleAppointment_toEdit.getVisitReason());
        class GetPatient extends AsyncTask<Void, Void, PatientEntity>
        {
            @Override
            protected PatientEntity doInBackground(Void... voids) {
                PatientEntity patientsList = DatabaseRepository
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .patientDao()
                        .getPatient(simpleAppointment_toEdit.getPatientID());
                return patientsList;
            }

            @Override
            protected void onPostExecute(PatientEntity patient) {
                super.onPostExecute(patient);
                activityaddappointment_autocompleteTextView.setText(patient.getNameSurnameBday(getString(R.string.of_the)));
            }
        }

        GetPatient getPatient = new GetPatient();
        getPatient.execute();
    }

    public void getPatients() {
        activityaddappontment_hashMap_patients = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        class GetPatients extends AsyncTask<Void, Void, List<PatientEntity>>
        {
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
                    activityaddappontment_hashMap_patients.put(patient.getNameSurnameBday(getString(R.string.of_the)), patient);
                    list.add(patient.getNameSurnameBday(getString(R.string.of_the)));
                }
                initAutocomplete(list);
            }
        }

        GetPatients getPatients = new GetPatients();
        getPatients.execute();
    }

    public void initAutocomplete(List list) {
        activityaddappointment_adapter_patients = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item, list);
        activityaddappointment_autocompleteTextView.setAdapter(activityaddappointment_adapter_patients);

        activityaddappointment_autocompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientEntity_toInsert = activityaddappontment_hashMap_patients.get(activityaddappointment_adapter_patients.getItem(position));
            }
        });

        activityaddappointment_autocompleteTextView.setThreshold(2);
    }

    public void saveAppointment(int hour, int minutes, LocalDate localDate, String visitReason, long patient_id, String patient_info) {
        class SaveAppointment extends AsyncTask<Void, Void, Void>
        {
            private AppointmentEntity appointmentEntity;
            private PatientWithAppointments patientWithAppointments;

            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                Calendar calendar = Calendar.getInstance();
                calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth(), hour, minutes);
                if(patient_id > 0) {
                    appointmentEntity = new AppointmentEntity(calendar.getTime(), visitReason, patient_id);
                } else {
                    appointmentEntity = new AppointmentEntity(calendar.getTime(), getHourFormatted(hour, minutes) + Globals.LONG_DASH + " " + patient_info + COMMA + visitReason, 0);
                }
                DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase().appointmentDao().insert(appointmentEntity);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent data = new Intent();
                data.putExtra("newAppointmentEntity", appointmentEntity);
                data.putExtra("patient_info", patient_info);
                setResult(RESULT_OK, data);
                finish();
                Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();
            }
        }
        new SaveAppointment().execute();
    }

    public void editAppointment(SimpleAppointment simpleAppointment_toEdit, String visitReason, int hour, int minutes) {
        class EditAppointment extends AsyncTask<Void, Void, AppointmentEntity>
        {
            @Override
            protected AppointmentEntity doInBackground(Void... voids) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(simpleAppointment_toEdit.getDate());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minutes);

                DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .appointmentDao()
                        .updateAppointmentByID(simpleAppointment_toEdit.getAppointmentID(), visitReason, calendar.getTime());

                AppointmentEntity appointment = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .appointmentDao()
                        .getAppointmentByID(simpleAppointment_toEdit.getAppointmentID());

                return appointment;
            }

            @Override
            protected void onPostExecute(AppointmentEntity appointmentEntity) {
                super.onPostExecute(appointmentEntity);
                Intent data = new Intent();
                data.putExtra("newAppointmentEntity", appointmentEntity);
                data.putExtra("patient_info", activityaddappointment_autocompleteTextView.getText().toString());
                setResult(RESULT_OK, data);
                finish();
                Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();
            }
        }
        new EditAppointment().execute();
    }

    public void setDate() {
        String year = String.valueOf(activityaddappointment_localDate.getYear());
        String month = activityaddappointment_localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        String day = String.valueOf(activityaddappointment_localDate.getDayOfMonth());

        TextView activityaddappointment_textView_day = findViewById(R.id.activityaddappointment_textView_day);
        TextView activityaddappointment_textView_monthYear = findViewById(R.id.activityaddappointment_textView_monthYear);
        activityaddappointment_textView_day.setText(day);
        activityaddappointment_textView_monthYear.setText(month + " " + year);
    }

    public String getHourFormatted(int hour, int minutes) {
        String minutes_string = String.valueOf(minutes);
        return String.format("%02d", hour) + ":" + minutes_string  + " ";
    }
}