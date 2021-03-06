package com.cafape.nutriplan.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cafape.nutriplan.ActivityPatientAccount;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

import org.apache.commons.lang3.StringUtils;

import static com.cafape.nutriplan.support.Globals.COMMA;
import static com.cafape.nutriplan.support.Globals.KILOGRAM;
import static com.cafape.nutriplan.support.Globals.PERCENTAGE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_Notes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_Notes extends Fragment
{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private String yesfood = "";
    private String nofood = "";

    private Context context;

    private LinearLayout activitypatientaccount_linearLayout_foodsuggestions;

    public Fragment_PatientAccount_Notes() {
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
    public static Fragment_PatientAccount_Notes newInstance(String param1, String param2) {
        Fragment_PatientAccount_Notes fragment = new Fragment_PatientAccount_Notes();
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
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_patientaccount_notes, container, false);
        setUiComponents(layout);
        setListeners();
        return layout;
    }

    public void setUiComponents(View layout) {
        TextView activitypatientaccount_textView_visitreason = layout.findViewById(R.id.activitypatientaccount_textView_visitreason);
        TextView activitypatientaccount_textView_previouspathologies = layout.findViewById(R.id.activitypatientaccount_textView_previouspathologies);
        TextView activitypatientaccount_textView_hereditarypathologies = layout.findViewById(R.id.activitypatientaccount_textView_hereditarypathologies);
        TextView activitypatientaccount_textView_allergies = layout.findViewById(R.id.activitypatientaccount_textView_allergies);
        TextView activitypatientaccount_textView_productsassumption = layout.findViewById(R.id.activitypatientaccount_textView_productsassumption);
        TextView activitypatientaccount_textView_intestine = layout.findViewById(R.id.activitypatientaccount_textView_intestine);
        TextView activitypatientaccount_textView_menstrualcycle = layout.findViewById(R.id.activitypatientaccount_textView_menstrualcycle);
        TextView activitypatientaccount_textView_physicalactivity = layout.findViewById(R.id.activitypatientaccount_textView_physicalactivity);
        TextView activitypatientaccount_textView_workingactivity = layout.findViewById(R.id.activitypatientaccount_textView_workingactivity);
        TextView activitypatientaccount_textView_welcomefood = layout.findViewById(R.id.activitypatientaccount_textView_welcomefood);
        TextView activitypatientaccount_textView_nonwelcomefood = layout.findViewById(R.id.activitypatientaccount_textView_nonwelcomefood);

        activitypatientaccount_linearLayout_foodsuggestions = layout.findViewById(R.id.activitypatientaccount_linearLayout_foodsuggestions);
        TextView activitypatientaccount_textView_yesfood = layout.findViewById(R.id.activitypatientaccount_textView_yesfood);
        TextView activitypatientaccount_textView_nofood = layout.findViewById(R.id.activitypatientaccount_textView_nofood);

        PatientEntity patientEntity = ((ActivityPatientAccount)getActivity()).getPatient();

        activitypatientaccount_textView_visitreason.setText(patientEntity.getVisitReason());
        String pathologies = patientEntity.getPreviousPathologies_details();
        yesfood = "";
        nofood = "";
        if(!pathologies.isEmpty()) {
            pathologies = pathologies + ". ";
        }
        if(patientEntity.isPathologiesHasHypercholesterolaemia()) {
            pathologies += context.getString(R.string.hypercholesterolaemia) + ", ";
            yesfood = yesfood + getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_hypercholesterolaemia_food_n) + COMMA;
        }
        if(patientEntity.isPathologiesHasThyroidism()) {
            pathologies += context.getString(R.string.thyroidism) + ", ";
        }
        if(patientEntity.isPathologiesHasDiabetes()) {
            pathologies += context.getString(R.string.diabetes) + ", ";
            nofood = nofood + getString(R.string.activityaddpatient_string_thyroidism_food_n) + COMMA;
        }
        if(patientEntity.isPathologiesHasKidneysLaziness()) {
            pathologies += context.getString(R.string.kidneyslaziness) + ", ";
            nofood = nofood + getString(R.string.activityaddpatient_string_kidneyslaziness_food_n) + COMMA;
        }
        if(patientEntity.isPathologiesHasOsteoporosis()) {
            pathologies += context.getString(R.string.osteoporosis) + ", ";
            yesfood = yesfood + getString(R.string.activityaddpatient_string_osteoporosis_food_y) + COMMA;
        }
        if(patientEntity.isPathologiesHasProstatitis()) {
            pathologies += context.getString(R.string.prostatitis) + ", ";
            yesfood = yesfood + getString(R.string.activityaddpatient_string_prostatitis_food_y) + COMMA;
            nofood = nofood + getString(R.string.activityaddpatient_string_prostatitis_food_n) + COMMA;
        }
        if(pathologies.length() >= 2) {
            pathologies = pathologies.substring(0, pathologies.length() - 2);
        }
        activitypatientaccount_textView_yesfood.setText(StringUtils.stripEnd(StringUtils.strip(yesfood.replace(", ,", ",")), COMMA));
        activitypatientaccount_textView_nofood.setText(StringUtils.stripEnd(StringUtils.strip(nofood.replace(", ,", ",")), COMMA));


        activitypatientaccount_textView_previouspathologies.setText(pathologies);
        activitypatientaccount_textView_hereditarypathologies.setText(patientEntity.getHereditaryPathologies_details());
        activitypatientaccount_textView_allergies.setText(patientEntity.getAllergies_details());
        activitypatientaccount_textView_productsassumption.setText(patientEntity.getProductsAssumption_details());
        activitypatientaccount_textView_intestine.setText(transformIntInString("intestine", patientEntity.getInstestine(), null));
        activitypatientaccount_textView_menstrualcycle.setText(transformIntInString("menstrualcycle", patientEntity.getMenstrualCycle(), patientEntity.getSex()));
        activitypatientaccount_textView_physicalactivity.setText(patientEntity.getPhysicalActivity_details());
        activitypatientaccount_textView_workingactivity.setText(patientEntity.getWorkinglActivity_details());
        PatientAnamnesisEntity patientAnamnesisEntity = ((ActivityPatientAccount)getActivity()).getPatientAnamnesisEntity();
        if(patientAnamnesisEntity != null) {
            activitypatientaccount_textView_welcomefood.setText(patientAnamnesisEntity.getWelcomefood());
            activitypatientaccount_textView_nonwelcomefood.setText(patientAnamnesisEntity.getNonwelcomefood());
        }

        PatientAntropometryEntity patientAntropometryEntity = ((ActivityPatientAccount)getActivity()).getPatientAntropometryEntities().get(0);
        if(patientAntropometryEntity != null) {
            TextView activitypatientaccount_textView_pi = layout.findViewById(R.id.activitypatientaccount_textView_pi);
            TextView activitypatientaccount_textView_bai = layout.findViewById(R.id.activitypatientaccount_textView_bai);
            TextView activitypatientaccount_textView_bmi = layout.findViewById(R.id.activitypatientaccount_textView_bmi);

            activitypatientaccount_textView_pi.setText(patientAntropometryEntity.getPi() + KILOGRAM);
            activitypatientaccount_textView_bai.setText(patientAntropometryEntity.getBai() + PERCENTAGE);
            activitypatientaccount_textView_bmi.setText(patientAntropometryEntity.getBmi() + PERCENTAGE);
        }

        LinearLayout activitypatientaccount_linearLayout_bai = layout.findViewById(R.id.activitypatientaccount_linearLayout_bai);
        LinearLayout activitypatientaccount_linearLayout_bmi = layout.findViewById(R.id.activitypatientaccount_linearLayout_bmi);

        activitypatientaccount_linearLayout_bai.setOnClickListener(view -> {
            AlertDialog.Builder alert_bai = new AlertDialog.Builder(getActivity());
            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View view_bai = factory.inflate(R.layout.table_bai, null);
            alert_bai.setView(view_bai);
            alert_bai.show();
        });

        activitypatientaccount_linearLayout_bmi.setOnClickListener(view -> {
            AlertDialog.Builder alert_bmi = new AlertDialog.Builder(getActivity());
            LayoutInflater factory = LayoutInflater.from(getActivity());
            final View view_bmi = factory.inflate(R.layout.table_bmi, null);
            alert_bmi.setView(view_bmi);
            alert_bmi.show();
        });

        Button activitypatientaccount_button_edit = layout.findViewById(R.id.activitypatientaccount_button_edit);
        activitypatientaccount_button_edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ((ActivityPatientAccount)getActivity()).openEditPatientNotes();
            }
        });
    }

    public void setListeners() {
       /* activityaddpatient_button_backto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1);
            }
        });

        */
    }

    public void checkUncheckFoodTextBox(boolean activation) {
        if(activation) {
            if(nofood.isEmpty() || yesfood.isEmpty()){
                if(activitypatientaccount_linearLayout_foodsuggestions.getVisibility() != View.VISIBLE) {
                    activitypatientaccount_linearLayout_foodsuggestions.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if(activitypatientaccount_linearLayout_foodsuggestions.getVisibility() == View.VISIBLE) {
                if(nofood.isEmpty() && yesfood.isEmpty()) {
                    activitypatientaccount_linearLayout_foodsuggestions.setVisibility(View.GONE);
                }
            }
        }
    }

    public String transformIntInString(String type, int value, String sex) {
        switch (type) {
            case "intestine": {
                switch (value) {
                    case 1: {return getString(R.string.activityaddpatient_string_intestine_diarrheal);}
                    case 2: {return getString(R.string.activityaddpatient_string_menstrualcycle_irregular);}
                    case 3: {return getString(R.string.activityaddpatient_string_intestine_alternate);}
                    case 4: {return getString(R.string.activityaddpatient_string_intestine_regular);}
                }

            } break;
            case "menstrualcycle": {
                if(sex.equalsIgnoreCase("F"))
                switch (value) {
                    case 1: {return getString(R.string.activityaddpatient_string_menstrualcycle_regular);}
                    case 2: {return getString(R.string.activityaddpatient_string_menstrualcycle_irregular);}
                    case 3: {return getString(R.string.activityaddpatient_string_menstrualcycle_absent);}
                }
            } break;
        }
        return "";
    }

    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }
}