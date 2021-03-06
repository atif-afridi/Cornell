package app.now.com.cornell.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.now.com.cornell.Models.LoginResponseModel;
import app.now.com.cornell.Models.TopUpResponseModel;
import app.now.com.cornell.NetworkCallBacks.ServiceCallInterface;
import app.now.com.cornell.R;
import app.now.com.cornell.Services.NetworkProcess;
import app.now.com.cornell.Utils.Constants;
import app.now.com.cornell.Utils.Logger;
import app.now.com.cornell.Utils.Tools;
import app.now.com.cornell.databinding.FragmentTopUpBinding;

public class TopUpFragment extends Fragment implements View.OnClickListener {

    private FragmentTopUpBinding binding;
    private boolean isValidetMsidin, isValidetEnterPin;
    private EditText etMsidin, etEnterPin;
    private LoginResponseModel model;
    private boolean callTopUp;

    private OnTopUpClicks mListener;

    public TopUpFragment() {
        // Required empty public constructor
    }

    public static TopUpFragment newInstance(LoginResponseModel expiry) {
        TopUpFragment fragment = new TopUpFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_AFTER_LOGIN, expiry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getParcelable(Constants.ARG_AFTER_LOGIN);
            if (model == null) return;
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String currentDateTime = simpleDate.format(new Date());

            if (model.getPayload().getAuthentication().getExpiryDate().compareTo(currentDateTime) > 0) {
                callTopUp = true;
            } else {
                callTopUp = false;
                Toast.makeText(getActivity(), "Token expired, Re-login again please.", Toast.LENGTH_SHORT).show();
                // show login again here
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_up, container, false);
        etMsidin = binding.etMsidin;
        etEnterPin = binding.etEnterPin;
        binding.btTopupBtn.setOnClickListener(this);
        binding.btLogoutBtn.setOnClickListener(this);

        return binding.getRoot();
    }

    private void makeTopUpRequest() throws JSONException {
        // fill the data here
        JSONObject topUpBody = new JSONObject();
        try {
            topUpBody.put("msisdn", etMsidin.getText().toString().trim());
            topUpBody.put("pin", etEnterPin.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkProcess networkProcess = new NetworkProcess(getTopUpService, getActivity(), true, model.getPayload().getAuthentication().getToken());
        networkProcess.serviceProcessing(topUpBody, Constants.API_SIGNUP);
    }

    ServiceCallInterface getTopUpService = new ServiceCallInterface() {
        @Override
        public void onServiceComplete(JSONObject json, String text) {
            // parse the response here
            if (json.has("status")) {
                try {
                    if (json.get("status").toString().equalsIgnoreCase("200")) {
                        Gson gson = new Gson();
                        TopUpResponseModel model = gson.fromJson(String.valueOf(json), TopUpResponseModel.class);
                        if (model == null) return;
                        if ("success".equalsIgnoreCase(model.getMessage().toString())) {
                            if (model.getPayload() != null) {
                                if (model.getPayload().getDisplay() != null) {
                                    Toast.makeText(getActivity(), model.getPayload().getDisplay(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "signUp null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), "unsuccessful response", Toast.LENGTH_SHORT).show();
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            if (etMsidin == null) return;
            if (etEnterPin == null) return;
            etMsidin.setError(null);
            etEnterPin.setError(null);
        }
    }

    private boolean checkValues() {
        // etMsidin validation check
        if (etMsidin.getText().toString().trim().length() > 0) {
            isValidetMsidin = true;
            etMsidin.setError(null);
        } else {
            isValidetMsidin = false;
            etMsidin.setError("Msidin cannot be empty");
        }
        // etEnterPin validation check
        if (etEnterPin.getText().toString().trim().length() > 0) {
            isValidetEnterPin = true;
            etEnterPin.setError(null);
        } else {
            isValidetEnterPin = false;
            etEnterPin.setError("Pin cannot be empty");
        }
        return isValidetMsidin && isValidetEnterPin;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_topupBtn:
                if (checkValues()) {
                    try {
                        if (callTopUp) {
                            makeTopUpRequest();
                        } else {
                            Toast.makeText(getActivity(), "No Session found, Re-login again please.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//                    Toast.makeText(getActivity(), "please recheck the fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_logoutBtn:
                //clear session
                SharedPreferences prefs = getActivity().getSharedPreferences(
                        Constants.SESSION_TOKEN, Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                Gson gsonLogin = new Gson();
                String jsonLogin = gsonLogin.toJson(model).toString();
                prefsEditor.putString(Constants.LOGIN_SESSION, null);
                prefsEditor.putBoolean(Constants.AUTHENTICATE, false);
                prefsEditor.commit();
                onButtonPressed();
                break;

        }
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onLogoutClicked();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTopUpClicks) {
            mListener = (OnTopUpClicks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnTopUpClicks {
        void onLogoutClicked();
    }
}
