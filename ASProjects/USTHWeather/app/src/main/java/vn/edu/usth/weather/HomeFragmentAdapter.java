package vn.edu.usth.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    int numberOfTabs = 3;

    private String titles[] = new String[] {"Hanoi","Stockholm","Moscow"};

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                WeatherForecastFragment wff = new WeatherForecastFragment();
                return wff;
            case 1:
                WeatherForecastFragment wff2 = new WeatherForecastFragment();
                return wff2;
            case 2:
                WeatherForecastFragment wff3 = new WeatherForecastFragment();
                return wff3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return titles[position];
    }
}
