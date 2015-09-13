package com.inspiritstudio.ifry;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.CountDownTimer;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class iFry extends ActionBarActivity implements OnClickListener {

    private TextView txtResult;
    private Button btn;
    private static final String tag = "Main";
    private MalibuCountDownTimer countDownTimer;
    private long timeElapsed;
    private boolean timerHasStarted = false;
    private Button startB;
    private TextView text;
    private TextView timeElapsedView;
    WebConnectionTask task2 = new WebConnectionTask();
    private String TotalResult;
    private Integer k ;

    private final long startTime = 10000;
    private final long interval = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startB = (Button) this.findViewById(R.id.button2);
        startB.setOnClickListener(this);
        k = 0;
        text = (TextView) this.findViewById(R.id.timer);
        timeElapsedView = (TextView) this.findViewById(R.id.timeElapsed);
        countDownTimer = new MalibuCountDownTimer(startTime, interval);
        text.setText(text.getText() + String.valueOf(startTime));
        countDownTimer.start();
        timerHasStarted = true;

        task2.execute(new String[] { "GET" });

        //manual do +
        txtResult = (TextView) findViewById(R.id.webText);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(getMethod);
    }
    @Override
    public void onClick(View v)
    {
        if (!timerHasStarted)
        {
            countDownTimer.start();
            timerHasStarted = true;
            startB.setText("Start");
        }
        else
        {

            countDownTimer.cancel();
            timerHasStarted = false;
            startB.setText("RESET");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_i_fry, menu);
        return true;
    }
    private View.OnClickListener getMethod = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            WebConnectionTask task = new WebConnectionTask();
            task.execute(new String[] { "GET" });
        }
    };

    private class WebConnectionTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String strResult = "";
            String url = "";
            switch (params[0]) {
                case "GET":
                    //url = "http://www.inspiritstudio.com/";
                    url = "http://www.apple.com//hk/shop/buy-iphone/iphone6/4.7-inch-display-64gb-rose-gold";
                    HttpGet httpGetRequest = new HttpGet(url);
                    try {
                        HttpResponse httpResponse = new DefaultHttpClient().execute(httpGetRequest);
                        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            strResult = EntityUtils.toString(httpResponse.getEntity());
                        } else {
                            strResult = "Error Response: " +
                                    httpResponse.getStatusLine().toString();
                        }
                    } catch (Exception e) {
                        strResult = e.getMessage().toString();
                        e.printStackTrace();
                    }
                    break;
                /*
                case "POST":
                    url = "http://www.systematic.com.hk/android/response.php";
                    HttpPost httpPostRequest = new HttpPost(url);
                    List<NameValuePair> urlParams = new ArrayList<NameValuePair>();
                    //urlParams.add(new BasicNameValuePair("student", txtStudent.getText().toString()));
                    //urlParams.add(new BasicNameValuePair("course", txtCourse.getText().toString()));
                    try {
                        httpPostRequest.setEntity(new UrlEncodedFormEntity(urlParams, HTTP.UTF_8));
                        HttpResponse httpResponse = new DefaultHttpClient().execute(httpPostRequest);
                        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            strResult = EntityUtils.toString(httpResponse.getEntity());
                        } else {
                            strResult = "Error Response: " +
                                    httpResponse.getStatusLine().toString();
                        }
                    } catch (Exception e) {
                        strResult = e.getMessage().toString();
                        e.printStackTrace();
                    }
                    */
            }
            return strResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Integer i = result.indexOf("Error Response");
            Integer j = result.indexOf("Unavailable");
            //Integer i = result.indexOf("be back soon");

            //if ((i >= 0 ) || (j >= 0) ) {
            if ((i >= 0 ) || (j >= 0) ) {
                k= k+1;
                TotalResult = k.toString()+"\n\n"+"diu no iphone";

               // txtResult.setText(TotalResult);

                // txtResult.setText("diu no iphone");

            }
            else {
                k+=1;
                TotalResult = k.toString()+"\n\n"+"gogogo";

                //txtResult.setText(TotalResult);

                 //txtResult.setText("gogogo");
            }

           // TotalResult += "\n\n"+result;
            txtResult.setText(TotalResult);
            //txtResult.setText(result);
        }
    }


    // CountDownTimer class
    public class MalibuCountDownTimer extends CountDownTimer
    {

        public MalibuCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
            text.setText("Time's up!");
            //WebConnectionTask task3 = new WebConnectionTask();
            //task3.execute(new String[]{"GET"});
            //txtResult.setText(result);
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));

            countDownTimer.start();
            timerHasStarted = true;

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            text.setText("Time remain:" + millisUntilFinished);
            timeElapsed = startTime - millisUntilFinished;
            timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
        }
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
