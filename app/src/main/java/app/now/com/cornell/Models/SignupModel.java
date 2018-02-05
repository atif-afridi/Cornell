package app.now.com.cornell.Models;

import com.google.gson.annotations.SerializedName;

/**
 * @author Atif Afridi on 03-Feb-18.
 */

public class SignupModel {

    private UserInfoModel userInfoModel;
    private ShippingAddressModel shippingAddressModel;
    private BillingAddressModel billingAddressModel;
    @SerializedName("HomeAddress")
    private HomeAddressModel homeAddressModel;

    public static class UserInfoModel {
        public UserInfoModel() {
        }

        @SerializedName("Email")
        String email;
        @SerializedName("Password")
        String password;
        @SerializedName("Title")
        String title;
        @SerializedName("FirstName")
        String firstName;
        @SerializedName("LastName")
        String lastName;
        @SerializedName("DateOfBirth")
        String dateOfBirth;
        @SerializedName("ContactNo")
        String contactNo;
        @SerializedName("MobileNo")
        String mobileNo;
        @SerializedName("IsSubscribedToNewsletter")
        boolean isSubscribedToNewsletter;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getContactNo() {
            return contactNo;
        }

        public void setContactNo(String contactNo) {
            this.contactNo = contactNo;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public boolean isSubscribedToNewsletter() {
            return isSubscribedToNewsletter;
        }

        public void setSubscribedToNewsletter(boolean subscribedToNewsletter) {
            isSubscribedToNewsletter = subscribedToNewsletter;
        }





    }
    public static class HomeAddressModel {
        @SerializedName("AddressL1")
        String addressL1;
        @SerializedName("Country")
        String country;
        @SerializedName("City")
        String city;
        @SerializedName("PostCode")
        String postCode;
        @SerializedName("AddressL2")
        String addressL2;
        @SerializedName("County")
        String county;

        public String getAddressL1() {
            return addressL1;
        }

        public void setAddressL1(String addressL1) {
            this.addressL1 = addressL1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddressL2() {
            return addressL2;
        }

        public void setAddressL2(String addressL2) {
            this.addressL2 = addressL2;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }
    }

    public static class ShippingAddressModel {
        @SerializedName("AddressL1")
        String addressL1;
        @SerializedName("Country")
        String country;
        @SerializedName("City")
        String city;
        @SerializedName("PostCode")
        String postCode;
        @SerializedName("AddressL2")
        String addressL2;
        @SerializedName("County")
        String county;

        public ShippingAddressModel() {
        }

        public String getAddressL1() {
            return addressL1;
        }

        public void setAddressL1(String addressL1) {
            this.addressL1 = addressL1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddressL2() {
            return addressL2;
        }

        public void setAddressL2(String addressL2) {
            this.addressL2 = addressL2;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }
    }

    public static class BillingAddressModel {
        @SerializedName("AddressL1")
        String addressL1;
        @SerializedName("Country")
        String country;
        @SerializedName("City")
        String city;
        @SerializedName("PostCode")
        String postCode;
        @SerializedName("AddressL2")
        String addressL2;
        @SerializedName("County")
        String county;

        public BillingAddressModel() {
        }

        public String getAddressL1() {
            return addressL1;
        }

        public void setAddressL1(String addressL1) {
            this.addressL1 = addressL1;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddressL2() {
            return addressL2;
        }

        public void setAddressL2(String addressL2) {
            this.addressL2 = addressL2;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }
    }

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public ShippingAddressModel getShippingAddressModel() {
        return shippingAddressModel;
    }

    public void setShippingAddressModel(ShippingAddressModel shippingAddressModel) {
        this.shippingAddressModel = shippingAddressModel;
    }

    public BillingAddressModel getBillingAddressModel() {
        return billingAddressModel;
    }

    public void setBillingAddressModel(BillingAddressModel billingAddressModel) {
        this.billingAddressModel = billingAddressModel;
    }

    public HomeAddressModel getHomeAddressModel() {
        return homeAddressModel;
    }

    public void setHomeAddressModel(HomeAddressModel homeAddressModel) {
        this.homeAddressModel = homeAddressModel;
    }
}
