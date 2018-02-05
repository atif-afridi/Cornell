package app.now.com.cornell.Fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.now.com.cornell.Adapters.PagerHome;
import app.now.com.cornell.Models.LoginResponseModel;
import app.now.com.cornell.R;
import app.now.com.cornell.Utils.Constants;
import app.now.com.cornell.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private LoginResponseModel model;
    private FragmentHomeBinding binding;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(LoginResponseModel mModel) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.ARG_AFTER_LOGIN, mModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        if (getArguments() != null) {
            model = getArguments().getParcelable(Constants.ARG_AFTER_LOGIN);
        }
        tabLayout = binding.homeTabs;
        viewPager = binding.homeViewpager;
        //Adding the tabs using addTab() method
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Creating our pager adapter
        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.topup));
        titles.add(getString(R.string.history));
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TopUpFragment.newInstance(model));
        fragments.add(new HistoryFragment());
        PagerHome adapter = new PagerHome(getActivity().getSupportFragmentManager(), fragments,titles);
        //Adding adapter to pager
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return binding.getRoot();
    }


}
