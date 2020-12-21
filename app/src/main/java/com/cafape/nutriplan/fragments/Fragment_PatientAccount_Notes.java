package com.cafape.nutriplan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cafape.nutriplan.ActivityPatientAccount;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_Notes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_Notes extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
    // TODO: Rename and change types and number of parameters
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

        PatientEntity patientEntity = ((ActivityPatientAccount)getActivity()).getPatient();
        PatientAnamnesisEntity patientAnamnesisEntity = ((ActivityPatientAccount)getActivity()).getPatientAnamnesisEntity();
        activitypatientaccount_textView_visitreason.setText(patientEntity.getVisitReason());
        activitypatientaccount_textView_previouspathologies.setText(patientEntity.getPreviousPathologies_details());
        activitypatientaccount_textView_hereditarypathologies.setText(patientEntity.getHereditaryPathologies_details());
        activitypatientaccount_textView_allergies.setText(patientEntity.getAllergies_details());
        activitypatientaccount_textView_productsassumption.setText(patientEntity.getProductsAssumption_details());
        activitypatientaccount_textView_intestine.setText(transformIntInString("intestine", patientEntity.getInstestine(), null));
        activitypatientaccount_textView_menstrualcycle.setText(transformIntInString("menstrualcycle", patientEntity.getMenstrualCycle(), patientEntity.getSex()));
        activitypatientaccount_textView_physicalactivity.setText(patientEntity.getPhysicalActivity_details());
        activitypatientaccount_textView_workingactivity.setText(patientEntity.getWorkinglActivity_details());
        activitypatientaccount_textView_welcomefood.setText(patientAnamnesisEntity.getWelcomefood());
        activitypatientaccount_textView_nonwelcomefood.setText(patientAnamnesisEntity.getNonwelcomefood());
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