package com.dk.mensajero.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Clase que permite hacer las operaciones de un cliente REST.
 */
public class RestClient {
    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final int BUFFER_SIZE = (1024*1024)*2;
    HttpParams httpRequestParams;
    private ArrayList<NameValuePair> params;
    private ArrayList<NameValuePair> headers;
    private String url;
    private int responseCode;
    private String message;
    private String response;

    public String getResponse() {
        return response;
    }

    public String getErrorMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public RestClient(String url) {
        this.url = url;
        params = new ArrayList<NameValuePair>();
        headers = new ArrayList<NameValuePair>();
        httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSocketBufferSize(httpRequestParams,BUFFER_SIZE);
    }

    public void addParam(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }

    public void addHeader(String name, String value) {
        headers.add(new BasicNameValuePair(name, value));
    }

    public void execute(RequestMethod method) throws Exception {
        switch (method) {
            case GET: {
                //add parameters
                String allParams = "";
                if (!params.isEmpty()) {
                    allParams += "?";
                    for (NameValuePair param : params) {
                        String paramString = param.getName() + "=" + URLEncoder.encode(param.getValue(), "UTF-8");
                        if (allParams.length() > 1) {
                            allParams += "&" + paramString;
                        } else {
                            allParams += paramString;
                        }
                    }
                }

                HttpGet request = new HttpGet(url + allParams);

                //add headers
                for (NameValuePair header : headers) {
                    request.addHeader(header.getName(), header.getValue());
                }

                executeRequest(request, url);
                break;
            }
            case POST: {
                HttpPost request = new HttpPost(url);

                //add headers
                for (NameValuePair h : headers) {
                    request.addHeader(h.getName(), h.getValue());
                }

                if (!params.isEmpty()) {
                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                }

                executeRequest(request, url);
                break;
            }
            case PUT: {
                HttpPut request = new HttpPut(url);

                //add headers
                for (NameValuePair h : headers) {
                    request.addHeader(h.getName(), h.getValue());
                }

                if (!params.isEmpty()) {
                    request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                }

                executeRequest(request, url);
                break;
            }
            case DELETE: {
                //add parameters
                String allParams = "";
                if (!params.isEmpty()) {
                    allParams += "?";
                    for (NameValuePair param : params) {
                        String paramString = param.getName() + "=" + URLEncoder.encode(param.getValue(), "UTF-8");
                        if (allParams.length() > 1) {
                            allParams += "&" + paramString;
                        } else {
                            allParams += paramString;
                        }
                    }
                }

                HttpDelete request = new HttpDelete(url + allParams);

                //add headers
                for (NameValuePair header : headers) {
                    request.addHeader(header.getName(), header.getValue());
                }

                executeRequest(request, url);
                break;
            }
        }
    }

    private void executeRequest(HttpUriRequest request, String url) {
        HttpClient client = new DefaultHttpClient(httpRequestParams);
        client.getParams().setParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, BUFFER_SIZE);
        HttpResponse httpResponse;

        try {
            httpResponse = client.execute(request);
            responseCode = httpResponse.getStatusLine().getStatusCode();
            message = httpResponse.getStatusLine().getReasonPhrase();

            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {

                InputStream instream = entity.getContent();
                response = convertStreamToString(instream);

                // Closing the input stream will trigger connection release
                instream.close();
            }

        } catch (IOException e) {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
    }

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}