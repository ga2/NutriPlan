package com.cafape.nutriplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.adapters.AppointmentsAndPatientsRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.PatientWithAppointmentsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.database.entities.PatientWithAppointments;
import com.cafape.nutriplan.objects.SimpleAppointment;
import com.cafape.nutriplan.objects.SimplePatientWithAppointment;
import com.cafape.nutriplan.support.AlertBuilderUtils;
import com.cafape.nutriplan.support.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;

import static com.cafape.nutriplan.Globals.DATEFORMAT;
import static com.cafape.nutriplan.Globals.REQCODE_EDITAPPOINTMENT;
import static com.cafape.nutriplan.Globals.REQCODE_NEWAPPOINTMENT_ADDED;
import static com.cafape.nutriplan.Globals.REQCODE_NEWPATIENT_ADDED;
import static com.cafape.nutriplan.Globals.TIMEFORMAT;
import static com.cafape.nutriplan.support.Utils.myprint;

public class ActivityAppointments extends AppCompatActivity
{
    private Context context;

    private MaterialCalendarView activityappointments_calendarView;
    private TextView activityappointments_textView_day;
    private TextView activityappointments_textView_monthYear;
    private RecyclerView activityappointments_recyclerView;
    private AppointmentsAndPatientsRecyclerViewAdapter appointmentsRecyclerViewAdapter;
    private ArrayList<SimpleAppointment> arrayList_appointmentEntity_ofTheDay;
    private TextView activityPatients_textView_nodata_details;
    private ConstraintLayout activityPatients_constraintLayout_nodata;
    private FloatingActionButton activitappointments_fab_appointment_add;
    private HashMap<String, EventDecoratorMonth> hashMap_calendarDecordatorReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        context = getApplicationContext();
        arrayList_appointmentEntity_ofTheDay = new ArrayList<SimpleAppointment>();
        hashMap_calendarDecordatorReference = new HashMap<>();

        setUiComponents();

