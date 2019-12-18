package vn.edu.usth.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherForecastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherForecastFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        WeatherFragment weatherFragment = new WeatherFragment();
        weatherFragment.setArguments(this.getArguments());

        ForecastFragment forecastFragment = new ForecastFragment();
        forecastFragment.setArguments(this.getArguments());


        fragmentTransaction.replace(R.id.frame_weather_frag, weatherFragment).replace(R.id.frame_forecast_frag, forecastFragment);

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();

        LinearLayout fragment_weather = (LinearLayout) inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        return fragment_weather;
    }

}
