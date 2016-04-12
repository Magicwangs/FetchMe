package com.cgf.fetchme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Header;

import com.cgf.fetchme.web.DJServer;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private static final String TAG = "LoginActivity";

	protected static final int LOGIN_SUCCESS = 0;
	protected static final int LOGIN_FAIL_AUTH = 1;
	protected static final int LOGIN_FAIL_NET = 2;

	// protected String host = "chenguanfu.f3322.net";
	protected String mHost = "115.215.93.214";
	private String mLoginUrl = "login/";
	private RequestParams mLoginParams = new RequestParams();
	private EditText mUserNameEditText = null;
	private EditText mUserPwdEditText = null;
	private Button mLoginButton = null;
	private String mUserName = null;
	private String mUserPwd = null;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(TAG, "handleMessage----in----");
			switch (msg.what) {
			case LOGIN_SUCCESS:
				Log.d(TAG, "LOGIN_SUCCESS");
				Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
				break;
			case LOGIN_FAIL_AUTH:
				Log.d(TAG, "LOGIN_FAIED");
				Toast.makeText(LoginActivity.this, "login fail auth", Toast.LENGTH_SHORT).show();
				break;
			default:
				Log.d(TAG, "LOGIN_FAIED");
				Toast.makeText(LoginActivity.this, "login fail net", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		InitUI();
	}

	private void InitUI() {
		// TODO Auto-generated method stub
		mUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
		mUserPwdEditText = (EditText) findViewById(R.id.userPwdEditText);
		mLoginButton = (Button) findViewById(R.id.loginButton);
		mLoginButton.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		moveTaskToBack(true);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.loginButton) {
			Log.d(TAG, "loginButton----onClick----in----");
			Login();
		}
	}

	private void Login() {
		// TODO Auto-generated method stub
		Log.d(TAG, "Login----in----");

		mUserNameEditText.clearFocus();
		mUserPwdEditText.clearFocus();
		// hide the soft keyboard
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

		mUserName = mUserNameEditText.getText().toString();
		mUserPwd = mUserPwdEditText.getText().toString();

		LoginOnline(mUserName, mUserPwd);
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
		};

		@Override
		public void onSuccess(int statusCode, Header[] headers, String response) {
			// TODO Auto-generated method stub
			Log.d(TAG, "onSuccess----in----");
			try {
				Gson gson = new Gson();
				mLoginResponse = gson.fromJson(response, LoginResponse.class);
				if(mLoginResponse.getIsLogin()) {
					DJServer.setCsrfToken(mLoginResponse.getCsrfToken());
//					DJServer.post("get_name/", null, new AsyncHttpResponseHandler() {
//						@Override
//						public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//							// TODO Auto-generated method stub
//							
//						}
//					});
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
		public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
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

	Thread mLoginThread = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = null;
			URL url = null;
			HttpURLConnection connection = null;
			InputStreamReader in = null;
			try {
				url = new URL("http://" + mHost + ":8000/FetchMe/login?userName=" + mUserName + "&" + "userPwd=" + mUserPwd);
				connection = (HttpURLConnection) url.openConnection();
				in = new InputStreamReader(connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(in);
				StringBuffer strBuffer = new StringBuffer();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					strBuffer.append(line);
				}
				result = strBuffer.toString();
				 if (result.equals("login success"))
					 mHandler.sendEmptyMessage(LOGIN_SUCCESS);
				 else
					 mHandler.sendEmptyMessage(LOGIN_FAIL_AUTH);
				Log.d(TAG, "result=" + result);
			} catch (Exception e) {
				e.printStackTrace();
				mHandler.sendEmptyMessage(LOGIN_FAIL_NET);
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	});
	
	private void LoginOnline(String userName, String userPwd) {
		Log.d(TAG, "LoginOnline----in----" + "userName=" + userName + ",userPwd=" + userPwd);
		if(mLoginParams == null)
			mLoginParams = new RequestParams();
		mLoginParams.put("userPwd", userPwd);
		mLoginParams.put("userName", userName);
		DJServer.get(mLoginUrl, mLoginParams, mLoginHandler);
//		mLoginThread.start();
		Log.d(TAG, "LoginOnline----out----");
	}
}
