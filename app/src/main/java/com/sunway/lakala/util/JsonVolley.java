package com.sunway.lakala.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sunway.lakala.R;
import com.sunway.lakala.resp.BaseResp;
import com.sunway.lakala.view.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonVolley implements Response.ErrorListener, Listener<JSONObject> {
	WeakReference<Context> context;
	private static RequestQueue myQueue;
	Map<String, Object> params = new HashMap<String, Object>();
	WeakReference<Handler> handler;
	int requestSucc = -1;
	int requestFail = -1;
	LoadingDialog dialog;
	List<Object> orderedParams = new ArrayList<>();
	private static final String REQUEST_TYPE = "10";
	private static  final String CHANNEL= "22";
	public JsonVolley(Context context, int requestSucc, int requestFail) {
		this.context = new WeakReference<Context>(context);
		if(myQueue == null) {
			myQueue = Volley.newRequestQueue(context);
		}
		this.requestSucc = requestSucc;
		this.requestFail = requestFail;

	}

	public Map<String, Object> getParams() {
		return params;
	}


	public void addOrderedParams(Object p){
		orderedParams.add(p);
	}

	public void addOneOrderedParams(Object p){
		orderedParams.clear();
		orderedParams.add(p);
	}
	public void requestGet(String url, Handler handler) {
		this.handler = new WeakReference<Handler>(handler);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url
				+ Map2KV.map(params), null, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("x-ldj-ctype",REQUEST_TYPE);
				headers.put("x-ldj-channel",CHANNEL);
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);

	}

	public void _requestGet(String url, Handler handler) {
		this.handler = new WeakReference<Handler>(handler);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url
				+ ParamsParser.parse(orderedParams), null, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("x-ldj-ctype",REQUEST_TYPE);
				headers.put("x-ldj-channel",CHANNEL);
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);

	}

	public void requestGet(String url, Handler handler,final String accessToken) {

		this.handler = new WeakReference<Handler>(handler);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url
				+ Map2KV.map(params), null, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				initHeader(headers,accessToken);
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);

	}

	public void _requestGet(String url, Handler handler,final String accessToken) {

		this.handler = new WeakReference<Handler>(handler);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url
				+ ParamsParser.parse(orderedParams), null, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				initHeader(headers,accessToken);
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);

	}

	 public void requestGet(String url, LoadingDialog dialog, Handler handler) {
		 this.dialog = dialog;
		 dialog.show();
		 requestGet(url, handler);

	 }

	public void _requestGet(String url, LoadingDialog dialog, Handler handler) {
		this.dialog = dialog;
		dialog.show();
		_requestGet(url, handler);

	}

	public void requestGet(String url, LoadingDialog dialog, Handler handler,String accessToken) {
		this.dialog = dialog;
		dialog.show();
		requestGet(url, handler, accessToken);

	}

	public void _requestGet(String url,LoadingDialog dialog, Handler handler,String accessToken) {
		this.dialog = dialog;
		dialog.show();
		_requestGet(url, handler, accessToken);

	}

	public void _requestPost(String url, Handler handler) {
		this.handler = new WeakReference<Handler>(handler);
		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url + ParamsParser.parse(orderedParams), jsonObject, this, this) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("x-ldj-ctype",REQUEST_TYPE);
				headers.put("x-ldj-channel",CHANNEL);
				headers.put("Content-Type", "application/json; charset=utf-8");

				return headers;
			}
		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		myQueue.add(jsonObjectRequest);
	}

	public void requestPost(String url, Handler handler) {
		this.handler = new WeakReference<Handler>(handler);
		JSONObject jsonObject = new JSONObject(params);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("x-ldj-ctype",REQUEST_TYPE);
				headers.put("x-ldj-channel",CHANNEL);
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}

	public void requestDelete(String url, Handler handler,final String accessToken,String id) {
		this.handler = new WeakReference<Handler>(handler);
		JSONObject jsonObject = new JSONObject(params);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.DELETE, url + "/" + id, jsonObject, this, this) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				initHeader(headers,accessToken);
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}

	private void initHeader(Map<String, String> headers,String accessToken){
		headers.put("Accept", "application/json");
		headers.put("x-ldj-ctype",REQUEST_TYPE);
		headers.put("x-ldj-channel",CHANNEL);
		headers.put("Content-Type", "application/json; charset=utf-8");
		headers.put("x-ldj-token", accessToken);

	}

	public void requestPost(String url,LoadingDialog dialog, Handler handler,final String accessToken) {
		this.handler = new WeakReference<Handler>(handler);
		this.dialog = dialog;
		dialog.show();
		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
                initHeader(headers,accessToken);
				return headers;
			}
		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}

	public void requestPost(String url, Handler handler,final String accessToken) {
		this.handler = new WeakReference<Handler>(handler);

		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url, jsonObject, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				initHeader(headers,accessToken);
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}


	public void _requestPost(String url, Handler handler,final String accessToken) {
		this.handler = new WeakReference<Handler>(handler);

		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url + ParamsParser.parse(orderedParams), jsonObject, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				initHeader(headers, accessToken);
				return headers;
			}

		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}

	public void _requestPost(String url, LoadingDialog dialog,Handler handler,final String accessToken) {
		this.handler = new WeakReference<Handler>(handler);
		this.dialog = dialog;
		dialog.show();

		JSONObject jsonObject = new JSONObject(params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Method.POST, url + ParamsParser.parse(orderedParams), jsonObject, this, this) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
				initHeader(headers,accessToken);
				return headers;
			}
		};
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000,// 默认超时时间，应设置一个稍微大点儿的
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,// 默认最大尝试次数0
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		myQueue.add(jsonObjectRequest);
	}

	 public void requestPost(String url, LoadingDialog dialog,Handler handler) {
	 this.dialog = dialog;
		 dialog.show();
		 requestPost(url, handler);
	 }



	public void _requestPost(String url, LoadingDialog dialog,Handler handler) {
		this.dialog = dialog;
		dialog.show();
		_requestPost(url, handler);

	}

	public void addParams(String key, Object value) {
		params.put(key, value);
	}

	@Override
	public void onResponse(JSONObject response) {
		Log.e("---------", "response:" + response);
		if (dialog != null) {
		 dialog.cancel();
		 }

		if (response == null) {
			if(handler != null && handler.get() != null && context != null && context.get() != null) {
				handler.get().obtainMessage(requestFail,
						context.get().getString(R.string.request_fail)).sendToTarget();
			}
			return;
		}

		int code = -1;
		String now = "";
		JSONObject obj = null;
		try {
			 obj = new JSONObject(response.toString());
			code = obj.getInt("responseCode");
		} catch (JSONException e) {
			e.printStackTrace();
			if(handler != null && handler.get() != null && context != null && context.get() != null) {
				handler.get().obtainMessage(requestFail,
						context.get().getString(R.string.request_fail)).sendToTarget();
			}
		}

		if(handler == null || handler.get() == null){
			return;
		}
		Message msg = handler.get().obtainMessage(requestSucc);
		if (code == NetParams.RESPONCE_NORMAL) {
			msg.arg1 = NetParams.RESPONCE_NORMAL;
			msg.obj = obj.toString();
		} else {
			msg.arg1 = NetParams.RESPONCE_UNNORAML;
			BaseResp errorResp =(BaseResp) JsonParser.jsonToObject(response.toString(), BaseResp.class);
			msg.arg2 = code;
			msg.obj = errorResp.getResponseMsg();
			Bundle bundle = new Bundle();
			bundle.putString("data",response.toString());
			msg.setData(bundle);
		}
		handler.get().sendMessage(msg);

	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Log.e("---------", "response:" + error);
		if (dialog != null) {
			dialog.cancel();
		}
		if(handler == null || handler.get() == null){
			return;
		}
		handler.get().obtainMessage(requestFail, error.getMessage()).sendToTarget();

	}
}
