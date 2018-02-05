package app.now.com.cornell.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Atif Afridi on 04-Feb-18.
 */

public class LoginResponseModel implements Parcelable {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("payload")
    AuthenticationKey payload;
    @SerializedName("errorCode")
    String errorCode;

    public LoginResponseModel() {

    }

    protected LoginResponseModel(Parcel in) {
        status = in.readString();
        message = in.readString();
        errorCode = in.readString();
    }

    public static final Creator<LoginResponseModel> CREATOR = new Creator<LoginResponseModel>() {
        @Override
        public LoginResponseModel createFromParcel(Parcel in) {
            return new LoginResponseModel(in);
        }

        @Override
        public LoginResponseModel[] newArray(int size) {
            return new LoginResponseModel[size];
        }
    };

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

    public AuthenticationKey getPayload() {
        return payload;
    }

    public void setPayload(AuthenticationKey payload) {
        this.payload = payload;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(message);
        dest.writeString(errorCode);
    }
}
