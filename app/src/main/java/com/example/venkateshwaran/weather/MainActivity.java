package com.example.venkateshwaran.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    EditText ed1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";

TextView ed2,ed3,ed4,ed5;

    private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String url2 = "&mode=xml&APPID=388fdb71c09bd0d16cd8e3972111cc6f";
    private HandleXML obj;
    Button b1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(TextView)findViewById(R.id.editText2);
        ed3=(TextView)findViewById(R.id.textView);
        ed4=(TextView)findViewById(R.id.editText4);
        ed5=(TextView)findViewById(R.id.editText5);
        ed1.setOnKeyListener(new View.OnKeyListener()
        {

            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {

                        case KeyEvent.KEYCODE_ENTER:
                            String url = ed1.getText().toString();
                            String finalUrl = url1 + url + url2;
                            ed2.setText(finalUrl);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                            obj = new HandleXML(finalUrl);
                            obj.fetchXML();

                            while(obj.parsingComplete);
                            ed2.setText("Sky:"+obj.getCountry());
                            ed3.setText((String.valueOf(String.format("%.1f",Float.parseFloat(obj.getTemperature())-273)))+" ℃");
                            ed4.setText("Humidity:"+obj.getHumidity()+"%");
                            ed5.setText("Pressure:"+obj.getPressure()+"hPa");

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ed1.getText().toString();
                String finalUrl = url1 + url + url2;
                ed2.setText(finalUrl);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                obj = new HandleXML(finalUrl);
                obj.fetchXML();

                while (obj.parsingComplete) ;
                ed2.setText("Sky:" + obj.getCountry());
                ed3.setText((String.valueOf(String.format("%.1f", Float.parseFloat(obj.getTemperature()) - 273))) + " C");
                ed4.setText("Humidity:" + obj.getHumidity() + "%");
                ed5.setText("Pressure:" + obj.getPressure() + "hPa");
            }
        });

        SharedPreferences sharedpreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Name,ed1.getText().toString());
        editor.commit();
      Toast toast=Toast.makeText(getApplicationContext(), sharedpreferences.getString(Name,"Chennai"),Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}