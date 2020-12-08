package com.cafape.nutriplan.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cafape.nutriplan.R;

import java.util.regex.Pattern;

import static com.cafape.nutriplan.Globals.LONG_DASH;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Calc_IdealWeight#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Calc_IdealWeight extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText fragment_calc_pi_form_editText_height;
    private TextView fragment_calc_pi_constraintLayout_textView_result;
    private RadioGroup fragment_calc_pi_radioGroup_sex;

    public Fragment_Calc_IdealWeight() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcIdealWeight.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Calc_IdealWeight newInstance(String param1, String param2) {
        Fragment_Calc_IdealWeight fragment = new Fragment_Calc_IdealWeight();
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
        View layout = inflater.inflate(R.layout.fragment_calc_ideal_weight, container, false);
        setUiComponent(layout);
        setListeners();
        return layout;
    }

    public void setUiComponent(View layout) {
        fragment_calc_pi_form_editText_height = layout.findViewById(R.id.fragment_calc_pi_form_editText_height);
        fragment_calc_pi_constraintLayout_textView_result = layout.findViewById(R.id.fragment_calc_pi_constraintLayout_textView_result);
        fragment_calc_pi_radioGroup_sex = layout.findViewById(R.id.fragment_calc_pi_radioGroup_sex);
    }

    public void setListeners() {
        fragment_calc_pi_form_editText_height.addTextChangedListener(new TextWatcher()
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
                setIdealWeightResult(height_string);
            }
        });

        fragment_calc_pi_radioGroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                setIdealWeightResult(fragment_calc_pi_form_editText_height.getText().toString());
            }
        });
    }

    private Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");

    public boolean isPositiveNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public String calculateIdealWeigth(String height) {
        float height_float = Float.parseFloat(height);
        float divisor = 2;
        //LORENZ FORMULA
        RadioButton checkedRadioButton = fragment_calc_pi_radioGroup_sex.findViewById(fragment_calc_pi_radioGroup_sex.getCheckedRadioButtonId());
        String sex = checkedRadioButton.getTag().toString();
        if(sex.equals(getString(R.string.male_short))) {
            divisor = 4;
        }

        float result = height_float - 100 - (height_float - 150) / divisor;
        if(result > 0 && result < 1000) {
            fragment_calc_pi_constraintLayout_textView_result.setLines(2);
            return String.valueOf(result) + "\nkg";
        } else {
            fragment_calc_pi_constraintLayout_textView_result.setLines(1);
            return LONG_DASH;
        }
    }

    public void setNullResult() {
        fragment_calc_pi_constraintLayout_textView_result.setLines(1);
        fragment_calc_pi_constraintLayout_textView_result.setText(LONG_DASH);
    }

    public void setIdealWeightResult(String height_string) {
        if(height_string != null && height_string.length() > 0) {
            if(isPositiveNumeric(height_string)) {
                String weightValue = calculateIdealWeigth(height_string);

                fragment_calc_pi_constraintLayout_textView_result.setText(weightValue);
            }
            else {
                setNullResult();
            }
        } else {
            setNullResult();
        }
    }
}