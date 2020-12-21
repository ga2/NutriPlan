package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.objects.SimpleAppointment;
import com.cafape.nutriplan.support.Utils;

import org.w3c.dom.Text;

public class ActivityVisitShow extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_show);

        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("args");
        if((args != null) && (args.containsKey("visitObject"))) {
            if(args.containsKey("name_surname")) {
                PatientAntropometryEntity patientAntropometryEntity = (PatientAntropometryEntity) args.getSerializable("visitObject");
                String name_surname = args.getString("name_surname");
                setUiComponents(patientAntropometryEntity, name_surname);
            }
        }
    }
    
    public void setUiComponents(PatientAntropometryEntity patientAntropometryEntity, String name_surname) {
        TextView activitypatientaccount_appBarLayout_textView_namesurname = findViewById(R.id.activitypatientaccount_appBarLayout_textView_namesurname);
        TextView activityvisitshow_textView_weight = findViewById(R.id.activityvisitshow_textView_weight);
        TextView activityvisitshow_textView_height = findViewById(R.id.activityvisitshow_textView_height);
        TextView activityvisitshow_textView_weist = findViewById(R.id.activityvisitshow_textView_weist);
        TextView activityvisitshow_textView_belly = findViewById(R.id.activityvisitshow_textView_belly);
        TextView activityvisitshow_textView_hips = findViewById(R.id.activityvisitshow_textView_hips);
        TextView activityvisitshow_textView_leg = findViewById(R.id.activityvisitshow_textView_leg);
        TextView activityvisitshow_textView_arm = findViewById(R.id.activityvisitshow_textView_arm);
        TextView activityvisitshow_textView_bicipital = findViewById(R.id.activityvisitshow_textView_bicipital);
        TextView activityvisitshow_textView_pectoral = findViewById(R.id.activityvisitshow_textView_pectoral);
        TextView activityvisitshow_textView_subscapularis = findViewById(R.id.activityvisitshow_textView_subscapularis);
        TextView activityvisitshow_textView_umbilicale = findViewById(R.id.activityvisitshow_textView_umbilicale);
        TextView activityvisitshow_textView_femoral = findViewById(R.id.activityvisitshow_textView_femoral);
        TextView activityvisitshow_textView_pi = findViewById(R.id.activityvisitshow_textView_pi);
        TextView activityvisitshow_textView_bai = findViewById(R.id.activityvisitshow_textView_bai);
        TextView activityvisitshow_textView_bmi = findViewById(R.id.activityvisitshow_textView_bmi);

        activitypatientaccount_appBarLayout_textView_namesurname.setText(name_surname + " " + Globals.LONG_DASH + " " + getString(R.string.activitypatientaccount_string_visit_ofthedate) + " " + Utils.convertDateFormat(patientAntropometryEntity.getAntropometryTime(), Globals.DATETIMEFORMAT_DISPLAY));
        activityvisitshow_textView_weight.setText(parseToString(patientAntropometryEntity.getWeight(), Globals.KILOGRAM));
        activityvisitshow_textView_height.setText(parseToString(patientAntropometryEntity.getHeight(), Globals.CENTIMETRES));
        activityvisitshow_textView_weist.setText(parseToString(patientAntropometryEntity.getWeist(), Globals.CENTIMETRES));
        activityvisitshow_textView_belly.setText(parseToString(patientAntropometryEntity.getBelly(), Globals.CENTIMETRES));
        activityvisitshow_textView_hips.setText(parseToString(patientAntropometryEntity.getHips(), Globals.CENTIMETRES));
        activityvisitshow_textView_leg.setText(parseToString(patientAntropometryEntity.getLeg(), Globals.CENTIMETRES));
        activityvisitshow_textView_arm.setText(parseToString(patientAntropometryEntity.getArm(), Globals.CENTIMETRES));
        activityvisitshow_textView_bicipital.setText(parseToString(patientAntropometryEntity.getBicipital(), Globals.CENTIMETRES));
        activityvisitshow_textView_pectoral.setText(parseToString(patientAntropometryEntity.getPectoral(), Globals.CENTIMETRES));
        activityvisitshow_textView_subscapularis.setText(parseToString(patientAntropometryEntity.getSubscapularis(), Globals.CENTIMETRES));
        activityvisitshow_textView_umbilicale.setText(parseToString(patientAntropometryEntity.getUmbelicale(), Globals.CENTIMETRES));
        activityvisitshow_textView_femoral.setText(parseToString(patientAntropometryEntity.getFemoral(), Globals.CENTIMETRES));
        activityvisitshow_textView_pi.setText(parseToString(patientAntropometryEntity.getPi(), Globals.PERCENTAGE));
        activityvisitshow_textView_bai.setText(parseToString(patientAntropometryEntity.getBai(), Globals.PERCENTAGE));
        activityvisitshow_textView_bmi.setText(parseToString(patientAntropometryEntity.getBmi(), Globals.PERCENTAGE));
    }

    public String parseToString(float number, String unit) {
        if(number > 0) {
            return String.valueOf(number) + " " + unit;
        } else {
            return "";
        }
    }
}