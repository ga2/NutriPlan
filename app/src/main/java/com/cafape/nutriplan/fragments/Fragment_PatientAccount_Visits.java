package com.cafape.nutriplan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cafape.nutriplan.ActivityPatientAccount;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.adapters.VisitsRecyclerViewAdapter;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_Visits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_Visits extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private VisitsRecyclerViewAdapter visitsRecyclerViewAdapter;

    private TextView activitypatientaccoun_textView_nodata_details;
    private ConstraintLayout activitypatientaccoun_constraintLayout_nodata;

    public Fragment_PatientAccount_Visits() {
        setRetainInstance(true);
    }
/*
    public Fragment_PatientAccount_Visits() {
        // Required empty public constructor
    }
*/
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_AddPatient_Antropometry.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_PatientAccount_Visits newInstance(String param1, String param2) {
        Fragment_PatientAccount_Visits fragment = new Fragment_PatientAccount_Visits();
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
        View layout = inflater.inflate(R.layout.fragment_patientaccount_visits, container, false);
        setUiComponents(layout);
        initVisitsList();
        return layout;
    }

    public void setUiComponents(View layout) {
        ActivityPatientAccount activityPatientAccount = ((ActivityPatientAccount)getActivity());
        RecyclerView activitypatientaccount_recycleView_visits = layout.findViewById(R.id.activitypatientaccount_recycleView_visits);
        visitsRecyclerViewAdapter = new VisitsRecyclerViewAdapter(getContext(), activityPatientAccount.getPatientAntropometryEntities());
        activitypatientaccount_recycleView_visits.setLayoutManager(new LinearLayoutManager(activityPatientAccount.getApplicationContext()));
        activitypatientaccount_recycleView_visits.setAdapter(visitsRecyclerViewAdapter);
        activityPatientAccount.setVisitsRecyclerViewAdapter(visitsRecyclerViewAdapter);

        if(visitsRecyclerViewAdapter.getItemCount() == 0) {
            activitypatientaccoun_textView_nodata_details = layout.findViewById(R.id.textView_nodata_details);
            activitypatientaccoun_constraintLayout_nodata = layout.findViewById(R.id.constraintLayout_nodata);
            activitypatientaccoun_textView_nodata_details.setText(getString(R.string.activitypatientaccount_string_nodata_details));
            activitypatientaccoun_textView_nodata_details.setVisibility(View.VISIBLE);
            activitypatientaccoun_constraintLayout_nodata.setVisibility(View.VISIBLE);
        } else {
            if(activitypatientaccoun_textView_nodata_details == null) {
                activitypatientaccoun_textView_nodata_details = layout.findViewById(R.id.textView_nodata_details);
                activitypatientaccoun_constraintLayout_nodata = layout.findViewById(R.id.constraintLayout_nodata);
            }
            activitypatientaccoun_textView_nodata_details.setText("");
            activitypatientaccoun_constraintLayout_nodata.setVisibility(View.GONE);
        }

        FloatingActionButton activitypatientaccount_fab_visit_add = layout.findViewById(R.id.activitypatientaccount_fab_visit_add);
        activitypatientaccount_fab_visit_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ((ActivityPatientAccount)getActivity()).openNewVisit();
            }
        });
    }

    public void initVisitsList() {

    }

    public void updateVisitsList( PatientAntropometryEntity patientAntropometryEntity) {
        //if(visitsRecyclerViewAdapter != null)
        {
            visitsRecyclerViewAdapter.addToRetrievedData(patientAntropometryEntity);
            visitsRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }
}