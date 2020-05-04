package com.example.shibam.covid_19livetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedStates extends AppCompatActivity {

    EditText edtSearchState;
    ListView listViewState;
    public static List<StateModel> stateModelsList = new ArrayList<>();
    StateModel stateModel;
    MyCustomAdapterState myCustomAdapterState;
    SimpleArcLoader simpleArcLoaderState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_states);
        edtSearchState = (EditText) findViewById(R.id.edtSearchState);
        listViewState =(ListView) findViewById(R.id.listViewState);
        simpleArcLoaderState = findViewById(R.id.loaderafs);

        getSupportActionBar().setTitle("Affected States");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        stateModelsList.clear();

        parse();

        listViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailedStateActivity.class).putExtra("position",position));
            }
        });
        edtSearchState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(myCustomAdapterState!=null){
                    myCustomAdapterState.getFilter().filter(s);
                    myCustomAdapterState.notifyDataSetInvalidated();
                    myCustomAdapterState.notifyDataSetChanged();
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void parse() {
        String url = "https://api.covid19india.org/data.json";
        simpleArcLoaderState.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("statewise");
                    JSONObject jobject;
                    for (int i = 1; i < jsonArray.length(); i++){
                        jobject = jsonArray.getJSONObject(i);

                        String active = jobject.getString("active");
                        String confirmed = jobject.getString("confirmed");
                        String deaths = jobject.getString("deaths");
                        String lastupdatedtime = jobject.getString("lastupdatedtime");
                        String recovered = jobject.getString("recovered");
                        String state = jobject.getString("state");
                        String statecode = jobject.getString("statecode");

                        stateModel = new StateModel(statecode,lastupdatedtime,state,active,confirmed,deaths,recovered);
                        stateModelsList.add(stateModel);
                    }
                    myCustomAdapterState = new MyCustomAdapterState(AffectedStates.this , stateModelsList);
                    listViewState.setAdapter(myCustomAdapterState);
                    simpleArcLoaderState.stop();
                    simpleArcLoaderState.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e("JSON Error",e.getMessage());
                    simpleArcLoaderState.stop();
                    simpleArcLoaderState.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoaderState.stop();
                simpleArcLoaderState.setVisibility(View.GONE);
                Toast.makeText(AffectedStates.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}

