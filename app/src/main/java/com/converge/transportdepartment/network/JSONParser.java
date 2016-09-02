package com.converge.transportdepartment.network;

import android.util.Log;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JSONParser {

	static InputStream is = null;

	static JSONObject jObj = null;

	static String json = "";

	// constructor

	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url) {
		jObj = null;
		// Making HTTP request

		try {

			// defaultHttpClient

			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();

			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(

					is, "utf-8"), 8);

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");

			}

			is.close();

			json = sb.toString();

		} catch (Exception e) {

			showLog("Buffer Error", "Error converting result " + e.toString());
			showLog("JSON Parser", "Error parsing data " + json);
		}

		// try parse the string to a JSON object

		try {

			jObj = new JSONObject(json);

		} catch (JSONException e) {

			showLog("JSON Parser", "Error parsing data " + e.toString());
			showLog("JSON Parser", "Error parsing data " + json);
		}

		// return JSON String

		return jObj;

	}

	// function get json from url

	// by making HTTP POST or GET mehtod

	public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
		jObj = null;
		// Making HTTP request

		try {

			// check for request method

			if (method == "POST") {

				// request method is POST

				// defaultHttpClient

				DefaultHttpClient httpClient = new DefaultHttpClient();

				HttpPost httpPost = new HttpPost(url);

				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();

				is = httpEntity.getContent();

				 /* HttpParams httpParameters = new BasicHttpParams();
			       // Set the timeout in milliseconds until a connection is established.
			       // The default value is zero, that means the timeout is not used.
			       int timeoutConnection = 30000;
			       HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			       // Set the default socket timeout (SO_TIMEOUT)
			       // in milliseconds which is the timeout for waiting for data.
			       int timeoutSocket = 31000;
			       HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
				// defaultHttpClient
			    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

			    HttpPost httpPost = new HttpPost(url);

				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();

				is = httpEntity.getContent();*/

			} else if (method == "GET") {

				// request method is GET

				DefaultHttpClient httpClient = new DefaultHttpClient();

				String paramString = URLEncodedUtils.format(params, "utf-8");

				url += "?" + paramString;

				HttpGet httpGet = new HttpGet(url);

				HttpResponse httpResponse = httpClient.execute(httpGet);

				HttpEntity httpEntity = httpResponse.getEntity();

				is = httpEntity.getContent();

			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(

					is, "utf-8"), 8);

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");

			}
			is.close();
			json = sb.toString();
			//AppUtility.SOUT("OUTPUT JSON ==  " + json);
		} catch (Exception e) {

			showLog("Buffer Error", "Error converting result " + e.toString());
			showLog("JSON Parser", "Error parsing data " + json);

		}

		// try parse the string to a JSON object

		try {

			jObj = new JSONObject(json);

		} catch (JSONException e) {

			showLog("JSON Parser", "Error parsing data " + e.toString());
			showLog("JSON Parser", "Error parsing data " + json);

		}

		// return JSON String

		return jObj;

	}

	// function get json from url

	// by making HTTP POST or GET mehtod

	public JSONObject makeHttpRequest(String url, String method, HashMap<String, String> textHashMap, HashMap<String, File> fileHashMap) {
		jObj = null;
		// Making HTTP request

		try {

			// check for request method

			if (method == "POST") {

				// request method is POST

				// defaultHttpClient

				DefaultHttpClient httpClient = new DefaultHttpClient();

				HttpPost httpPost = new HttpPost(url);

				MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
				entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

				if(textHashMap!=null)
				{
					for (Map.Entry<String,String> entry : textHashMap.entrySet())
					{
						String key = entry.getKey();
						String value = entry.getValue();
						entityBuilder.addTextBody(key,value);
						// doo stuff
					}
				}

				if(fileHashMap!=null)
				{
					for (Map.Entry<String,File> entry : fileHashMap.entrySet())
					{
						String key = entry.getKey();
						File value = entry.getValue();
						entityBuilder.addPart(key, new FileBody(value));
					}
				}


				HttpEntity entity =  entityBuilder.build();

				httpPost.setEntity(entity);

				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();

				is = httpEntity.getContent();

				 /* HttpParams httpParameters = new BasicHttpParams();
			       // Set the timeout in milliseconds until a connection is established.
			       // The default value is zero, that means the timeout is not used.
			       int timeoutConnection = 30000;
			       HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			       // Set the default socket timeout (SO_TIMEOUT)
			       // in milliseconds which is the timeout for waiting for data.
			       int timeoutSocket = 31000;
			       HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
				// defaultHttpClient
			    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

			    HttpPost httpPost = new HttpPost(url);

				httpPost.setEntity(new UrlEncodedFormEntity(params));

				HttpResponse httpResponse = httpClient.execute(httpPost);

				HttpEntity httpEntity = httpResponse.getEntity();

				is = httpEntity.getContent();*/

			} else if (method == "GET") {

				// request method is GET

				showLog("JSONParser", "GET method not supported in this method call");

			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(

					is, "utf-8"), 8);

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = reader.readLine()) != null) {
				//Log.e("JSON Parser response : ", line);
				sb.append(line + "\n");

			}
			is.close();
			json = sb.toString();
			System.out.println("Json Parser Output  ===   " + json);
			//AppUtility.SOUT("OUTPUT JSON ==  " + json);
		} catch (Exception e) {

			showLog("Buffer Error", "Error converting result " + e.toString());
			showLog("JSON Parser", "Error parsing data " + json);

		}

		// try parse the string to a JSON object

		try {

			jObj = new JSONObject(json);

		} catch (JSONException e) {

			showLog("JSON Parser", "Error parsing data " + e.toString());
			showLog("JSON Parser", "Error parsing data " + json);

		}

		// return JSON String

		return jObj;

	}

	public void postJSONData(String result,JSONObject obj) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);

		String json=obj.toString();

		try {

			HttpPost httppost = new HttpPost(result.toString());
			StringEntity se = new StringEntity(obj.toString());
			httppost.setHeader(HTTP.CONTENT_TYPE,"application/json" );
			//  se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httppost.setEntity(se);

			HttpResponse response = httpclient.execute(httppost);
			String temp = EntityUtils.toString(response.getEntity());
			showLog("tag", temp);


		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
	}

	public void showLog(String tag, String msg) {
			Log.e(tag, msg);
	}

}