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
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.adapters.AppointmentsAndPatientsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientWithAppointments;
import com.cafape.nutriplan.objects.SimpleAppointment;
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
import java.util.List;
import java.util.Locale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;

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
    private ArrayList<AppointmentEntity> arrayList_appointmentEntity_ofTheDay;
    private TextView activityPatients_textView_nodata_details;
    private ConstraintLayout activityPatients_constraintLayout_nodata;
    private FloatingActionButton activitappointments_fab_appointment_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        context = getApplicationContext();
        arrayList_appointmentEntity_ofTheDay = new ArrayList<AppointmentEntity>();

        setUiComponents();

        initCalendar();
        setListeners();
        getAppointmentsOfTheDay();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE_NEWPATIENT_ADDED) {
            if (resultCode == RESULT_OK) {
                //todo
               /* AppointmentEntity appointmentEntity = (AppointmentEntity)data.getSerializableExtra("newAppointmentEntity");
                appointmentsRecyclerViewAdapter.addToRetrievedData(appointmentEntity);
                appointmentsRecyclerViewAdapter.notifyDataSetChanged();*/
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
                //todo update on activityresult arrayList_appointmentEntity_ofTheDay
                Bundle args = new Bundle();
                args.putSerializable("arrayList", (Serializable)arrayList_appointmentEntity_ofTheDay);
                LocalDate dateSelected = activityappointments_calendarView.getSelectedDate().getDate();
                args.putSerializable("dateSelected", (Serializable)dateSelected);

                Intent intent_goToActivity = new Intent(context, ActivityAddAppointment.class);
                intent_goToActivity.putExtra("appointmentsOfTheDay", args);
                startActivityForResult(intent_goToActivity, REQCODE_NEWAPPOINTMENT_ADDED);
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

    /*

    public void getAppointments() {
        class GetAppointments extends AsyncTask<Void, Void, List<AppointmentEntity>>
        {
            @Override
            protected List<AppointmentEntity> doInBackground(Void... voids) {
                List<AppointmentEntity> appointmentList = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .appointmentDao()
                        .getAppointmentsForMonth(String.valueOf(activityappointments_calendarView.getSelectedDate().getMonth()));
                //todo doesn't update
                //.getAppointments();
                return appointmentList;
            }

            @Override
            protected void onPostExecute(List<AppointmentEntity> appointments) {
                super.onPostExecute(appointments);
                for (AppointmentEntity appointment : appointments) {
                    myprint(appointment.getPatientID_ref());
                }
                appointmentsRecyclerViewAdapter = new AppointmentsRecyclerViewAdapter(context, appointments);
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
                appointmentsRecyclerViewAdapter.setEditAppointmentClickListener(new AppointmentsRecyclerViewAdapter.EditAppointmentClickListener() {
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

    public void getAppointmentsOfTheDay() {
        class GetAppointmentsOfTheDay extends AsyncTask<Void, Void, List<SimpleAppointment>>
        {
            @Override
            protected List<SimpleAppointment> doInBackground(Void... voids) {
                int req_day = activityappointments_calendarView.getSelectedDate().getDay();
                int req_month = activityappointments_calendarView.getSelectedDate().getMonth();
                int req_year = activityappointments_calendarView.getSelectedDate().getYear();

                List<PatientWithAppointments> appointmentList = DatabaseRepository
                        .getInstance(context)
                        .getAppDatabase()
                        .patientWithAppointmentsDao()
                        .getAllAppointmentsOfTheDay(String.valueOf(req_year), String.valueOf(req_month), String.valueOf(req_day));

                ArrayList<SimpleAppointment> arrayList_appointments = new ArrayList<>();
                for (PatientWithAppointments patientWithAppointments: appointmentList) {
                    for (AppointmentEntity appointmentEntity : patientWithAppointments.appointments) {
                        myprint(appointmentEntity.getAppointmentTime());
                        String req_time = Utils.convertDateFormat(appointmentEntity.getAppointmentTime(), TIMEFORMAT);

                        arrayList_appointments.add(new SimpleAppointment(req_year, req_month, req_day, req_time, patientWithAppointments.patientEntity.getNameSurnameBday(getString(R.string.of_the)),
                        appointmentEntity.getVisitReason(), appointmentEntity.getAppointmentID() , patientWithAppointments.patientEntity.getPatiendID()));
                            //todo time is missing
                    }
                }

                //todo doesn't update
                //.getAppointments();
                return arrayList_appointments;
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
                    public void onItemClick(String name) {
                        //todo edit appointment
                    }
                });

                appointmentsRecyclerViewAdapter.setDeleteAppointmentClickListener(new AppointmentsAndPatientsRecyclerViewAdapter.DeleteAppointmentClickListener() {
                    @Override
                    public void onItemClick(SimpleAppointment appointmentID_toDelete) {
                        AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(ActivityAppointments.this, R.string.warning, R.string.activityappointments_string_alertMessage_deleteappointment);
                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAppointment(appointmentID_toDelete);
                            }
                        });
                        builder.setNegativeButton(R.string.back, null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
        }

        GetAppointmentsOfTheDay getPatients = new GetAppointmentsOfTheDay();
        getPatients.execute();
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
                }
            }
        }

        DeleteAppointment getDelete = new DeleteAppointment();
        getDelete.execute();
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
                        .getAppointmentsForMonth(String.valueOf(req_year), String.valueOf(req_month));

                //todo doesn't update
                //.getAppointments();
                return appointmentList;
            }

            @Override
            protected void onPostExecute(List<AppointmentEntity> appointments) {
                super.onPostExecute(appointments);

                for(AppointmentEntity appointmentEntity : appointments) {
                    //todo add decorator only if not present
                    Date appointmentDate = appointmentEntity.getAppointmentTime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(appointmentDate);

                    activityappointments_calendarView.addDecorator(new EventDecoratorMonth(CalendarDay.from(
                            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))));
                }
            }
        }

        GetAppointmentsForCalendar getPatients = new GetAppointmentsForCalendar();
        getPatients.execute();
    }

    public class EventDecoratorMonth implements DayViewDecorator {
        private CalendarDay date = null;

        //todo bug when changing page
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
}