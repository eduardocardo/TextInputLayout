package com.educar.textinputlayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Eduardo on 16/04/2016.
 */
public class MiFragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] =
            new String[]{"Sesión", "Listado"};

    public MiFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = null;

        switch (position) {
            case 0:
                f = FragmentSession.newInstance();
                break;
            case 1:
                f = FragmentList.newInstance();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return null;
    }
}
