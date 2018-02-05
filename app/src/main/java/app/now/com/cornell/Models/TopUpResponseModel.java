package app.now.com.cornell.Models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Atif Afridi on 05-Feb-18.
 */

public class TopUpResponseModel {


    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("payload")
    DisplayTop payload;
    @SerializedName("errorCode")
    String errorCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DisplayTop getPayload() {
        return payload;
    }

    public void setPayload(DisplayTop payload) {
        this.payload = payload;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public class DisplayTop {
        @SerializedName("display")
        String display;

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }

}
