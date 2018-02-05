package app.now.com.cornell.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.now.com.cornell.Activities.MainActivity;
import app.now.com.cornell.Models.LoginResponseModel;
import app.now.com.cornell.NetworkCallBacks.ServiceCallInterface;
import app.now.com.cornell.R;
import app.now.com.cornell.Services.NetworkProcess;
import app.now.com.cornell.Utils.Constants;
import app.now.com.cornell.Utils.Tools;
import app.now.com.cornell.databinding.AuthFragmentBinding;

/**
 * Created by Atif Afridi on 04-Feb-18.
 */

public class AuthFragment extends Fragment implements View.OnClickListener, Validator.ValidationListener {

    private OnAuthButtonClick onAuthButtonClick;
    private AuthFragmentBinding binding;
    @NotEmpty
    @Email
    private EditText emailLogin;
    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC)
    private EditText passwordLogin;
    private Validator validator;

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.auth_fragment, container, false);
        validator = new Validator(this);
        validator.setValidationListener(this);
        binding.loginBtn.setOnClickListener(this);
        binding.signUp.setOnClickListener(this);
        passwordLogin = binding.passwordLogin;
        emailLogin = binding.emailLogin;

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (!Tools.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "No internet found!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    validator.validate();
                }
                break;
            case R.id.sign_up:
                if (!Tools.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "No internet found!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    onAuthButtonClick.signUpClicked();
                }
                break;
        }
    }

    @Override
    public void onValidationSucceeded() {

        NetworkProcess loginProcess = new NetworkProcess(getServiceListener(), getActivity());
        JSONObject loginBody = new JSONObject();
        try {
            loginBody.put("email", emailLogin.getText().toString().trim());
            loginBody.put("Password", passwordLogin.getText().toString().trim());
            loginBody.put("IPAddress", "192.0.2.0");
            loginProcess.serviceProcessing(loginBody, Constants.API_LOGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @NonNull
    private ServiceCallInterface getServiceListener() {
        return new ServiceCallInterface() {
            @Override
            public void onServiceComplete(JSONObject json, String text) {

                try {
                    if (json.has("status")) {
                        if (json.get("status").toString().equalsIgnoreCase("200")) {
                            // parse the response here
                            Gson gson = new Gson();
                            LoginResponseModel model = gson.fromJson(String.valueOf(json), LoginResponseModel.class);
                            if (model == null) return;
                            boolean isAuthenticated = model.getPayload().getAuthentication().isAuthenticated();
                            if (isAuthenticated) {
                                // make session
                                SharedPreferences prefs = getActivity().getSharedPreferences(
                                        Constants.SESSION_TOKEN, Context.MODE_PRIVATE);
                                SharedPreferences.Editor prefsEditor = prefs.edit();
                                Gson gsonLogin = new Gson();
                                String jsonLogin = gsonLogin.toJson(model).toString();
                                prefsEditor.putString(Constants.LOGIN_SESSION, jsonLogin);
                                prefsEditor.putBoolean(Constants.AUTHENTICATE, isAuthenticated);
                                prefsEditor.commit();

                                onAuthButtonClick.logInClicked(model);
                            } else {
                                // TODO here
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceError(VolleyError error, String text) {

            }
        };
    }


    public interface OnAuthButtonClick {
        void signUpClicked();

        void logInClicked(LoginResponseModel model);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            onAuthButtonClick = (MainActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAuthButtonClick");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onAuthButtonClick = null;
    }
}
