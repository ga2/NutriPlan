package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

public class MainActivity extends AppCompatActivity
{
    private Context context;
    private Button activityMain_button_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        setUiComponents();
        setListeners();
    }

    public void setUiComponents() {
        activityMain_button_login = (Button) findViewById(R.id.mainactivity_button_login);
    }

    public void setListeners() {
        activityMain_button_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent_goToActivity = new Intent(context, ActivityMenu.class);
                startActivity(intent_goToActivity);
            }
        });
    }
}