package app.now.com.cornell.NetworkCallBacks;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * @author Atif Afridi on 03-Feb-18.
 */

public interface ServiceCallInterface {

    void onServiceComplete(JSONObject json, String text);

    void onServiceError(VolleyError error, String text);
}
