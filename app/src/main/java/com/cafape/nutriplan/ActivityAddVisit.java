package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.database.DatabaseRepository;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.Globals;

import java.text.DecimalFormat;

import static com.cafape.nutriplan.support.Utils.getStringFromEditText;
import static com.cafape.nutriplan.support.Utils.parseStringToFloat;

public class ActivityAddVisit extends AppCompatActivity
{
    private PatientEntity patientEntity;
    private PatientAntropometryEntity patientAntropometryEntity;

    private EditText activityaddvisit_editText_weight;
    private EditText activityaddvisit_editText_height;
    private EditText activityaddvisit_editText_hips;

    private EditText activityaddvisit_editText_pi;
    private EditText activityaddvisit_editText_bmi;
    private EditText activityaddvisit_editText_bai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        initPatient();
        setUiComponents();
        setListeners();
    }

    public void initPatient() {
        Intent intent = this.getIntent();
        Bundle args = intent.getBundleExtra("args");
        if((args != null) && args.containsKey("patientObject")) {
            patientEntity = (PatientEntity) args.getSerializable("patientObject");
            TextView activitypatientaccount_appBarLayout_textView_namesurname = findViewById(R.id.activitypatientaccount_appBarLayout_textView_namesurname);
            activitypatientaccount_appBarLayout_textView_namesurname.setText(patientEntity.getName() + " " + patientEntity.getSurname() + " " + Globals.LONG_DASH + " " + getString(R.string.activitypatientaccount_string_visit_add));
        } else {
            finish();
        }
    }

    public void setUiComponents() {

        activityaddvisit_editText_weight = findViewById(R.id.activityaddvisit_editText_weight);
        activityaddvisit_editText_height = findViewById(R.id.activityaddvisit_editText_height);
        activityaddvisit_editText_hips = findViewById(R.id.activityaddvisit_editText_hips);

        activityaddvisit_editText_pi = findViewById(R.id.activityaddvisit_editText_pi);
        activityaddvisit_editText_bai = findViewById(R.id.activityaddvisit_editText_bai);
        activityaddvisit_editText_bmi = findViewById(R.id.activityaddvisit_editText_bmi);
        
        Button activityaddvisit_button_save = findViewById(R.id.activityaddvisit_button_save);
        activityaddvisit_button_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                populateVisit();
                saveVisitEntity();
            }
        });
    }
    
    public void setListeners() {

        //peso, altezza, fianchi
        activityaddvisit_editText_weight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateIndexes();
            }
        });

        activityaddvisit_editText_height.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateIndexes();
            }
        });

        activityaddvisit_editText_hips.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculateIndexes();
            }
        });
    }

    public void calculateIndexes() {
        String hips = getStringFromEditText(activityaddvisit_editText_hips);
        String height = getStringFromEditText(activityaddvisit_editText_height);
        String weight = getStringFromEditText(activityaddvisit_editText_weight);

        if(!height.isEmpty() && !weight.isEmpty()) {
            String bodyMassIndex = calculateBMI(height, weight);
            activityaddvisit_editText_bmi.setText(bodyMassIndex);
        }

        if(!height.isEmpty() && !hips.isEmpty()) {
            String bodyAdiposityIndex = calculateBAI(height, hips);
            activityaddvisit_editText_bai.setText(bodyAdiposityIndex);
        }

        if(!height.isEmpty()) {
            String idealWeight = calculatePI(height);
            activityaddvisit_editText_pi.setText(idealWeight);
        }
    }

    public void populateVisit() {
        
        float activityaddvisit_weight = parseStringToFloat(activityaddvisit_editText_weight);
        float activityaddvisit_height = parseStringToFloat(activityaddvisit_editText_height);
        float activityaddvisit_hips = parseStringToFloat(activityaddvisit_editText_hips);
        float activityaddvisit_editText_belly = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_belly));
        float activityaddvisit_editText_weist = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_weist));
        float activityaddvisit_editText_leg = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_leg));
        float activityaddvisit_editText_arm = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_arm));
        float activityaddvisit_editText_bicipital = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_bicipital));
        float activityaddvisit_editText_pectoral = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_pectoral));
        float activityaddvisit_editText_subscapularis = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_subscapularis));
        float activityaddvisit_editText_umbilicale = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_umbilicale));
        float activityaddvisit_editText_femoral = parseStringToFloat(findViewById(R.id.activityaddvisit_editText_femoral));
        //String activityaddvisit_editText_note = Utils.getStringFromEditText(findViewById(R.id.activityaddvisit_editText_notes));
        String activityaddvisit_editText_note = "";


        float activityaddvisit_pi = parseStringToFloat(activityaddvisit_editText_pi);
        float activityaddvisit_bai = parseStringToFloat(activityaddvisit_editText_bai);
        float activityaddvisit_bmi = parseStringToFloat(activityaddvisit_editText_bmi);
        
        patientAntropometryEntity = new PatientAntropometryEntity(patientEntity.getPatiendID(), activityaddvisit_weight, activityaddvisit_height,
                activityaddvisit_editText_weist, activityaddvisit_editText_belly, activityaddvisit_hips, activityaddvisit_editText_leg,
                activityaddvisit_editText_arm, activityaddvisit_editText_bicipital, activityaddvisit_editText_pectoral, activityaddvisit_editText_subscapularis,
                activityaddvisit_editText_umbilicale, activityaddvisit_editText_femoral, activityaddvisit_pi, activityaddvisit_bai, activityaddvisit_bmi, activityaddvisit_editText_note);
    }

    public String parseToString(float number, String unit) {
        if(number > 0) {
            return String.valueOf(number) + " " + unit;
        } else {
            return "";
        }
    }

    public void saveVisitEntity() {
        class SaveVisit extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseRepository.getInstance(getApplicationContext()).getAppDatabase().patientAntropometryDao().insert(patientAntropometryEntity);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent data = new Intent();
                data.putExtra("newVisitEntity", patientAntropometryEntity);
                setResult(RESULT_OK, data);
                finish();
                Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_LONG).show();
            }
        }

        new SaveVisit().execute();
    }

    public String calculatePI(String height) {
        String res = "";
        if(patientEntity != null) {
            String sex = patientEntity.getSex();

            float height_float = Float.parseFloat(height);
            float divisor = 2;
            //LORENZ FORMULA

            if (sex.equals(getString(R.string.male_short))) {
                divisor = 4;
            }

            float result = height_float - 100 - (height_float - 150) / divisor;
            if (result > 0 && result < 1000) {
                return String.valueOf(result);
            }
        }
        return res;
    }

    public static String calculateBAI(String height, String hipscirc) {
        float height_float = Float.parseFloat(height);
        float hipscirc_float = Float.parseFloat(hipscirc);

        float result = (hipscirc_float / (float)Math.pow(height_float / 100, 1.5f)) - 18;

        if(result > 0 && result < 100) {
            DecimalFormat df = new DecimalFormat("###.##");
            return df.format(result);} else {
            return "0";
        }
    }

    public static String calculateBMI(String height, String weight) {
        float height_float = Float.parseFloat(height);
        float weight_float = Float.parseFloat(weight);

        float result = (1.3f * weight_float) / (float)Math.pow(height_float / 100, 2.5f);

        if(result > 0 && result < 100) {
            DecimalFormat df = new DecimalFormat("###.##");
            return df.format(result);
        } else {
            return "0";
        }
    }
}