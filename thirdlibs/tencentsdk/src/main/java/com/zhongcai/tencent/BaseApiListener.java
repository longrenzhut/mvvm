package com.zhongcai.tencent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;


abstract public class BaseApiListener implements IRequestListener {

	abstract void OnFailed(String error);

	public BaseApiListener() {

	}


	@Override
	public void onIOException(final IOException e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onMalformedURLException(final MalformedURLException e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onJSONException(final JSONException e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onConnectTimeoutException(ConnectTimeoutException e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onSocketTimeoutException(SocketTimeoutException e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onUnknowException(Exception e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onHttpStatusException(HttpStatusException e) {
		OnFailed(e.getMessage());
	}

	@Override
	public void onNetworkUnavailableException(NetworkUnavailableException e) {
		OnFailed(e.getMessage());
	}



}
