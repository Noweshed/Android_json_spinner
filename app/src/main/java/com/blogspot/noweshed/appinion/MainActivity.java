package com.blogspot.noweshed.appinion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner Product, Literature, PhyS, Gift;
    private RequestQueue mQueue;
    private TextView textView;
    private Button submitbtn;

    ArrayList<String> ProductArray;
    ArrayList<String> litArray;
    ArrayList<String> phyArray;
    ArrayList<String> giftArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Product = (Spinner) findViewById(R.id.PG);
        Literature = (Spinner) findViewById(R.id.literature);
        PhyS = (Spinner) findViewById(R.id.PS);
        Gift = (Spinner) findViewById(R.id.Gift);
        submitbtn = (Button) findViewById(R.id.submitId);

        ProductArray = new ArrayList<>();
        ProductArray.add("Choose");

        litArray = new ArrayList<>();
        litArray.add("Choose");

        phyArray = new ArrayList<>();
        phyArray.add("Choose");

        giftArray = new ArrayList<>();
        giftArray.add("Choose");

        mQueue = Volley.newRequestQueue(this);

        jsonParse();

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jsonParse() {

        String url = "https://raw.githubusercontent.com/appinion-dev/intern-dcr-data/master/data.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Product Group
                            JSONArray product = response.getJSONArray("product_group_list");
                            for (int i = 0; i < product.length(); i++) {
                                JSONObject productObject = product.getJSONObject(i);

                                String productgroup = productObject.getString("product_group");

                                ProductArray.add(productgroup);
                            }
                            Product.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, ProductArray));

                            //Literature
                            JSONArray literature = response.getJSONArray("literature_list");
                            for (int i = 0; i < literature.length(); i++) {
                                JSONObject literatureObject = literature.getJSONObject(i);

                                String lit = literatureObject.getString("literature");

                                litArray.add(lit);
                            }
                            Literature.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, litArray));

                            //Physician Sample
                            JSONArray phySample = response.getJSONArray("physician_sample_list");
                            for (int i = 0; i < phySample.length(); i++) {
                                JSONObject phyObject = phySample.getJSONObject(i);

                                String phy = phyObject.getString("sample");

                                phyArray.add(phy);
                            }
                            PhyS.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, phyArray));

                            //Gift
                            JSONArray gift = response.getJSONArray("gift_list");
                            for (int i = 0; i < gift.length(); i++) {
                                JSONObject giftObject = gift.getJSONObject(i);

                                String giftlist = giftObject.getString("gift");

                                giftArray.add(giftlist);
                            }
                            Gift.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, giftArray));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
