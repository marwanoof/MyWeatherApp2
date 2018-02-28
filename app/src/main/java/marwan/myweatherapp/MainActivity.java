package marwan.myweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private TextView dgr;
    private EditText savecity;
    private TextView hum;
    private Button btn11,btn22,btn33;

    String DEGREE  = "\u00b0";

    private static String forecastDaysNum = "3";
    //private ViewPager pager;
    String city = "Muscat,OM";
    int clickcount=0;
    String cityBtn="city1";
    City cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String lang = "en";
        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        dgr = (TextView) findViewById(R.id.celsius);
        savecity=(EditText) findViewById(R.id.editTextCity);
        btn11=(Button) findViewById(R.id.btn1);
        btn22=(Button) findViewById(R.id.btn2);
        btn33=(Button) findViewById(R.id.btn3);
        //pager = (ViewPager) findViewById(R.id.pager);

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});

        //JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        //task1.execute(new String[]{city,lang, forecastDaysNum});

        cn=new City();
        btn11.setBackgroundColor(Color.GRAY);
        btn22.setBackgroundColor(Color.GRAY);
        btn33.setBackgroundColor(Color.GRAY);
    }

    public void saveCity(View view){

        if(cityBtn.equals("city1")){
            cn.setCity1(savecity.getText().toString());
            city=savecity.getText().toString();
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
            Toast.makeText(MainActivity.this,city+" has been saved to CITY1",Toast.LENGTH_LONG).show();



        }else if(cityBtn.equals("city2")){
            cn.setCity2(savecity.getText().toString());
            city=savecity.getText().toString();
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
            Toast.makeText(MainActivity.this,city+" has been saved to CITY2",Toast.LENGTH_LONG).show();



        }else if(cityBtn.equals("city3")){
            cn.setCity3(savecity.getText().toString());
            city=savecity.getText().toString();
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
            Toast.makeText(MainActivity.this,city+" has been saved to CITY3",Toast.LENGTH_LONG).show();

        }

    }
    public void city1(View view){
        cityBtn="city1";
        city=cn.getCity1();
        if(city!=null){
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
        }

        btn11.setBackgroundColor(Color.DKGRAY);
        btn22.setBackgroundColor(Color.GRAY);
        btn33.setBackgroundColor(Color.GRAY);

    }
    public void city2(View view){
        cityBtn="city2";
        city=cn.getCity2();
        if(city!=null){
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
        }
        btn11.setBackgroundColor(Color.GRAY);
        btn22.setBackgroundColor(Color.DKGRAY);
        btn33.setBackgroundColor(Color.GRAY);
    }
    public void city3(View view){
        cityBtn="city3";
        city=cn.getCity3();
        if(city!=null){
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(new String[]{city});
        }
        btn11.setBackgroundColor(Color.GRAY);
        btn22.setBackgroundColor(Color.GRAY);
        btn33.setBackgroundColor(Color.DKGRAY);
    }


    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }




        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);



            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15))+"C");
            dgr.setText(DEGREE);
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "");

        }







    }
/*
    private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {

            String data = ( (new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
            WeatherForecast forecast = new WeatherForecast();
            try {
                forecast = JSONWeatherParser.getForecastWeather(data);
                System.out.println("Weather ["+forecast+"]");
                // Let's retrieve the icon
                //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;

        }




        @Override
        protected void onPostExecute(WeatherForecast forecastWeather) {
            super.onPostExecute(forecastWeather);

            DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);

            pager.setAdapter(adapter);
        }



    }*/
}