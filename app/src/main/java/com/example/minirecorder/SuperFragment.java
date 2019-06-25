package com.example.minirecorder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class SuperFragment extends Fragment {

    public boolean onKeyDown(int keyCode, KeyEvent event){return false;}

    public void onFragmentResume() {}

    public static int mScanRequestCode = 200;

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == mScanRequestCode){
                String scannedText = data.getStringExtra("SCAN_RESULT");
                if (this.getView() != null) {
                    View focusView = this.getView().findFocus();
                    if (focusView != null && focusView instanceof EditText){
                        ((EditText) focusView).setText(scannedText);
                        ((EditText) focusView).onEditorAction(EditorInfo.IME_ACTION_DONE);
                    }
                }
            }
        }
    }

}
