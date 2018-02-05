package app.now.com.cornell.Utils;

import android.util.Log;

/**
 * @author Atif Afridi on 03-Feb-18.
 */

public class Logger {
    private static final String TAG = "Cornell";
    private Logger() {
    }

    public static void d(String format, Object... args) {
        try {
            Log.d(TAG, String.format(format, args));
        } catch (Exception ignored) {
            ignored.printStackTrace();
            Logger.d("EXCEPTION_IGNORED : [%s]", ignored.toString());
        }
    } // log
}
