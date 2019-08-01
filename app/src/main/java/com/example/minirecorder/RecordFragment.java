package com.example.minirecorder;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import static com.example.minirecorder.GetPassageUtil.NUMBER_OF_PASSAGES;

public class RecordFragment extends SuperFragment implements View.OnClickListener {

    public static String TAG = "RecordFragment";
    private TextView mPassageTv;
    private ImageButton mRecordBtn;
    private Button mStopBtn;
    private Button mStartBtn;
    private ImageButton mDiscardBtn;
    public static final int ACTIVITY_RECORD_SOUND = 0;
    private String mNameString;
    private String mLangString;
    private String AudioSavePathInDevice;
    private Button mSubmitBtn;
    MediaRecorder mediaRecorder ;
    private String mAudioName = "";
    private int mPassageID;


    public RecordFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        mPassageTv = (TextView) rootView.findViewById(R.id.passageTv);
        mPassageTv.setMovementMethod(new ScrollingMovementMethod());
        mRecordBtn = (ImageButton) rootView.findViewById(R.id.recordBtn);
        mDiscardBtn = (ImageButton) rootView.findViewById(R.id.discardBtn);
        mStopBtn = (Button) rootView.findViewById(R.id.stopBtn);
        mStartBtn = (Button) rootView.findViewById(R.id.startBtn);
        mSubmitBtn = (Button) rootView.findViewById(R.id.submitBtn);
        mRecordBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        mStartBtn.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
        mDiscardBtn.setOnClickListener(this);
        mSubmitBtn.setVisibility(View.INVISIBLE);
        mDiscardBtn.setVisibility(View.INVISIBLE);
        getPasageText();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mNameString = bundle.getString("nameString", "CannotGetName");
            mLangString = bundle.getString("langString", "CannotGetLang");
        }
        return rootView;

    }

    @Override
    public void onClick(View v) {

        if (v == null) return;

        if (v == this.mRecordBtn) {
            Log.d(TAG, "mRecordBtn");
            //startRecording();
        } else if(v == mStopBtn){
            Log.d(TAG, "mStopBtn");
            //todo
            mediaRecorder.stop();
            mStopBtn.setEnabled(false);
            mStartBtn.setEnabled(true);
            mSubmitBtn.setVisibility(View.VISIBLE);
            mDiscardBtn.setVisibility(View.VISIBLE);


            Toast.makeText(getActivity(), "Recording Completed",
                    Toast.LENGTH_LONG).show();

        } else if(v == mStartBtn){
            Log.d(TAG, "mStartBtn");
            //todo

                mAudioName = fileNameFactory();
                AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mAudioName + ".mp3";
                MediaRecorderReady();

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                mStartBtn.setEnabled(false);
                mStopBtn.setEnabled(true);

                Toast.makeText(getActivity(), "Recording started",
                        Toast.LENGTH_LONG).show();

        } else if(v == mSubmitBtn){
            File file = null;
            if (!TextUtils.isEmpty(AudioSavePathInDevice)) {
                file = new File(AudioSavePathInDevice);
                try {
                    //File file = new File(audioURI.getPath());

                    UploadToServerUtil connection = new UploadToServerUtil();
                    connection.uploadFile("http://chanjacky.com/minirecorder/uploadhelper.php", file, mAudioName, getActivity());
                    FragmentUtils.onBack(getActivity());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (v == mDiscardBtn){
            File fdelete = new File(AudioSavePathInDevice);
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    Toast.makeText(getActivity(), "Audio deleted!",
                            Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onClick: mDiscardBtn deleted");
                } else {
                    Log.d(TAG, "onClick: mDiscardBtn not yet deleted");
                }
            }
            mDiscardBtn.setVisibility(View.INVISIBLE);
            mSubmitBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVITY_RECORD_SOUND:
                Log.d(TAG, "onActivityResult: ACTIVITY_RECORD_SOUND");
                if (resultCode == Activity.RESULT_OK) {
                   //todo
                Uri audioURI = data.getData();
                    try {
                        //File file = new File(audioURI.getPath());

                        UploadToServerUtil connection = new UploadToServerUtil();
                        connection.uploadFile("http://chanjacky.com/minirecorder/uploadhelper.php",FileUtil.getFileFromUri(audioURI, getActivity()),fileNameFactory(),getActivity());
                        FragmentUtils.onBack(getActivity());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    private void startRecording() {
        //todo
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
    }

    private String fileNameFactory(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return mNameString + "_" + mLangString + "_" + currentDateTimeString +"_" + mPassageID;
    }

    private void getPasageText(){
        Log.d(TAG, "getPasageText is running ");
        String text = "cannot get text ";
        Random r = new Random();
        mPassageID = r.nextInt(GetPassageUtil.NUMBER_OF_PASSAGES) + 1;

        try {
            text = GetPassageUtil.getPassageByIndex(getActivity().getAssets().open("passage.json"),mPassageID);
            Log.d(TAG, "getPasageText is trying ");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "getPasageText is catching ");
        }
        if(StringUtils.isNotEmpty(text)&&text!=null) {
            Log.d(TAG, "getPasageText is setting msg ");
            mPassageTv.setText(text);
        }
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

}
