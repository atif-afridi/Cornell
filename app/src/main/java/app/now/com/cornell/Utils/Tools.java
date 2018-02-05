package app.now.com.cornell.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * @author Atif Afridi on 03-Feb-18.
 */

public class Tools {

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // test for connection
            if (cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.d("Tools_isNetworkAvailable : [%s]", ex.toString());
        }
        return false;
    }


}
