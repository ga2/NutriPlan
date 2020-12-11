package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityMenu extends AppCompatActivity
{
    private Context context;
    private ConstraintLayout activitymanu_constraintLayout_card_calc;
    private ConstraintLayout activitymanu_constraintLayout_card_patient;
    private ConstraintLayout activitymanu_constraintLayout_card_appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        context = getApplicationContext();

        setUiComponents();
        setListeners();
    }

    public void setUiComponents() {
        activitymanu_constraintLayout_card_calc = findViewById(R.id.activitymanu_constraintLayout_card_calc);
        activitymanu_constraintLayout_card_patient = findViewById(R.id.activitymanu_constraintLayout_card_patient);
        activitymanu_constraintLayout_card_appointment = findViewById(R.id.activitymanu_constraintLayout_card_appointment);
    }

    public void setListeners() {
        activitymanu_constraintLayout_card_calc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityCalc.class);
                startActivity(intent_goToActivity);
            }
        });

        activitymanu_constraintLayout_card_patient.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityPatients.class);
                startActivity(intent_goToActivity);
            }
        });

        activitymanu_constraintLayout_card_appointment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityAppointments.class);
                startActivity(intent_goToActivity);
            }
        });
    }
}