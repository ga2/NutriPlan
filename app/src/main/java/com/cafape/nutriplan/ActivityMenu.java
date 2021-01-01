package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class ActivityMenu extends AppCompatActivity
{
    private Context context;
    private ConstraintLayout activitymanu_constraintLayout_card_calc;
    private ConstraintLayout activitymanu_constraintLayout_card_patient;
    private ConstraintLayout activitymanu_constraintLayout_card_appointment;
    private ConstraintLayout activitymanu_constraintLayout_card_importexport;

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
        activitymanu_constraintLayout_card_importexport = findViewById(R.id.activitymanu_constraintLayout_card_importexport);
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
        activitymanu_constraintLayout_card_importexport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_importexport = new AlertDialog.Builder(ActivityMenu.this);
                LayoutInflater factory = LayoutInflater.from(ActivityMenu.this);
                final View view_importexport = factory.inflate(R.layout.dialog_importexport, null);
                alert_importexport.setView(view_importexport);
                alert_importexport.show();
            }
        });
    }
}