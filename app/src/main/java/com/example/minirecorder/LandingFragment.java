package com.example.minirecorder;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

public class LandingFragment extends SuperFragment implements View.OnClickListener {

    public static String TAG = "LandingFragment";
    private EditText mNameEt;
    private RadioGroup mLangRg;
    private Button mNextBtn;
    private String mChosenLang;

    public LandingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_landing, container, false);
        mNameEt = (EditText) rootView.findViewById(R.id.nameET);
        mNameEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){
                    Toast.makeText(getActivity(), "Overrided", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        mLangRg = (RadioGroup) rootView.findViewById(R.id.languageRG);
        mLangRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) rootView.findViewById(checkedId);
                mChosenLang =  radbtn.getText().toString();
                Toast.makeText(getActivity(), "You choose " + radbtn.getText(), Toast.LENGTH_LONG).show();
            }
        });
        mNextBtn = (Button) rootView.findViewById(R.id.nextBtn);
        mNextBtn.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v == null) return;

        if (v == this.mNextBtn) {
            Log.d(TAG, "onClick: Open camera");
            goToNextFragment();
        }
    }

    private void goToNextFragment() {
        if(StringUtils.isNotEmpty(mNameEt.getText().toString())){
            Log.d(TAG, "Name + Lang:" +mNameEt.getText().toString() + mChosenLang);
            SuperFragment recordFragment = new RecordFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nameString", mNameEt.getText().toString());
            bundle.putString("langString", mChosenLang);
            recordFragment.setArguments(bundle);
            FragmentUtils.addFragmentToMainContainer(getActivity(), recordFragment,RecordFragment.TAG, true);
            Log.d(TAG, "Go");


        }
        else{
            Toast.makeText(getActivity(), "Input name first", Toast.LENGTH_LONG).show();
        }
    }
}
