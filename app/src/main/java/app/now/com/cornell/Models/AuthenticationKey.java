package app.now.com.cornell.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Atif Afridi on 04-Feb-18.
 */

public class AuthenticationKey {

    @SerializedName("authentication")
    AuthData authentication;

    public AuthData getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthData authentication) {
        this.authentication = authentication;
    }

    public class AuthData {
        @SerializedName("isAuthenticated")
        boolean isAuthenticated;
        @SerializedName("token")
        String token;
        @SerializedName("expiryDate")
        String expiryDate;

        public boolean isAuthenticated() {
            return isAuthenticated;
        }

        public void setAuthenticated(boolean authenticated) {
            isAuthenticated = authenticated;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }
    }


}
