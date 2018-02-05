package app.now.com.cornell.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import app.now.com.cornell.Fragments.HistoryFragment;
import app.now.com.cornell.Fragments.TopUpFragment;

/**
 * Created by Atif Afridi on 04-Feb-18.
 */

public class PagerHome extends FragmentStatePagerAdapter {

    List<Fragment> fragments;
    List<String> titles;

    public PagerHome(FragmentManager fm, List<Fragment> mFrag, List<String> mTitles) {
        super(fm);
        this.fragments = mFrag;
        this.titles = mTitles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}