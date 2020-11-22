package com.example.graph;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.graph.database.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private static final String URL = "https://demo5636362.mockable.io/stats";

    Button logoutBtn;
    TextView fetchData;

    SharedPreferences preferences;
    DBHelper dbHelper;
    private RequestQueue requestQueue;

    // Bar chart

    ProgressBar januaryP, februaryP, marchP, aprilP, mayP, juneP, julyP, augustP, septemberP, octoberP, novemberP, decemberP;
    TextView txtJan, txtFeb, txtMar, txtApr, txtMay, txtJun, txtJul, txtAug, txtSep, txtOct, txtNov, txtDec;
    TextView statJan, statFeb, statMar, statApr, statMay, statJun, statJul, statAug, statSep, statOct, statNov, statDec;

    // Bar chart
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logoutBtn = findViewById(R.id.logout_button_home);
        fetchData = findViewById(R.id.fetchData_text_home);

        preferences = getSharedPreferences("LoginPref", MODE_PRIVATE);
        dbHelper = new DBHelper(this);
//        Bar Chart Initialization
//        ----Progressbars
        januaryP = findViewById(R.id.january_bar);
        februaryP = findViewById(R.id.february_bar);
        marchP = findViewById(R.id.march_bar);
        aprilP = findViewById(R.id.april_bar);
        mayP = findViewById(R.id.may_bar);
        juneP = findViewById(R.id.june_bar);
        julyP = findViewById(R.id.july_bar);
        augustP = findViewById(R.id.august_bar);
        septemberP = findViewById(R.id.september_bar);
        octoberP = findViewById(R.id.october_bar);
        novemberP = findViewById(R.id.november_bar);
        decemberP = findViewById(R.id.december_bar);

//        ----Month Names
        txtJan = findViewById(R.id.text_jan_chart);
        txtFeb = findViewById(R.id.text_feb_chart);
        txtMar = findViewById(R.id.text_mar_chart);
        txtApr = findViewById(R.id.text_apr_chart);
        txtMay = findViewById(R.id.text_may_chart);
        txtJun = findViewById(R.id.text_jun_chart);
        txtJul = findViewById(R.id.text_jul_chart);
        txtAug = findViewById(R.id.text_aug_chart);
        txtSep = findViewById(R.id.text_sep_chart);
        txtOct = findViewById(R.id.text_oct_chart);
        txtNov = findViewById(R.id.text_nov_chart);
        txtDec = findViewById(R.id.text_dec_chart);

//        ----Stats
        statJan = findViewById(R.id.text_jan_stat);
        statFeb = findViewById(R.id.text_feb_stat);
        statMar = findViewById(R.id.text_mar_stat);
        statApr = findViewById(R.id.text_apr_stat);
        statMay = findViewById(R.id.text_may_stat);
        statJun = findViewById(R.id.text_jun_stat);
        statJul = findViewById(R.id.text_jul_stat);
        statAug = findViewById(R.id.text_aug_stat);
        statSep = findViewById(R.id.text_sep_stat);
        statOct = findViewById(R.id.text_oct_stat);
        statNov = findViewById(R.id.text_nov_stat);
        statDec = findViewById(R.id.text_dec_stat);

//        Bar Chart Initialization

        String currentUserName = preferences.getString("userName", "0");

        if (currentUserName.equals("0")) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

//        Volley
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jan = jsonArray.getJSONObject(0);
                        JSONObject feb = jsonArray.getJSONObject(1);
                        JSONObject mar = jsonArray.getJSONObject(2);
                        JSONObject apr = jsonArray.getJSONObject(3);
                        JSONObject may = jsonArray.getJSONObject(4);
                        JSONObject jun = jsonArray.getJSONObject(5);
                        JSONObject jul = jsonArray.getJSONObject(6);
                        JSONObject aug = jsonArray.getJSONObject(7);
                        JSONObject sep = jsonArray.getJSONObject(8);
                        JSONObject oct = jsonArray.getJSONObject(9);
                        JSONObject nov = jsonArray.getJSONObject(10);
                        JSONObject dec = jsonArray.getJSONObject(11);
                        JSONObject data = jsonArray.getJSONObject(i);

                        String[] months = {jan.getString("month"), feb.getString("month")
                                , mar.getString("month"), apr.getString("month")
                                , may.getString("month"), jun.getString("month")
                                , jul.getString("month"), aug.getString("month")
                                , sep.getString("month"), oct.getString("month")
                                , nov.getString("month"), dec.getString("month")};
                        String[] stats = {jan.getString("stat"), feb.getString("stat")
                                , mar.getString("stat"), apr.getString("stat")
                                , may.getString("stat"), jun.getString("stat")
                                , jul.getString("stat"), aug.getString("stat")
                                , sep.getString("stat"), oct.getString("stat")
                                , nov.getString("stat"), dec.getString("stat")};


                        txtJan.setText(months[0]);
                        januaryP.setProgress(Integer.parseInt(stats[0]));
                        statJan.setText(stats[0]);


                        txtFeb.setText(months[1]);
                        februaryP.setProgress(Integer.parseInt(stats[1]));
                        statFeb.setText(stats[1]);

                        txtMar.setText(months[2]);
                        marchP.setProgress(Integer.parseInt(stats[2]));
                        statMar.setText(stats[2]);

                        txtApr.setText(months[3]);
                        aprilP.setProgress(Integer.parseInt(stats[3]));
                        statApr.setText(stats[3]);

                        txtMay.setText(months[4]);
                        mayP.setProgress(Integer.parseInt(stats[4]));
                        statMay.setText(stats[4]);

                        txtJun.setText(months[5]);
                        juneP.setProgress(Integer.parseInt(stats[5]));
                        statJun.setText(stats[5]);

                        txtJul.setText(months[6]);
                        julyP.setProgress(Integer.parseInt(stats[6]));
                        statJul.setText(stats[6]);

                        txtAug.setText(months[7]);
                        augustP.setProgress(Integer.parseInt(stats[7]));
                        statAug.setText(stats[7]);

                        txtSep.setText(months[8]);
                        septemberP.setProgress(Integer.parseInt(stats[8]));
                        statSep.setText(stats[8]);

                        txtOct.setText(months[9]);
                        octoberP.setProgress(Integer.parseInt(stats[9]));
                        statOct.setText(stats[9]);

                        txtNov.setText(months[10]);
                        novemberP.setProgress(Integer.parseInt(stats[10]));
                        statNov.setText(stats[10]);

                        txtDec.setText(months[11]);
                        decemberP.setProgress(Integer.parseInt(stats[11]));
                        statDec.setText(stats[11]);

                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
//        Volley

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("userName");
                editor.commit();

                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

    }

}
