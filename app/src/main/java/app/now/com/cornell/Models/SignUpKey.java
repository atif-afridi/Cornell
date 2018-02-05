package app.now.com.cornell.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Atif Afridi on 04-Feb-18.
 */

public class SignUpKey {
    @SerializedName("signUp")
    UserRegister signup;

    public UserRegister getSignup() {
        return signup;
    }

    public void setSignup(UserRegister signup) {
        this.signup = signup;
    }
}
