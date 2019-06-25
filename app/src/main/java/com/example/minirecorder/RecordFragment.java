package com.example.minirecorder;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordFragment extends SuperFragment implements View.OnClickListener {

    public static String TAG = "RecordFragment";
    private TextView mPassageTv;
    private ImageButton mRecordBtn;
    private Button mSubmitBtn;
    private Button mCancelBtn;


    public RecordFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        mPassageTv = (TextView) rootView.findViewById(R.id.passageTv);
        mPassageTv.setMovementMethod(new ScrollingMovementMethod());
        mRecordBtn = (ImageButton) rootView.findViewById(R.id.recordBtn);
        mSubmitBtn = (Button) rootView.findViewById(R.id.submitBtn);
        mCancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        mRecordBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {

        if (v == null) return;

        if (v == this.mRecordBtn) {
            Log.d(TAG, "mRecordBtn");
            startRecording();
        } else if(v == mSubmitBtn){
            Log.d(TAG, "mSubmitBtn");
            //todo
        }
        else if(v == mCancelBtn){
            Log.d(TAG, "mCancelBtn");
            //todo
        }
    }

    private void startRecording() {
        //todo
    }

    private void setPasageText(String resultFromServer){
        mPassageTv.setText(resultFromServer);
    }
}