package com.cgf.fetchmeas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.MainTextView) TextView mMainTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		// for test the butter knife to simply the code of the findViewById and so on
		ButterKnife.bind(this);
		mMainTextView.setText("Hello FetchMe");
        
		Intent intent;
        boolean isLogin = true;
        if(isLogin == false) {
            intent = new Intent(this, LoginActivity.class);
            Log.d(TAG, "choose LoginActivity");
        }
        else {
            intent = new Intent(this, FetchMeActivity.class);
            Log.d(TAG, "choose FetchMeActivity");
        }
        if(intent != null) {
            startActivity(intent);
        }
        else {
            Log.d(TAG, "No Other Activity");
        }
    }
}
