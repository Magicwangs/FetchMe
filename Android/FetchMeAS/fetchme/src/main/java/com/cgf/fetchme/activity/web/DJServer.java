package com.cgf.fetchme.activity.web;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DJServer {
	private static final String BASE_URL = "http://192.168.1.102:80/FetchMe/";
	private static AsyncHttpClient client = new AsyncHttpClient();
	private static String csrfToken;

	public static String getCsrfToken() {
		return csrfToken;
	}

	public static void setCsrfToken(String csrfToken) {
//		if(csrfToken == null)
			DJServer.csrfToken = csrfToken;
	}

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		if(params == null) {
			params = new RequestParams();
		}
		params.put("csrfmiddlewaretoken", csrfToken);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
