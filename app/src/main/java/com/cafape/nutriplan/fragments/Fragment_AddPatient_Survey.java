package com.cafape.nutriplan.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cafape.nutriplan.ActivityAddPatient2;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.cafape.nutriplan.support.AlertBuilderUtils;
import com.cafape.nutriplan.support.Utils;
import com.cafape.nutriplan.ui.CustomDatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_AddPatient_Survey#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_AddPatient_Survey extends Fragment
{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    
    private Button activityaddpatient_button_goto2;
    private CustomDatePicker activityaddpatient_datepicker_bdate;
    private EditText activityaddpatient_editText_age;

    public Fragment_AddPatient_Survey() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_AddPatient_Survey.
     */
    public static Fragment_AddPatient_Survey newInstance(String param1, String param2) {
        Fragment_AddPatient_Survey fragment = new Fragment_AddPatient_Survey();
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
        View layout = inflater.inflate(R.layout.fragment_add_patient_survey, container, false);
        setUiComponents(layout);
        setListeners(layout);
        return layout;
    }

    public void setUiComponents(View layout) {
        activityaddpatient_button_goto2 = layout.findViewById(R.id.activityaddpatient_button_goto2);
        activityaddpatient_datepicker_bdate = layout.findViewById(R.id.activityaddpatient_datepicker_bdate);
        activityaddpatient_editText_age = layout.findViewById(R.id.activityaddpatient_editText_age);
    }

    public void setListeners(View layout) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        activityaddpatient_datepicker_bdate.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Date date = Utils.getDateFromDatePicker(datePicker);
                activityaddpatient_editText_age.setText(String.valueOf(Utils.calculateAge(date)));
            }
        });

        activityaddpatient_button_goto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientEntity_name = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_name)).getText().toString();
                String patientEntity_surname = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_surname)).getText().toString();
                Date patientEntity_bdate = Utils.getDateFromDatePicker(activityaddpatient_datepicker_bdate);
                String patientEntity_phone = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_phone)).getText().toString();
                activityaddpatient_editText_age.setText(String.valueOf(Utils.calculateAge(patientEntity_bdate)));
                
                if(!patientEntity_name.isEmpty() && !patientEntity_surname.isEmpty() && !patientEntity_phone.isEmpty()) {
                    patientEntity_name = Utils.upperCaseAllFirst(patientEntity_name);
                    patientEntity_surname = Utils.upperCaseAllFirst(patientEntity_surname);

                    String patientEntity_sex = getTagFromRadioGroup(layout, R.id.activityaddpatient_radioGroup_sex);
                    int patientEntity_intestine = Integer.valueOf(getTagFromRadioGroup(layout, R.id.activityaddpatient_radioGroup_intestine));
                    int patientEntity_menstrualCycle = Integer.valueOf(getTagFromRadioGroup(layout, R.id.activityaddpatient_radioGroup_intestine));
                    String patientEntity_visitReason = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_visitReason)).getText().toString();
                    String patientEntity_previousDiets_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_previousDiets)).getText().toString();
                    boolean patientEntity_previousDiets_status = !patientEntity_previousDiets_details.isEmpty();
                    String patientEntity_weightHistory = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_weightHistory)).getText().toString();
                    String patientEntity_previousPathologies_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_previousPathologies)).getText().toString();
                    boolean patientEntity_previousPathologies_status = !patientEntity_previousPathologies_details.isEmpty();
                    String patientEntity_hereditaryPathologies_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_hereditaryPathologies)).getText().toString();
                    boolean patientEntity_hereditaryPathologies_status = !patientEntity_hereditaryPathologies_details.isEmpty();
                    String patientEntity_allergies_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_allergies)).getText().toString();
                    boolean patientEntity_allergies_status = !patientEntity_allergies_details.isEmpty();
                    String patientEntity_productsAssumption_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_productsAssumption)).getText().toString();
                    boolean patientEntity_productsAssumption_status = !patientEntity_productsAssumption_details.isEmpty();
                    String patientEntity_physicalActivity_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_physicalActivity)).getText().toString();
                    boolean patientEntity_physicalActivity_status = !patientEntity_physicalActivity_details.isEmpty();
                    String patientEntity_workingActivity_details = ((EditText)layout.findViewById(R.id.activityaddpatient_editText_workingActivity)).getText().toString();
                    boolean patientEntity_workingActivity_status = !patientEntity_workingActivity_details.isEmpty();
                    PatientEntity patientEntity = new PatientEntity(patientEntity_name, patientEntity_surname, patientEntity_bdate,
                            patientEntity_sex, patientEntity_phone,
                            patientEntity_visitReason, patientEntity_previousDiets_status, patientEntity_previousDiets_details,
                            patientEntity_weightHistory, patientEntity_previousPathologies_status, patientEntity_previousPathologies_details,
                            patientEntity_hereditaryPathologies_status, patientEntity_hereditaryPathologies_details,
                            patientEntity_allergies_status, patientEntity_allergies_details,
                            patientEntity_productsAssumption_status, patientEntity_productsAssumption_details,
                            patientEntity_intestine, patientEntity_menstrualCycle,
                            patientEntity_physicalActivity_status, patientEntity_physicalActivity_details,
                            patientEntity_workingActivity_status, patientEntity_workingActivity_details);
                    ((ActivityAddPatient2)getActivity()).setPatientEntity(patientEntity);

                            ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                            viewPager.setCurrentItem(1);
                } else {
                    String message = getString(R.string.activityaddpatient_string_alertTitle_formCheck);
                    if(patientEntity_name.isEmpty()) {
                        message += getString(R.string.activityaddpatient_string_alertMessage_formCheck_name);
                    }
                    if(patientEntity_surname.isEmpty()) {
                        message += getString(R.string.activityaddpatient_string_alertMessage_formCheck_surname);
                    }
                    if(patientEntity_phone.isEmpty()) {
                        message += getString(R.string.activityaddpatient_string_alertMessage_formCheck_phone);
                    }

                    AlertDialog.Builder builder = AlertBuilderUtils.BuildAlert(getActivity(), R.string.error, message);
                    builder.setPositiveButton(R.string.back, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        });
    }

    public String getTagFromRadioGroup(View layout, int radioGroupReference) {
        RadioGroup radioGroup = layout.findViewById(radioGroupReference);
        RadioButton radioButton = layout.findViewById(radioGroup.getCheckedRadioButtonId());
        return radioButton.getTag().toString();
    }
}