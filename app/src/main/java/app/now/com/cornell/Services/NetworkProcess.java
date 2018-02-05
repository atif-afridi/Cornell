package app.now.com.cornell.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.now.com.cornell.NetworkCallBacks.ServiceCallInterface;
import app.now.com.cornell.Utils.Logger;
import app.now.com.cornell.Utils.Tools;

/**
 * @author Atif Afridi on 03-Feb-18.
 */

public class NetworkProcess {
    private ServiceCallInterface serviceListener;
    private Context context;
    private ProgressDialog dialog;
    private boolean showProgressDialog;
    private String token;

    public NetworkProcess(ServiceCallInterface serviceListener, Context context) {
        this(serviceListener, context, true);
    }

    public NetworkProcess(ServiceCallInterface serviceListener, Context context, boolean showProgressDialog) {
        this.serviceListener = serviceListener;
        this.context = context;
        this.showProgressDialog = showProgressDialog;
        if (context != null) {
            initProgressDialog(context);
        }
    }

    public NetworkProcess(ServiceCallInterface serviceListener, Context context, boolean showProgressDialog, String token) {
        this.serviceListener = serviceListener;
        this.context = context;
        this.showProgressDialog = showProgressDialog;
        this.token = token;

        if (context != null) {
            initProgressDialog(context);
        }
    }

    public void serviceProcessing(final JSONObject json, String url) throws JSONException {
        if (!Tools.isNetworkAvailable(context)) {
            return;
        }
        if (dialog != null && showProgressDialog) {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        final JsonObjectRequest requestCall = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(json.toString()),
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (dialog != null) {
                                if (dialog.isShowing())
                                    dialog.dismiss();
                            }
                            if (200 == Integer.valueOf(response.get("status").toString())) {
                                serviceListener.onServiceComplete(response, response.toString());
                            }

                        } catch (Exception ex) {
                            Logger.d("NetworkProcess_serviceProcessing : [%s]", ex.toString());
                        }
                    } // onResponse

                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    Activity activity = (Activity) context;
                    if (context != null && !activity.isFinishing()) {
                        if (dialog != null) {
                            if (dialog.isShowing())
                                dialog.dismiss();
                        }
                    }
                    String message = null;
                    if (volleyError instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (volleyError instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                    } else if (volleyError instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (volleyError instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                    } else if (volleyError instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (volleyError instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                    }
                    serviceListener.onServiceError(volleyError, message);
                } catch (Exception ee) {
                    Logger.d("NetworkProcess_onErrorResponse : [%s]", ee.toString());
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                String creds = String.format("%s:%s", "betatest", "j6vbV7EcnxCwheyM");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Content-Type", "application/json");
                headers.put("Host", "beta.talkhomeapp.com");
                headers.put("Authorization", auth);
                if (token != null) {
                    if (token.length() > 0) {
                        headers.put("Token", token);
                    }
                }

                return headers;
            }
        };
        requestCall.setRetryPolicy(new DefaultRetryPolicy(
                30 * 1000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestCall.setShouldCache(false);
        Volley.newRequestQueue(context).add(requestCall);
    }


    private void initProgressDialog(final Context context) {
        if (showProgressDialog) {
            dialog = ProgressDialog.show(context, "",
                    "Working. Please wait...", true);
        }
    }
}
