package app.now.com.cornell.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Atif Afridi on 04-Feb-18.
 */

public class UserRegister {
    @SerializedName("isRegistered")
    boolean isRegistered;

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }
}
