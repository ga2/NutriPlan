package com.cafape.nutriplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.adapters.AppointmentsRecyclerViewAdapter;
import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.support.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

import static com.cafape.nutriplan.Globals.REQCODE_NEWAPPOINTMENT_ADDED;
import static com.cafape.nutriplan.Globals.REQCODE_NEWPATIENT_ADDED;
import static com.cafape.nutriplan.support.Utils.myprint;

public class ActivityAppointments extends AppCompatActivity
{
    private Context context;

    private MaterialCalendarView activityappointments_calendarView;
    private TextView activityappointments_textView_day;
    private TextView activityappointments_textView_monthYear;
    private RecyclerView activityappointments_recyclerView;
    private AppointmentsRecyclerViewAdapter appointmentsRecyclerViewAdapter;
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
        getAppointments();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE_NEWPATIENT_ADDED) {
            if (resultCode == RESULT_OK) {
                AppointmentEntity appointmentEntity = (AppointmentEntity)data.getSerializableExtra("newAppointmentEntity");
                appointmentsRecyclerViewAdapter.addToRetrievedData(appointmentEntity);
                appointmentsRecyclerViewAdapter.notifyDataSetChanged();
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
                setDate(String.valueOf(date.getDay()), date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), String.valueOf(date.getYear()));
            }
        });

        activityappointments_calendarView.setOnMonthChangedListener(new OnMonthChangedListener()
        {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                getAppointments();
                Toast.makeText(getApplicationContext(), date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), Toast.LENGTH_SHORT).show();
            }
        });

        DayViewDecorator dayViewDecorator = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return day.equals(CalendarDay.from(2021, 1, 25));
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_brightness_8));
                view.addSpan(new ForegroundColorSpan(Color.WHITE));
            }
        };

        dayViewDecorator.shouldDecorate(CalendarDay.from(2021, 1, 25)); //1=gennaio

        activityappointments_calendarView.addDecorator(dayViewDecorator);

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
    }

    public void setDate(String day, String month, String year) {
        activityappointments_textView_day.setText(day);
        activityappointments_textView_monthYear.setText(month + " " + year);
    }

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
}