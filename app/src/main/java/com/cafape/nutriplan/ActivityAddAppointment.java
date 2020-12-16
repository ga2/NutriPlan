package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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
import com.cafape.nutriplan.support.AlertBuilderUtils;
import com.cafape.nutriplan.support.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import static com.cafape.nutriplan.Globals.DATEFORMAT_DISPLAY;

public class ActivityAddAppointment extends AppCompatActivity
{
    private Context context;

    private LocalDate activityaddappointment_localDate;
    private TimePicker activityaddappointment_timePicker;
    private AutoCompleteTextView activityaddappointment_autocompleteTextView;
    private EditText activityaddappointment_editText_visitReason;
    private RecyclerView activityaddappointment_recyclerView;
    private AppointmentsRecyclerViewAdapter appointmentAddRecyclerViewAdapter;
    private ArrayList<AppointmentEntity> arrayList_appointmentEntity_ofTheDay;
    private TextView activityaddappointment_textView_nodata_details;
    private ConstraintLayout activityaddappointment_constraintLayout_nodata;
    private HashMap<String, PatientEntity> activityaddappontment_hashMap_patients;
    private PatientEntity patientEntity_toInsert;
    private Button activityaddappointment_button_add;
    private ArrayAdapter<String> activityaddappointment_adapter_patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        context = getApplicationContext();

        setUiComponents();
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("appointmentsOfTheDay");
        initDate(args);
        initAppointmentoOfTheDay(args);
        getPatients();
        setListeners();
    }

    //todo non aprire se non ci sono pazienti
    //salvato? visualizza boh

    public void setUiComponents() {
        activityaddappointment_timePicker = findViewById(R.id.activityaddappointment_timePicker);
        activityaddappointment_autocompleteTextView = findViewById(R.id.activityaddappointment_autocompleteTextView);
        activityaddappointment_editText_visitReason = findViewById(R.id.activityaddappointment_editText_visitReason);
        activityaddappointment_recyclerView = findViewById(R.id.activityaddappointment_recyclerView);
        activityaddappointment_button_add = findViewById(R.id.activityaddappointment_button_add);
    }

    public void setListeners() {
        activityaddappointment_button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patientString = activityaddappointment_autocompleteTextView.getText().toString();
                int patientPosition = activityaddappointment_adapter_patients.getPosition(patientString);
                if(patientPosition > - 1) {
                    patientEntity_toInsert = activityaddappontment_hashMap_patients.get(patientString);
                    saveAppointment(activityaddappointment_timePicker.getHour(), activityaddappointment_timePicker.getMinute(), activityaddappointment_localDate, activityaddappointment_editText_visitReason.getText().toString(), patientEntity_toInsert.getPatiendID());
                } else {
                    String message = getString(R.string.activityaddpatient_string_alertTitle_formCheck);
                    message += getString(R.string.activityaddappointment_string_alertMessage_formCheck_name);

                    AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(ActivityAddAppointment.this, R.string.error, message);
                    builder.setPositiveButton(R.string.back, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    public void initAppointmentoOfTheDay(Bundle args) {
        arrayList_appointmentEntity_ofTheDay = (ArrayList<AppointmentEntity>) args.getSerializable("appointmentsOfTheDay");

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
                    list.add(patient.getName() + " " + patient.getSurname() + " - " + Utils.convertDateFormat(patient.getBirthDate(), DATEFORMAT_DISPLAY));
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
    /*
    private void getAppointments() {
        class GetAppointments extends AsyncTask<Void, Void, List<AppointmentEntity>>
        {
            @Override
            protected List<AppointmentEntity> doInBackground(Void... voids) {
                List<AppointmentEntity> appointmentList = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .appointmentDao()
                        .getAppointmentOfDay(String.valueOf(activityappointments_calendarView.getSelectedDate().getMonth()));
                return appointmentList;
            }

            @Override
            protected void onPostExecute(List<AppointmentEntity> appointments) {
                super.onPostExecute(appointments);
                for (AppointmentEntity appointment : appointments) {
                    System.out.println(appointment.getPatientID_ref());
                }
                appointmentAddRecyclerViewAdapter = new AppointmentsRecyclerViewAdapter(context, appointments);
                activityaddappointment_recyclerView.setLayoutManager(new LinearLayoutManager(context));
                activityaddappointment_recyclerView.setAdapter(appointmentAddRecyclerViewAdapter);
                appointmentAddRecyclerViewAdapter.notifyDataSetChanged();

                if(appointmentAddRecyclerViewAdapter.getItemCount() == 0) {
                    activityaddappointment_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                    activityaddappointment_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                    activityaddappointment_textView_nodata_details.setText(getString(R.string.activityappointments_string_nodata_details));
                    activityaddappointment_textView_nodata_details.setVisibility(View.VISIBLE);
                    activityaddappointment_constraintLayout_nodata.setVisibility(View.VISIBLE);
                } else {
                    if(activityaddappointment_textView_nodata_details == null) {
                        activityaddappointment_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                        activityaddappointment_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                    }
                    activityaddappointment_textView_nodata_details.setText("");
                    activityaddappointment_constraintLayout_nodata.setVisibility(View.GONE);
                }
                //Call click method
                appointmentAddRecyclerViewAdapter.setEditAppointmentClickListener(new AppointmentsRecyclerViewAdapter.EditAppointmentClickListener() {
                    @Override
                    public void onItemClick(String name) {
                        //openWhatsapp(name);
                    }
                });
            }
        }

        GetAppointments getPatients = new GetAppointments();
        getPatients.execute();
    }
    */
    public void saveAppointment(int hour, int minutes, LocalDate localDate, String visitReason, int patient_id) {
        class SaveAppointment extends AsyncTask<Void, Void, Void>
        {
            private AppointmentEntity appointmentEntity;
            private PatientWithAppointments patientWithAppointments;

            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                Calendar calendar = Calendar.getInstance();
                calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth(), hour, minutes);

                appointmentEntity = new AppointmentEntity(calendar.getTime(), visitReason, patient_id);
                DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase().appointmentDao().insert(appointmentEntity);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent data = new Intent();
                data.putExtra("newAppointmentEntity", appointmentEntity);
                setResult(RESULT_OK, data);
                finish();
                Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();
            }
        }
        new SaveAppointment().execute();
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
}