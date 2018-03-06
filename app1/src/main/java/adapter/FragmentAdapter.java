package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.example.administrator.myyushi.Constant;

import java.util.List;

/**
 * Created by misshu on 2017/4/6/006.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    public  Fragment tabFrames[];
    public  String titles[];

    public FragmentAdapter(FragmentManager fm, String[] titles, Fragment[] tabFrames) {
        super(fm);
        this.titles = titles;
        this.tabFrames = tabFrames;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFrames[position];

    }

    @Override
    public int getCount() {

        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }



}
