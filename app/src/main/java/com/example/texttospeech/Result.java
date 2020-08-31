package com.example.texttospeech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;


public class Result extends Activity {
    TextView QueryText;
    TextView Title;
    TextView descText;
    ImageView imageView;


    public String API_END = "https://www.googleapis.com/customsearch/v1?key=AIzaSyD8sBxnVk7BwYwKBcgivt2PRZeR1MnaShE&cx=d2b34d756bdea61ac&q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        QueryText = findViewById(R.id.QueryText);
        Title = findViewById(R.id.titleText);
        descText = findViewById(R.id.descText);
        imageView = findViewById(R.id.image);

        Intent i = getIntent();
        String query = i.getStringExtra("queryString").toLowerCase();

        QueryText.setText("Query Text : ".concat(query));
//        initiate the queue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = API_END.concat(query);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("items");

                                JSONObject item = jsonArray.getJSONObject(0);
                                String title = item.getString("title");
                                String Desc = item.getString("snippet");

                                Title.setText(title);
                                descText.setText(Desc);
//                                imageView.setImageURI(url);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener(){
                        @Override
                                public void onErrorResponse(VolleyError error) {
                            QueryText.setText("That didn't work");
                        }
                });
        queue.add(request);

    }



}
