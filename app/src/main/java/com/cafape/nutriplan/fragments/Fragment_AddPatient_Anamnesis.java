package com.cafape.nutriplan.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cafape.nutriplan.ActivityAddPatient2;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

import org.apache.commons.lang3.StringUtils;

import static com.cafape.nutriplan.support.Globals.COMMA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_AddPatient_Anamnesis#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_AddPatient_Anamnesis extends Fragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button activityaddpatient_button_backto1;
    private Button activityaddpatient_button_goto3;

    private LinearLayout activityaddpatient_linearLayout_foodsuggestions;
    private TextView activityaddpatient_textView_yesfood;
    private TextView activityaddpatient_textView_nofood;

    private String nofood = "";
    private String yesfood = "";

    public Fragment_AddPatient_Anamnesis() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_AddPatient_Anamnesis.
     */
    public static Fragment_AddPatient_Anamnesis newInstance(String param1, String param2) {
        Fragment_AddPatient_Anamnesis fragment = new Fragment_AddPatient_Anamnesis();
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
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_addpatient_anamnesis, container, false);
        setUiComponents(layout);
        setListeners(layout);
        return layout;
    }

    @Override
    public void onResume(){
        super.onResume();
        nofood = "";
        yesfood = "";
        showFoodPreferences();
    }

    public void setUiComponents(View layout) {
        activityaddpatient_linearLayout_foodsuggestions = layout.findViewById(R.id.activityaddpatient_linearLayout_foodsuggestions);
        activityaddpatient_textView_yesfood = layout.findViewById(R.id.activityaddpatient_textView_yesfood);
        activityaddpatient_textView_nofood = layout.findViewById(R.id.activityaddpatient_textView_nofood);
        activityaddpatient_button_backto1 = layout.findViewById(R.id.activityaddpatient_button_backto1);
        activityaddpatient_button_goto3 = layout.findViewById(R.id.activityaddpatient_button_goto3);
    }

    public void setListeners(View layout) {

        activityaddpatient_button_backto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(0);
            }
        });

        activityaddpatient_button_goto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String activityaddpatient_editText_welcomefood = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_welcomefood)); 
                String activityaddpatient_editText_nonwelcomefood = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_nonwelcomefood)); 
                String activityaddpatient_editText_anamnesis_breakfast = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_anamnesis_breakfast)); 
                String activityaddpatient_editText_anamnesis_nibble = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_anamnesis_nibble)); 
                String activityaddpatient_editText_anamnesis_lunch = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_anamnesis_lunch)); 
                String activityaddpatient_editText_anamnesis_snack = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_anamnesis_snack)); 
                String activityaddpatient_editText_anamnesis_dinner = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_anamnesis_dinner)); 
                String activityaddpatient_editText_anamnesis_postdinner = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_anamnesis_postdinner)); 
                String activityaddpatient_editText_prototype_breakfast = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_prototype_breakfast)); 
                String activityaddpatient_editText_prototype_nibble = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_prototype_nibble)); 
                String activityaddpatient_editText_prototype_lunch = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_prototype_lunch)); 
                String activityaddpatient_editText_prototype_snack = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_prototype_snack)); 
                String activityaddpatient_editText_prototype_dinner = getStringFromEditText(layout.findViewById(R.id.activityaddpatient_editText_prototype_dinner)); 

                PatientAnamnesisEntity patientAnamnesisEntity = new PatientAnamnesisEntity(0, activityaddpatient_editText_welcomefood, activityaddpatient_editText_nonwelcomefood,
                        activityaddpatient_editText_anamnesis_breakfast, activityaddpatient_editText_anamnesis_nibble, activityaddpatient_editText_anamnesis_lunch,
                        activityaddpatient_editText_anamnesis_snack, activityaddpatient_editText_anamnesis_dinner, activityaddpatient_editText_anamnesis_postdinner,
                        activityaddpatient_editText_prototype_breakfast, activityaddpatient_editText_prototype_nibble, activityaddpatient_editText_prototype_lunch,
                        activityaddpatient_editText_prototype_snack, activityaddpatient_editText_prototype_dinner);

                ((ActivityAddPatient2)getActivity()).setPatientAnamnesisEntity(patientAnamnesisEntity);
                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(2);
            }
        });
    }

    public void showFoodPreferences() {
        boolean hasOne = false;
        PatientEntity patientEntity = ((ActivityAddPatient2)getActivity()).getPatientEntity();
        if(patientEntity.isPathologiesHasHypercholesterolaemia()) {
            hasOne = true;
            yesfood = yesfood + getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_n) + COMMA;
        } else {
            yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_y), "");
            nofood = nofood.replace(getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_n), "");
        }

        if(patientEntity.isPathologiesHasThyroidism()) {
            hasOne = true;
            //yesfood = yesfood + getString(R.string.activityaddpatient_string_thyroidism_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_thyroidism_food_n) + COMMA;
        } else {
            //yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_thyroidism_food_y), "");
            nofood = nofood.replace(getString(R.string.activityaddpatient_string_thyroidism_food_n), "");
        }
