package com.cafape.nutriplan.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cafape.nutriplan.ActivityAddPatient2;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_AddPatient_Anamnesis#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_AddPatient_Anamnesis extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button activityaddpatient_button_backto1;
    private Button activityaddpatient_button_goto3;

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
    // TODO: Rename and change types and number of parameters
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

    public void setUiComponents(View layout) {
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

    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

}