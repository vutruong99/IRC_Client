package vn.edu.usth.weather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.app.FragmentActivity;
import android.widget.TableLayout;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;


public class WeatherActivity extends AppCompatActivity {

    MediaPlayer mp;
    Context context;
    RequestQueue requestQueue;

    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
// This method is executed in main thread
            String content = msg.getData().getString("server_response");
            Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
        }
    };

    private void networkRequest() {
        requestQueue = Volley.newRequestQueue(this);

        Response.Listener<Bitmap> listener =
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView iv = (ImageView) findViewById(R.id.main_weather);
                        iv.setImageBitmap(response);
                    }
                };

        ImageRequest imageRequest = new ImageRequest(
                "https://usth.edu.vn/uploads/logo_1_vi.png",
                listener, 0, 0, ImageView.ScaleType.CENTER,
                Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeatherActivity.this, " LMAOOOOOOOOOOOOOOO      " + error, Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(imageRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("onCreate","Weather activity is being created");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new HomeFragmentAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        writeExternal();

        mp = MediaPlayer.create(getApplicationContext(), R.raw.humble);
        mp.stop();
        //mp.start();

        new GetRequestImage().execute("https://i.pinimg.com/474x/f3/d5/d5/f3d5d5cc4d8ea544d8a4658e30f7c715.jpg");
    }


    //Labwork 12
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.refresh:
                networkRequest();
                //AsyncTaskRunner runner = new AsyncTaskRunner();
                //runner.execute("5000");
                return true;
            case R.id.settings:
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private class GetRequestImage extends AsyncTask<String, Integer, Bitmap> {
        private String resp;
        ProgressDialog progressDialog;
        Bitmap bitmap;

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(Bitmap bitmap) {
            // execution of result of Long time consuming operation
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.forecast_fragment);
            Drawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            linearLayout.setBackgroundDrawable(bitmapDrawable);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(WeatherActivity.this,
                    "Updating weather...",
                    "Wait for 5 seconds!");
        }


        protected void onProgressUpdate(String... text) {
            // Do something here
        }
    }

    //labwork 11
    private void writeExternal() {
        String filename = "humble.mp3";
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/";

        try {
            InputStream is = getApplicationContext().getResources().openRawResource(R.raw.humble);
            File myFile = new File(filepath + filename);
            myFile.createNewFile();
            OutputStream fout = new FileOutputStream(myFile);
            fout.write(is.read());
            fout.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart","Weather activity is starting");
    }

    protected void onResume() {
        super.onResume();
        Log.i("onResume","Weather activity is resuming");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause","Weather activity is pausing");
        mp.stop();
        mp.release();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop","Weather activity is stopping");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy","Weather activity is being destroyed");
    }
}
