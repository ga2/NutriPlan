package com.cafape.nutriplan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cafape.nutriplan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_File#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_File extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Fragment_PatientAccount_File() {
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
    public static Fragment_PatientAccount_File newInstance(String param1, String param2) {
        Fragment_PatientAccount_File fragment = new Fragment_PatientAccount_File();
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
        View layout = inflater.inflate(R.layout.fragment_activity_patient_account_files, container, false);
        setUiComponents(layout);
        setListeners(layout);
        return layout;
    }

    public void setUiComponents(View layout) {
        //activityaddpatient_button_backto2 = layout.findViewById(R.id.activityaddpatient_button_backto2);
        //activityaddpatient_button_save = layout.findViewById(R.id.activityaddpatient_button_save);

    }

    public void setListeners(View layout) {
       /* activityaddpatient_button_backto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1);
            }
        });

        */
    }


    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }
}