        initCalendar();
        setListeners();
        getAppointmentsOfTheDay();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQCODE_NEWAPPOINTMENT_ADDED)) {
            if (resultCode == RESULT_OK) {
                AppointmentEntity appointmentEntity = (AppointmentEntity) data.getSerializableExtra("newAppointmentEntity");
                String patient_info = (String) data.getSerializableExtra("patient_info");
                appointmentsRecyclerViewAdapter.addToRetrievedData(new SimpleAppointment(appointmentEntity, patient_info));
                appointmentsRecyclerViewAdapter.notifyDataSetChanged();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(appointmentEntity.getAppointmentTime());
                arrayList_appointmentEntity_ofTheDay.add(new SimpleAppointment(appointmentEntity, patient_info));
                activityappointments_calendarView.addDecorator(new EventDecoratorMonth(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))));
                getAppointmentsOfTheDay();
            }
        } else if (requestCode == REQCODE_EDITAPPOINTMENT){
            if (resultCode == RESULT_OK) {
                AppointmentEntity appointmentEntity = (AppointmentEntity) data.getSerializableExtra("newAppointmentEntity");
                String patient_info = (String) data.getSerializableExtra("patient_info");
                appointmentsRecyclerViewAdapter.editRetievedDataByID(appointmentEntity, patient_info);
                appointmentsRecyclerViewAdapter.notifyDataSetChanged();
                updateAppointmentOfTheDay(new SimpleAppointment(appointmentEntity, patient_info), patient_info);
                getAppointmentsOfTheDay();
            }
        }
    }

    public void setUiComponents() {
        activityappointments_calendarView = findViewById(R.id.activityappointments_calendarView);
        activityappointments_textView_day = findViewById(R.id.activityappointments_textView_day);
        activityappointments_textView_monthYear = findViewById(R.id.activityappointments_textView_monthYear);
        activityappointments_recyclerView = findViewById(R.id.activityappointments_recyclerView);
        activitappointments_fab_appointment_add = findViewById(R.id.activitappointments_fab_appointment_add);
    }

    public void setListeners() {
        activityappointments_calendarView.setOnDateChangedListener(new OnDateSelectedListener()
        {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                getAppointmentsOfTheDay();
                setDate(String.valueOf(date.getDay()), date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), String.valueOf(date.getYear()));
            }
        });

        activityappointments_calendarView.setOnMonthChangedListener(new OnMonthChangedListener()
        {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                getAppointmentForCalendar(date.getYear(), date.getMonth());
                Toast.makeText(getApplicationContext(), date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), Toast.LENGTH_SHORT).show();
            }
        });

        activitappointments_fab_appointment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPatientsNumber();
            }
        });
    }

    public void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int year_int = calendar.get(Calendar.YEAR);
        int month_int = calendar.get(Calendar.MONTH) + 1;
        int day_int = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate localDate = LocalDate.of(year_int, month_int, day_int);
        setDate(String.valueOf(day_int), Utils.getMonthName(calendar), String.valueOf(year_int));

        activityappointments_calendarView.setSelectedDate(localDate);
        activityappointments_calendarView.setCurrentDate(localDate);

        getAppointmentForCalendar(year_int, month_int);
    }

    public void setDate(String day, String month, String year) {
        activityappointments_textView_day.setText(day);
        activityappointments_textView_monthYear.setText(month + " " + year);
    }

    public ArrayList<SimpleAppointment> getAppointmentsOfToday() {
        ArrayList<SimpleAppointment> arrayList_AppointmentstoDisplay = new ArrayList<>();
        int req_day = activityappointments_calendarView.getSelectedDate().getDay();

        for(SimpleAppointment simpleAppointment : arrayList_appointmentEntity_ofTheDay) {
            if(simpleAppointment.getDay() == req_day) {
                arrayList_AppointmentstoDisplay.add(simpleAppointment);
            }
        }
        return arrayList_AppointmentstoDisplay;
    }

    public void getAppointmentsOfTheDay() {
        class GetAppointmentsOfTheDay extends AsyncTask<Void, Void, List<SimpleAppointment>>
        {
            @Override
            protected List<SimpleAppointment> doInBackground(Void... voids) {
                return getAppointmentsOfToday();
            }

            @Override
            protected void onPostExecute(List<SimpleAppointment> appointments) {
                super.onPostExecute(appointments);

                appointmentsRecyclerViewAdapter = new AppointmentsAndPatientsRecyclerViewAdapter(context, appointments);
                activityappointments_recyclerView.setLayoutManager(new LinearLayoutManager(context));
                activityappointments_recyclerView.setAdapter(appointmentsRecyclerViewAdapter);
                appointmentsRecyclerViewAdapter.notifyDataSetChanged();

                if(appointmentsRecyclerViewAdapter.getItemCount() == 0) {
                    activityPatients_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                    activityPatients_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                    activityPatients_textView_nodata_details.setText(getString(R.string.activityappointments_string_nodata_details));
                    activityPatients_textView_nodata_details.setVisibility(View.VISIBLE);
                    activityPatients_constraintLayout_nodata.setVisibility(View.VISIBLE);
                } else {
                    if(activityPatients_textView_nodata_details == null) {
                        activityPatients_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                        activityPatients_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                    }
                    activityPatients_textView_nodata_details.setText("");
                    activityPatients_constraintLayout_nodata.setVisibility(View.GONE);
                }
                //Call click method
                appointmentsRecyclerViewAdapter.setEditAppointmentClickListener(new AppointmentsAndPatientsRecyclerViewAdapter.EditAppointmentClickListener() {
                    @Override
                    public void onItemClick(SimpleAppointment appointment_toEdit) {
                        Bundle args = new Bundle();
                        args.putSerializable("arrayList", (Serializable)arrayList_appointmentEntity_ofTheDay);
                        LocalDate dateSelected = activityappointments_calendarView.getSelectedDate().getDate();
                        args.putSerializable("dateSelected", (Serializable)dateSelected);
                        args.putSerializable("appointmentToEdit", (Serializable)appointment_toEdit);
                        args.putSerializable("editMode", true);

                        Intent intent_goToActivity = new Intent(context, ActivityAddAppointment.class);
                        intent_goToActivity.putExtra("appointmentsOfTheDay", args);
                        startActivityForResult(intent_goToActivity, REQCODE_EDITAPPOINTMENT);
                    }
                });

                appointmentsRecyclerViewAdapter.setDeleteAppointmentClickListener(new AppointmentsAndPatientsRecyclerViewAdapter.DeleteAppointmentClickListener() {
                    @Override
                    public void onItemClick(SimpleAppointment appointment_toDelete) {
                        AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(ActivityAppointments.this, R.string.warning, R.string.activityappointments_string_alertMessage_deleteappointment);
                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAppointment(appointment_toDelete);
                            }
                        });
                        builder.setNegativeButton(R.string.back, null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
        }

        GetAppointmentsOfTheDay getAppointmentsOfTheDay = new GetAppointmentsOfTheDay();
        getAppointmentsOfTheDay.execute();
    }

    public void deleteAppointment(SimpleAppointment simpleAppointment) {
        class DeleteAppointment extends AsyncTask<Void, Void, Integer>
        {
            @Override
            protected Integer doInBackground(Void... voids) {
                int deletingResult = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .appointmentDao()
                        .deleteByID(simpleAppointment.getAppointmentID());
                return deletingResult;
            }

            @Override
            protected void onPostExecute(Integer deletingResult) {
                super.onPostExecute(deletingResult);
                if ((deletingResult != null) && (deletingResult != null)) {
                    Toast.makeText(context, R.string.deleted, Toast.LENGTH_SHORT).show();
                    appointmentsRecyclerViewAdapter.deleteFromRetrievedData(simpleAppointment);
                    appointmentsRecyclerViewAdapter.notifyDataSetChanged();
                    if(appointmentsRecyclerViewAdapter.getItemCount() == 0) {
                        activityPatients_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                        activityPatients_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                        activityPatients_textView_nodata_details.setText(getString(R.string.activityappointments_string_nodata_details));
                        activityPatients_textView_nodata_details.setVisibility(View.VISIBLE);
                        activityPatients_constraintLayout_nodata.setVisibility(View.VISIBLE);
                        String decoratorKey = Utils.convertDateFormat(simpleAppointment.getDate(), DATEFORMAT);
                        activityappointments_calendarView.removeDecorator(hashMap_calendarDecordatorReference.get(decoratorKey));
                    } else {
                        if(activityPatients_textView_nodata_details == null) {
                            activityPatients_textView_nodata_details = findViewById(R.id.textView_nodata_details);
                            activityPatients_constraintLayout_nodata = findViewById(R.id.constraintLayout_nodata);
                        }
                        activityPatients_textView_nodata_details.setText("");
                        activityPatients_constraintLayout_nodata.setVisibility(View.GONE);
                    }
                }
            }
        }

        DeleteAppointment getDelete = new DeleteAppointment();
        getDelete.execute();
    }

    public void updateAppointmentOfTheDay(SimpleAppointment simpleAppointment_in, String patient_info) {
        long id = simpleAppointment_in.getAppointmentID();
        int pos = 0;
        for(SimpleAppointment appointmentEntity : arrayList_appointmentEntity_ofTheDay) {
            if(appointmentEntity.getAppointmentID() == id) {
                arrayList_appointmentEntity_ofTheDay.get(pos).setVisitReason(simpleAppointment_in.getVisitReason());
                arrayList_appointmentEntity_ofTheDay.get(pos).setDate(simpleAppointment_in.getDate());
                arrayList_appointmentEntity_ofTheDay.get(pos).generateDisplayText(patient_info);

                break;
            }
            pos++;
        }
    }

    public void getAppointmentForCalendar(int req_year, int req_month) {
        class GetAppointmentsForCalendar extends AsyncTask<Void, Void, List<AppointmentEntity>>
        {
            @Override
            protected List<AppointmentEntity> doInBackground(Void... voids) {
                List<AppointmentEntity> appointmentList = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .appointmentDao()
                        .getAppointmentsForMonth(String.valueOf(req_year),  String.format("%02d", req_month));

                arrayList_appointmentEntity_ofTheDay = new ArrayList<>();
                for(AppointmentEntity appointmentEntity : appointmentList) {
                    PatientEntity patientEntity = DatabaseRepository
                            .getInstance(context)
                            .getAppDatabase()
                            .patientDao()
                            .getPatient(appointmentEntity.getPatientID_ref());
                    arrayList_appointmentEntity_ofTheDay.add(new SimpleAppointment(appointmentEntity, patientEntity.getNameSurnameBday(context.getString(R.string.of_the))));
                }
                return appointmentList;
            }

            @Override
            protected void onPostExecute(List<AppointmentEntity> appointments) {
                super.onPostExecute(appointments);

                for(AppointmentEntity appointmentEntity : appointments) {
                    Date appointmentDate = appointmentEntity.getAppointmentTime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(appointmentDate);
                    EventDecoratorMonth eventDecoratorMonth = new EventDecoratorMonth(CalendarDay.from(
                            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));

                    hashMap_calendarDecordatorReference.put(Utils.convertDateFormat(appointmentDate, DATEFORMAT), eventDecoratorMonth);
                    activityappointments_calendarView.addDecorator(eventDecoratorMonth);
                }
            }
        }

        GetAppointmentsForCalendar getPatients = new GetAppointmentsForCalendar();
        getPatients.execute();
    }

    public class EventDecoratorMonth implements DayViewDecorator {
        private CalendarDay date = null;

        public EventDecoratorMonth(CalendarDay date) {
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_brightness_8));
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    private void getPatientsNumber() {
        class GetPatientsNumber extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                int numberPatients = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .patientDao()
                        .getPatientsNumber();

                return numberPatients;
            }

            @Override
            protected void onPostExecute(Integer patientNumber) {
                super.onPostExecute(patientNumber);
                if(patientNumber > 0) {
                    Bundle args = new Bundle();
                    args.putSerializable("arrayList", (Serializable) getAppointmentsOfToday());
                    LocalDate dateSelected = activityappointments_calendarView.getSelectedDate().getDate();
                    args.putSerializable("dateSelected", (Serializable) dateSelected);
                    args.putSerializable("editMode", false);

                    Intent intent_goToActivity = new Intent(context, ActivityAddAppointment.class);
                    intent_goToActivity.putExtra("appointmentsOfTheDay", args);
                    startActivityForResult(intent_goToActivity, REQCODE_NEWAPPOINTMENT_ADDED);
                } else {
                    String message = context.getString(R.string.activityappointments_string_alertMessage_blockopening);
                    AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(ActivityAppointments.this, R.string.error, message);
                    builder.setPositiveButton(R.string.back, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        }

        GetPatientsNumber getPatientsNumber = new GetPatientsNumber();
        getPatientsNumber.execute();
    }
}