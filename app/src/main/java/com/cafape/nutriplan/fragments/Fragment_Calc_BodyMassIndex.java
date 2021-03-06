package com.cafape.nutriplan.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cafape.nutriplan.ActivityCalc;
import com.cafape.nutriplan.ActivityPatientAccount;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.support.Utils;

import java.util.regex.Pattern;

import static com.cafape.nutriplan.support.Globals.LONG_DASH;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Calc_BodyMassIndex#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Calc_BodyMassIndex extends Fragment
{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText fragment_calc_bmi_form_editText_height;
    private EditText fragment_calc_bmi_form_editText_weight;
    private TextView fragment_calc_bmi_constraintLayout_textView_result;
    private ConstraintLayout fragment_calc_bmi_constraintLayout_scale;

    protected FragmentActivity mActivity;

    public Fragment_Calc_BodyMassIndex() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcBodyMassIndex.
     */
    public static Fragment_Calc_BodyMassIndex newInstance(String param1, String param2) {
        Fragment_Calc_BodyMassIndex fragment = new Fragment_Calc_BodyMassIndex();
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
        View layout = inflater.inflate(R.layout.fragment_calc_body_mass_index, container, false);
        setUiComponent(layout);
        setListeners();
        return layout;
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(activity);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    */

    public void setUiComponent(View layout) {
        fragment_calc_bmi_form_editText_height = layout.findViewById(R.id.fragment_calc_bmi_form_editText_height);
        fragment_calc_bmi_form_editText_weight = layout.findViewById(R.id.fragment_calc_bmi_form_editText_weight);
        fragment_calc_bmi_constraintLayout_textView_result = layout.findViewById(R.id.fragment_calc_bmi_constraintLayout_textView_result);
        fragment_calc_bmi_constraintLayout_scale = layout.findViewById(R.id.fragment_calc_bmi_constraintLayout_scale);
    }

    public void setListeners() {
        fragment_calc_bmi_form_editText_height.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String height_string = editable.toString();
                setBMIResult(height_string, fragment_calc_bmi_form_editText_weight.getText().toString());
            }
        });

        fragment_calc_bmi_form_editText_weight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String weight_string = editable.toString();
                setBMIResult(fragment_calc_bmi_form_editText_height.getText().toString(), weight_string);
            }
        });

        fragment_calc_bmi_constraintLayout_scale.setOnClickListener(view -> {
            ActivityCalc activityCalc = ((ActivityCalc)getActivity());
            activityCalc.showBMITable();
        });
    }

    private Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");

    public boolean isPositiveNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public void setNullResult() {
        fragment_calc_bmi_constraintLayout_textView_result.setText(LONG_DASH);
    }

    public void setBMIResult(String height_string, String weight_string) {
        if((height_string != null && height_string.length() > 0) && (weight_string != null && weight_string.length() > 0)) {
            if(isPositiveNumeric(height_string) && isPositiveNumeric(weight_string)) {
                String bmiValue = Utils.calculateBMI(height_string, weight_string);

                fragment_calc_bmi_constraintLayout_textView_result.setText(bmiValue);
            }
            else {
                setNullResult();
            }
        } else {
            setNullResult();
        }
    }
}