package com.cafape.nutriplan.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cafape.nutriplan.ActivityAddPatient2;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_AddPatient_Antropometry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_AddPatient_Antropometry extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button activityaddpatient_button_backto2;
    private Button activityaddpatient_button_save;

    private EditText activityaddpatient_editText_weight;
    private EditText activityaddpatient_editText_height;
    private EditText activityaddpatient_editText_hips;

    private EditText activityaddpatient_editText_pi;
    private EditText activityaddpatient_editText_bmi;
    private EditText activityaddpatient_editText_bai;

    public Fragment_AddPatient_Antropometry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_AddPatient_Antropometry.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_AddPatient_Antropometry newInstance(String param1, String param2) {
        Fragment_AddPatient_Antropometry fragment = new Fragment_AddPatient_Antropometry();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_addpatient_antropometry, container, false);
        setUiComponents(layout);
        setListeners(layout);
        return layout;
    }

    public void calculateIndexes() {
        String hips = getStringFromEditText(activityaddpatient_editText_hips);
        String height = getStringFromEditText(activityaddpatient_editText_height);
        String weight = getStringFromEditText(activityaddpatient_editText_weight);

        if(!height.isEmpty() && !weight.isEmpty()) {
            String bodyMassIndex =  ((ActivityAddPatient2) getActivity()).calculateBMI(height, weight);
            activityaddpatient_editText_bmi.setText(bodyMassIndex);
        }

        if(!height.isEmpty() && !hips.isEmpty()) {
            String bodyAdiposityIndex =  ((ActivityAddPatient2) getActivity()).calculateBAI(height, hips);
            activityaddpatient_editText_bai.setText(bodyAdiposityIndex);
        }

        if(!height.isEmpty()) {
            String idealWeight = ((ActivityAddPatient2) getActivity()).calculatePI(height);
            activityaddpatient_editText_pi.setText(idealWeight);
        }
    }

    public void setUiComponents(View layout) {
        activityaddpatient_button_backto2 = layout.findViewById(R.id.activityaddpatient_button_backto2);
        activityaddpatient_button_save = layout.findViewById(R.id.activityaddpatient_button_save);

        activityaddpatient_editText_weight = layout.findViewById(R.id.activityaddpatient_editText_weight);
        activityaddpatient_editText_height = layout.findViewById(R.id.activityaddpatient_editText_height);
        activityaddpatient_editText_hips = layout.findViewById(R.id.activityaddpatient_editText_hips);

        activityaddpatient_editText_pi = layout.findViewById(R.id.activityaddpatient_editText_pi);
        activityaddpatient_editText_bai = layout.findViewById(R.id.activityaddpatient_editText_bai);
        activityaddpatient_editText_bmi = layout.findViewById(R.id.activityaddpatient_editText_bmi);
    }

    public void setListeners(View layout) {
        activityaddpatient_button_backto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1);
            }
        });

        //peso, altezza, fianchi
        activityaddpatient_editText_weight.addTextChangedListener(new TextWatcher()
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

        activityaddpatient_editText_height.addTextChangedListener(new TextWatcher()
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

        activityaddpatient_editText_hips.addTextChangedListener(new TextWatcher()
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

        activityaddpatient_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float activityaddpatient_weight = parseStringToFloat(activityaddpatient_editText_weight);
                float activityaddpatient_height = parseStringToFloat(activityaddpatient_editText_height);
                float activityaddpatient_hips = parseStringToFloat(activityaddpatient_editText_hips);
                float activityaddpatient_editText_belly = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_belly));
                float activityaddpatient_editText_weist = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_weist));
                float activityaddpatient_editText_leg = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_leg));
                float activityaddpatient_editText_arm = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_arm));
                float activityaddpatient_editText_bicipital = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_bicipital));
                float activityaddpatient_editText_pectoral = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_pectoral));
                float activityaddpatient_editText_subscapularis = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_subscapularis));
                float activityaddpatient_editText_umbilicale = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_umbilicale));
                float activityaddpatient_editText_femoral = parseStringToFloat(layout.findViewById(R.id.activityaddpatient_editText_femoral));


                float activityaddpatient_pi = parseStringToFloat(activityaddpatient_editText_pi);
                float activityaddpatient_bai = parseStringToFloat(activityaddpatient_editText_bai);
                float activityaddpatient_bmi = parseStringToFloat(activityaddpatient_editText_bmi);

                PatientAntropometryEntity patientAntropometryEntity = new PatientAntropometryEntity(0, activityaddpatient_weight, activityaddpatient_height
                , activityaddpatient_editText_weist, activityaddpatient_editText_belly, activityaddpatient_hips, activityaddpatient_editText_leg,
                        activityaddpatient_editText_arm, activityaddpatient_editText_bicipital, activityaddpatient_editText_pectoral,
                        activityaddpatient_editText_subscapularis,
                        activityaddpatient_editText_umbilicale, activityaddpatient_editText_femoral, activityaddpatient_pi,
                        activityaddpatient_bai, activityaddpatient_bmi, "");
                ((ActivityAddPatient2)getActivity()).setPatientAntropometryEntity(patientAntropometryEntity);
                ((ActivityAddPatient2)getActivity()).saveData();
            }
        });
    }
    
    public float parseStringToFloat(EditText editText) {
        String string = getStringFromEditText(editText);
        float res = 0.0f;
        if(!string.isEmpty()) {
            res = Float.parseFloat(string);    
        }
        return res;
    }

    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }
}