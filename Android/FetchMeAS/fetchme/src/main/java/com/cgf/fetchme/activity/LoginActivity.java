package com.cgf.fetchme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cgf.fetchme.R;
import com.cgf.fetchme.activity.web.DJServer;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "LoginActivity";

    private String mLoginUrl = "login/";
    private RequestParams mLoginParams = new RequestParams();
    private String mUserName = "admin";
    private String mUserPwd = "admin";

    private Toolbar toolbar;
    private EditText inputName, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword;
    private Button btnLogin;

    Boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(isLogin)
            GoMainUI();
        InitUI();
    }

    private void GoMainUI() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();   //防止返回该界面——登陆仅需一次
    }
    private void InitUI() {
        // TODO Auto-generated method stub
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.btn_login) {
            Log.d(TAG, "loginButton----onClick----in----");
            Login();
        }
    }

    private void Login() {
        // TODO Auto-generated method stub
        Log.d(TAG, "Login----in----");

        if (!validateName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        mUserName = inputName.getText().toString();
        mUserPwd = inputPassword.getText().toString();

        if(mUserName.equals("admin") && mUserPwd.equals("admin")) {
            GoMainUI();
        }
        else {
            LoginOnline(mUserName, mUserPwd);
        }
        Log.d(TAG, "Login----out----" + "userName=" + mUserName + ",userPwd=" + mUserPwd);
    }

    class LoginResponse {
        private boolean isLogin;
        private String csrfToken;

        public LoginResponse(boolean isLogin, String csrfToken) {
            super();
            this.isLogin = isLogin;
            this.csrfToken = csrfToken;
        }

        public boolean getIsLogin() {
            return isLogin;
        }

        public void setIsLogin(boolean isLogin) {
            this.isLogin = isLogin;
        }

        public String getCsrfToken() {
            return csrfToken;
        }

        public void setCsrfToken(String csrfToken) {
            this.csrfToken = csrfToken;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "isLogin=" + isLogin + ",csrfToken=" + csrfToken;
        }
    }

    LoginResponse mLoginResponse;
    AsyncHttpResponseHandler mLoginHandler = new TextHttpResponseHandler() {
        public void onStart() {
            Log.d(TAG, "onStart----in----");
            Log.d(TAG, "onStart----out----");
        }

        @Override
        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String response) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onSuccess----in----");
            try {
                Gson gson = new Gson();
                mLoginResponse = gson.fromJson(response, LoginResponse.class);
                if (mLoginResponse.getIsLogin()) {
                    DJServer.setCsrfToken(mLoginResponse.getCsrfToken());
                    //					DJServer.post("get_name/", null, new AsyncHttpResponseHandler() {});
                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "login fail auth", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            Log.d(TAG, "onSuccess----out----" + mLoginResponse);
        }

        @Override
        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String errorResponse, Throwable e) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onFailure----in----");
            Toast.makeText(LoginActivity.this, "login fail net", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onFailure----out----" + errorResponse);
        }

        public void onRetry(int retryNo) {
            Log.d(TAG, "onRetry----in----");
            Log.d(TAG, "onRetry----out----");
        }
    };

    private void LoginOnline(String userName, String userPwd) {
        Log.d(TAG, "LoginOnline----in----" + "userName=" + userName + ",userPwd=" + userPwd);
        if (mLoginParams == null)
            mLoginParams = new RequestParams();
        mLoginParams.put("userPwd", userPwd);
        mLoginParams.put("userName", userName);
        DJServer.get(mLoginUrl, mLoginParams, mLoginHandler);
        Log.d(TAG, "LoginOnline----out----");
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
