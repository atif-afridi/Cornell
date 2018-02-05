package app.now.com.cornell.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import app.now.com.cornell.Fragments.AuthFragment;
import app.now.com.cornell.Fragments.HomeFragment;
import app.now.com.cornell.Fragments.SignUpFragment;
import app.now.com.cornell.Fragments.TopUpFragment;
import app.now.com.cornell.Models.LoginResponseModel;
import app.now.com.cornell.R;
import app.now.com.cornell.Utils.Constants;
import app.now.com.cornell.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AuthFragment.OnAuthButtonClick
        , SignUpFragment.SignUpCompleteListner,TopUpFragment.OnTopUpClicks {
    private TextView toolbarHeader;
    private ActivityMainBinding binding;
    private String json;
    private boolean isAuthenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        /**setting screens**/
        setViews();
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        }
//        fragmentTransection(HomeFragment.newInstance(null), "signup");
//        toolbarHeader.setText(R.string.home);


//        loadEntryScreen();


        Gson gson = new Gson();
        SharedPreferences prefs = getSharedPreferences(Constants.SESSION_TOKEN, MODE_PRIVATE);
        json = prefs.getString(Constants.LOGIN_SESSION, "");
        isAuthenticated = prefs.getBoolean(Constants.AUTHENTICATE, false);

        LoginResponseModel loginResponseModel = gson.fromJson(json, LoginResponseModel.class);

        if (isAuthenticated) {
            loadHomeScreen(loginResponseModel);
        } else {
            loadEntryScreen();
        }
    }

    private void loadHomeScreen(LoginResponseModel mModel) {
        toolbarHeader.setText(R.string.home);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, HomeFragment.newInstance(mModel))
                .commit();

    }

    private void loadEntryScreen() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, AuthFragment.newInstance())
                .commit();
        toolbarHeader.setText(R.string.corenell_distributors);
    }

    private void setViews() {
        toolbarHeader = binding.toolbarHeaderTitle;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        loadEntryScreen();
    }

    private void fragmentTransection(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void signUpClicked() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        fragmentTransection(SignUpFragment.newInstance(), "signup");
        toolbarHeader.setText(R.string.signup);
    }

    @Override
    public void logInClicked(LoginResponseModel model) {
        // TODO start from here
        toolbarHeader.setText(R.string.home);
        fragmentTransection(HomeFragment.newInstance(model), "homefragment");
    }


    @Override
    public void showLoginScreen() {
        onBackPressed();
    }

    @Override
    public void onLogoutClicked() {
        loadEntryScreen();
    }
}
