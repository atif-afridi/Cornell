package app.now.com.cornell.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.now.com.cornell.Activities.MainActivity;
import app.now.com.cornell.Models.ResponseModel;
import app.now.com.cornell.Models.SignupModel;
import app.now.com.cornell.NetworkCallBacks.ServiceCallInterface;
import app.now.com.cornell.R;
import app.now.com.cornell.Services.NetworkProcess;
import app.now.com.cornell.Utils.Constants;
import app.now.com.cornell.Utils.Tools;
import app.now.com.cornell.databinding.ActivitySignUpBinding;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private TextView txtdob;
    private EditText email, password, firstname, lastname, contactnumber, mobilenumber, addressLine1Home, cityHome, postCodeHome, countryHome, addressLine1Shipping, cityShipping, postCodeShipping, countryShipping, addressLine1Billing, cityBilling, postCodeBilling, countryBilling;
    private boolean isValidemail, isValidpassword, isValidfirstname, isValidlastname, isValidcontactnumber, isValidmobilenumber, isValidaddressLine1Home, isValidcityHome, isValidpostCodeHome, isValidcountryHome, isValidcityShipping, isValidpostCodeShipping, isValidcountryShipping, isValidcityBilling, isValidpostCodeBilling, isValidcountryBilling;
    private EditText addressLine2Shipping, countyShipping, addressLine2Billing, countyBilling, addressLine2Home, countyHome;
    private Spinner titleDropDown, newsletterDropdown;
    private LinearLayout dateWrapper;
    private Button signUpBtn;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ArrayAdapter<String> titleAdapter, newsletterAdapter;
    private SignupModel signupModel;
    private JSONObject userInfoJson;
    private SignUpCompleteListner signUpCompleteListner;
    private NestedScrollView scrollView;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActivitySignUpBinding rootView = DataBindingUtil.inflate(inflater, R.layout.activity_sign_up, container, false);
        setViews(rootView);
        setCallBacks();
        /**code here**/
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Resources res = getResources();
        // set title spinner
        setTitleSpinner(res);
        // set news letter spinner
        setNewsLetterSpinner(res);
        // hide the keypad user friendly flow
        rootView.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Tools.hideKeyBoard(getActivity(), v);
                return false;
            }
        });
        return rootView.getRoot();
    }

    private void setNewsLetterSpinner(Resources res) {
        newsletterAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.newsletter_arrays));
        newsletterDropdown.setAdapter(newsletterAdapter);
        newsletterDropdown.setSelection(0);
    }

    private void setTitleSpinner(Resources res) {
        titleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, res.getStringArray(R.array.title_arrays));
        titleDropDown.setAdapter(titleAdapter);
        titleDropDown.setSelection(0);
    }


    private void fillSignupModel() {

        SignupModel.UserInfoModel userInfoModel = new SignupModel.UserInfoModel();
        userInfoModel.setEmail("go.testing@gmail.com");
        userInfoModel.setPassword("gopassword");
        userInfoModel.setFirstName("Go");
        userInfoModel.setLastName("Testing");
        userInfoModel.setDateOfBirth("");
        userInfoModel.setContactNo("");
        userInfoModel.setMobileNo("");
        userInfoModel.setSubscribedToNewsletter(false);

        SignupModel.HomeAddressModel homeAddressModel = new SignupModel.HomeAddressModel();
        homeAddressModel.setAddressL1("");
        homeAddressModel.setCountry("");
        homeAddressModel.setCity("");
        homeAddressModel.setPostCode("");
        homeAddressModel.setAddressL2("");
        homeAddressModel.setCounty("");

        SignupModel.ShippingAddressModel shippingAddressModel = new SignupModel.ShippingAddressModel();
        shippingAddressModel.setAddressL1("");
        shippingAddressModel.setCountry("");
        shippingAddressModel.setCity("");
        shippingAddressModel.setPostCode("");
        shippingAddressModel.setAddressL2("");
        shippingAddressModel.setCounty("");

        SignupModel.BillingAddressModel billingAddressModel = new SignupModel.BillingAddressModel();
        shippingAddressModel.setAddressL1("");
        shippingAddressModel.setCountry("");
        shippingAddressModel.setCity("");
        shippingAddressModel.setPostCode("");
        shippingAddressModel.setAddressL2("");
        shippingAddressModel.setCounty("");

        signupModel = new SignupModel();
        signupModel.setUserInfoModel(userInfoModel);
        signupModel.setHomeAddressModel(homeAddressModel);
        signupModel.setShippingAddressModel(shippingAddressModel);
        signupModel.setBillingAddressModel(billingAddressModel);


    }

    private void userInfoData() {
        userInfoJson = new JSONObject();
        try {
            userInfoJson.put("Email", email.getText().toString().trim());
            userInfoJson.put("Password", password.getText().toString().trim());
            userInfoJson.put("Title", titleDropDown.getSelectedItem().toString().trim());
            userInfoJson.put("FirstName", firstname.getText().toString().trim());
            userInfoJson.put("LastName", lastname.getText().toString().trim());
            userInfoJson.put("DateOfBirth", "");
            userInfoJson.put("ContactNo", contactnumber.getText().toString().trim());
            userInfoJson.put("MobileNo", mobilenumber.getText().toString().trim());
            userInfoJson.put("IsSubscribedToNewsletter", false);

            JSONObject homeAddress = new JSONObject();
            homeAddress.put("AddressL1", addressLine1Home.getText().toString().trim());
            homeAddress.put("Country", countryHome.getText().toString().trim());
            homeAddress.put("City", cityHome.getText().toString().trim());
            homeAddress.put("PostCode", postCodeHome.getText().toString().trim());
            homeAddress.put("AddressL2", addressLine2Home.getText().toString().trim());
            homeAddress.put("County", countyHome.getText().toString().trim());
            JSONObject shippingAddress = new JSONObject();
            if (addressLine1Shipping.getText().toString().trim().length() > 0) {
                shippingAddress.put("AddressL1", addressLine1Shipping.getText().toString().trim());
            } else {
                shippingAddress.put("AddressL1", addressLine1Home.getText().toString().trim());
            }
            shippingAddress.put("AddressL1", addressLine1Home.getText().toString().trim());
            shippingAddress.put("Country", countryShipping.getText().toString().trim());
            shippingAddress.put("City", cityShipping.getText().toString().trim());
            shippingAddress.put("PostCode", postCodeShipping.getText().toString().trim());
            shippingAddress.put("AddressL2", addressLine2Shipping.getText().toString().trim());
            shippingAddress.put("County", countyShipping.getText().toString().trim());
            JSONObject billingAddress = new JSONObject();
            if (addressLine1Billing.getText().toString().trim().length() > 0) {
                billingAddress.put("AddressL1", addressLine1Billing.getText().toString().trim());
            } else {
                billingAddress.put("AddressL1", addressLine1Home.getText().toString().trim());
            }
            billingAddress.put("AddressL1", addressLine1Home.getText().toString().trim());
            billingAddress.put("Country", countryBilling.getText().toString().trim());
            billingAddress.put("City", cityBilling.getText().toString().trim());
            billingAddress.put("PostCode", postCodeBilling.getText().toString().trim());
            billingAddress.put("AddressL2", addressLine2Billing.getText().toString().trim());
            billingAddress.put("County", countyBilling.getText().toString().trim());

            userInfoJson.put("HomeAddress", homeAddress);
            userInfoJson.put("ShippingAddress", shippingAddress);
            userInfoJson.put("BillingAddress", billingAddress);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setViews(ActivitySignUpBinding rootView) {
        email = rootView.email;
        password = rootView.password;
        firstname = rootView.firstname;
        lastname = rootView.lastname;
        contactnumber = rootView.contactnumber;
        mobilenumber = rootView.mobilenumber;
        addressLine1Home = rootView.addressLine1Home;
        addressLine2Home = rootView.addressLine2Home;
        cityHome = rootView.cityHome;
        countyHome = rootView.countyHome;
        postCodeHome = rootView.postCodeHome;
        countryHome = rootView.countryHome;
        addressLine1Shipping = rootView.addressLine1Shipping;
        addressLine2Shipping = rootView.addressLine2Shipping;
        cityShipping = rootView.cityShipping;
        countyShipping = rootView.countyShipping;
        postCodeShipping = rootView.postCodeShipping;
        countryShipping = rootView.countryShipping;
        addressLine1Billing = rootView.addressLine1Billing;
        addressLine2Billing = rootView.addressLine2Billing;
        cityBilling = rootView.cityBilling;
        countyBilling = rootView.countyBilling;
        postCodeBilling = rootView.postCodeBilling;
        countryBilling = rootView.countryBilling;
        signUpBtn = rootView.signUpBtn;
        titleDropDown = rootView.titleDropdown;
        newsletterDropdown = rootView.newsletterDropdown;
        txtdob = (TextView) rootView.dobTV;
        dateWrapper = rootView.dateWrapper;
        scrollView = rootView.scrollView;
        setDateField();
    }

    private void setCallBacks() {
        dateWrapper.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    private void makeSignupRequest() throws JSONException {
        // fill the data here
        userInfoData();
        NetworkProcess networkProcess = new NetworkProcess(getSignupService, getActivity(), true);
        networkProcess.serviceProcessing(userInfoJson, Constants.API_SIGNUP);
    }

    public void setDateField() {
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getActivity(), onDateChanged()
                , newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
    }

    @NonNull
    private DatePickerDialog.OnDateSetListener onDateChanged() {
        return new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                String date = dateFormatter.format(newDate.getTime());
                txtdob.setText(date);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_wrapper:
                datePickerDialog.show();
                break;
            case R.id.signUpBtn:
                if (!Tools.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "No internet found!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (checkValues()) {
                        try {
                            makeSignupRequest();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_UP);
                            }
                        });
                    }
                }
                break;
        }
    }


    private boolean checkValues() {
        // email validation check
        if (email.getText().toString().trim().length() > 0) {
            if (Tools.isEmailValid(email.getText().toString())) {
                isValidemail = true;
                email.setError(null);
            } else {
                isValidemail = false;
                email.setError("Enter valid email please");
            }
        } else {
            isValidemail = false;
            email.setError("Email cannot be empty");
        }
        // password validation check
        if (password.getText().toString().trim().length() > 0) {
            if (Tools.isPasswordValid(password.getText().toString())) {
                isValidpassword = true;
            } else {
                isValidpassword = false;
                password.setError("Enter valid password e.g = Aa1@#$%^&+= ");
            }
        } else {
            isValidpassword = false;
            password.setError("Password cannot be empty");
        }
        // firstname validation check
        if (firstname.getText().toString().trim().length() > 0) {
            isValidfirstname = true;
        } else {
            isValidfirstname = false;
            firstname.setError("Firstname cannot be empty");
        }
        // lastname validation check
        if (lastname.getText().toString().trim().length() > 0) {
            isValidlastname = true;
        } else {
            isValidlastname = false;
            lastname.setError("Lastname cannot be empty");
        }
        // contactnumber validation check
        if (contactnumber.getText().toString().trim().length() > 0) {
            isValidcontactnumber = true;
        } else {
            isValidcontactnumber = false;
            contactnumber.setError("Contact number cannot be empty");
        }
        // mobilenumber validation check
        if (mobilenumber.getText().toString().trim().length() > 0) {
            isValidmobilenumber = true;
        } else {
            isValidmobilenumber = false;
            mobilenumber.setError("Mobile number cannot be empty");
        }
        // addressLine1Home validation check
        if (addressLine1Home.getText().toString().trim().length() > 0) {
            isValidaddressLine1Home = true;
        } else {
            isValidaddressLine1Home = false;
            addressLine1Home.setError("Address Line1 Home cannot be empty");
        }// cityHome validation check
        if (cityHome.getText().toString().trim().length() > 0) {
            isValidcityHome = true;
        } else {
            isValidcityHome = false;
            cityHome.setError("City Home cannot be empty");
        }// postCodeHome validation check
        if (postCodeHome.getText().toString().trim().length() > 0) {
            isValidpostCodeHome = true;
        } else {
            isValidpostCodeHome = false;
            postCodeHome.setError("Post Code Home cannot be empty");
        }// countryHome validation check
        if (countryHome.getText().toString().trim().length() > 0) {
            isValidcountryHome = true;
        } else {
            isValidcountryHome = false;
            countryHome.setError("Country Home cannot be empty");
        }// addressLine1Shipping validation check
        // cityShipping validation check
        if (cityShipping.getText().toString().trim().length() > 0) {
            isValidcityShipping = true;
        } else {
            isValidcityShipping = false;
            cityShipping.setError("City Shipping cannot be empty");
        }// postCodeShipping validation check
        if (postCodeShipping.getText().toString().trim().length() > 0) {
            isValidpostCodeShipping = true;
        } else {
            isValidpostCodeShipping = false;
            postCodeShipping.setError("Post Code Shipping cannot be empty");
        }// countryShipping validation check
        if (countryShipping.getText().toString().trim().length() > 0) {
            isValidcountryShipping = true;
        } else {
            isValidcountryShipping = false;
            countryShipping.setError("Country Shipping cannot be empty");
        }// addressLine1Billing validation check
//        if (addressLine1Billing.getText().toString().trim().length() > 0) {
//            isValidaddressLine1Billing = true;
//        } else {
//            isValidaddressLine1Billing = false;
//            addressLine1Billing.setError("Address Line1 Billing cannot be empty");
//        }// cityBilling validation check
        if (cityBilling.getText().toString().trim().length() > 0) {
            isValidcityBilling = true;
        } else {
            isValidcityBilling = false;
            cityBilling.setError("City Billing cannot be empty");
        }// postCodeBilling validation check
        if (postCodeBilling.getText().toString().trim().length() > 0) {
            isValidpostCodeBilling = true;
            postCodeBilling.setError(null);
        } else {
            isValidpostCodeBilling = false;
            postCodeBilling.setError("Post Code Billing cannot be empty");
        }// countryBilling validation check
        if (countryBilling.getText().toString().trim().length() > 0) {
            isValidcountryBilling = true;
            countryBilling.setError(null);
        } else {
            isValidcountryBilling = false;
            countryBilling.setError("Country Billing cannot be empty");
        }
        return isValidemail && isValidpassword && isValidfirstname && isValidlastname && isValidcontactnumber && isValidmobilenumber && isValidaddressLine1Home && isValidcityHome && isValidpostCodeHome && isValidcountryHome && isValidcityShipping && isValidpostCodeShipping && isValidcountryShipping && isValidcityBilling && isValidpostCodeBilling && isValidcountryBilling;
    }

    ServiceCallInterface getSignupService = new ServiceCallInterface() {
        @Override
        public void onServiceComplete(JSONObject json, String text) {
            // parse the response here
            if (json.has("status")) {
                try {
                    if (json.get("status").toString().equalsIgnoreCase("200")) {
                        Gson gson = new Gson();
                        ResponseModel model = gson.fromJson(String.valueOf(json), ResponseModel.class);
                        if (model == null) return;
                        if ("success".equalsIgnoreCase(model.getMessage().toString())) {
                            if (model.getPayload() != null) {
                                if (model.getPayload().getSignup() != null) {
                                    if (model.getPayload().getSignup().isRegistered()) {
                                        // open the home screen
                                        Toast.makeText(getActivity(), "SignUp complete Login to Continue", Toast.LENGTH_SHORT).show();
                                        signUpCompleteListner.showLoginScreen();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "signUp null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), model.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceError(VolleyError error, String text) {
            if (text.length() > 0) {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getActivity(), "Pleasr try again later!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            signUpCompleteListner = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignUpCompleteListner");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signUpCompleteListner = null;
    }

    public interface SignUpCompleteListner {
        void showLoginScreen();
    }
}
