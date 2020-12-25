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
import com.cafape.nutriplan.adapters.FilesRecyclerViewAdapter;
import com.cafape.nutriplan.adapters.VisitsRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_File#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_File extends Fragment
{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FilesRecyclerViewAdapter filesRecyclerViewAdapter;

    private TextView activitypatientaccount_textView_nodata_details;
    private ConstraintLayout activitypatientaccount_constraintLayout_nodata;


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
        View layout = inflater.inflate(R.layout.fragment_patientaccount_files, container, false);
        setUiComponents(layout);
        setListeners(layout);
        return layout;
    }

    public void setUiComponents(View layout) {
        ActivityPatientAccount activityPatientAccount = ((ActivityPatientAccount)getActivity());
        RecyclerView activitypatientaccount_recycleView_files = layout.findViewById(R.id.activitypatientaccount_recycleView_files);
        filesRecyclerViewAdapter = new FilesRecyclerViewAdapter(getContext(), activityPatientAccount.getPatientFilesEntities());
        activitypatientaccount_recycleView_files.setLayoutManager(new LinearLayoutManager(activityPatientAccount.getApplicationContext()));
        activitypatientaccount_recycleView_files.setAdapter(filesRecyclerViewAdapter);
        activityPatientAccount.setFilesRecyclerViewAdapter(filesRecyclerViewAdapter);

        if(filesRecyclerViewAdapter.getItemCount() == 0) {
            activitypatientaccount_textView_nodata_details = layout.findViewById(R.id.textView_nodata_details);
            activitypatientaccount_constraintLayout_nodata = layout.findViewById(R.id.constraintLayout_nodata);
            activitypatientaccount_textView_nodata_details.setText(getString(R.string.activitypatientaccount_string_nodata_details));
            activitypatientaccount_textView_nodata_details.setVisibility(View.VISIBLE);
            activitypatientaccount_constraintLayout_nodata.setVisibility(View.VISIBLE);
        } else {
            if(activitypatientaccount_textView_nodata_details == null) {
                activitypatientaccount_textView_nodata_details = layout.findViewById(R.id.textView_nodata_details);
                activitypatientaccount_constraintLayout_nodata = layout.findViewById(R.id.constraintLayout_nodata);
            }
            activitypatientaccount_textView_nodata_details.setText("");
            activitypatientaccount_constraintLayout_nodata.setVisibility(View.GONE);
        }

        FloatingActionButton activitypatientaccount_fab_file_add = layout.findViewById(R.id.activitypatientaccount_fab_file_add);
        activitypatientaccount_fab_file_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ((ActivityPatientAccount)getActivity()).performFileSearch();
            }
        });
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