/*
        if(patientEntity.isPathologiesHasDiabetes()) {
            hasOne = true;
            yesfood = yesfood + getString(R.string.activityaddpatient_string_diabetes_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_diabetes_food_n) + COMMA;
        } else {
            yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_diabetes_food_y), "");
            nofood = nofood.replace(getString(R.string.activityaddpatient_string_diabetes_food_n), "");
        }*/

        if(patientEntity.isPathologiesHasKidneysLaziness()) {
            hasOne = true;
            //yesfood = yesfood + getString(R.string.activityaddpatient_string_kidneyslaziness_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_kidneyslaziness_food_n) + COMMA;
        } else {
            //yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_kidneyslaziness_food_y), "");
            nofood = nofood.replace(getString(R.string.activityaddpatient_string_kidneyslaziness_food_n), "");
        }

        if(patientEntity.isPathologiesHasOsteoporosis()) {
            hasOne = true;
            yesfood = yesfood + getString(R.string.activityaddpatient_string_osteoporosis_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_osteoporosis_food_n) + COMMA;
        } else {
            yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_osteoporosis_food_y), "");
            //nofood = nofood.replace(getString(R.string.activityaddpatient_string_osteoporosis_food_n), "");
        }

        if(patientEntity.isPathologiesHasProstatitis()) {
            hasOne = true;
            yesfood = yesfood + getString(R.string.activityaddpatient_string_prostatitis_food_y);
            nofood = nofood + getString(R.string.activityaddpatient_string_prostatitis_food_n);
        } else {
            yesfood = yesfood.replace(getString(R.string.activityaddpatient_string_prostatitis_food_y), "");
            nofood = nofood.replace(getString(R.string.activityaddpatient_string_prostatitis_food_n), "");
        }

        activityaddpatient_textView_yesfood.setText(StringUtils.stripEnd(StringUtils.strip(yesfood.replace(", ,", "")), COMMA));
        activityaddpatient_textView_nofood.setText(StringUtils.stripEnd(StringUtils.strip(nofood.replace(", ,", "")), COMMA));
        checkUncheckFoodTextBox(hasOne);
    }

    public void checkUncheckFoodTextBox(boolean activation) {
        if(activation) {
            if(!nofood.isEmpty() || !yesfood.isEmpty()) {
                if(activityaddpatient_linearLayout_foodsuggestions.getVisibility() != View.VISIBLE){
                    activityaddpatient_linearLayout_foodsuggestions.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if(activityaddpatient_linearLayout_foodsuggestions.getVisibility() == View.VISIBLE) {
                if(nofood.isEmpty() && yesfood.isEmpty()) {
                    activityaddpatient_linearLayout_foodsuggestions.setVisibility(View.GONE);
                }
            }
        }
    }

    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

}