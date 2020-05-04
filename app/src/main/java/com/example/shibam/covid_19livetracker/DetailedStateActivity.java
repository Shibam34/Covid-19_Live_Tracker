package com.example.shibam.covid_19livetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailedStateActivity extends AppCompatActivity {

    TextView tvStateCode,tvLastUpdatedTime,tvState,tvActiveState,tvConfirmed,tvDeathsState,tvRecoveredState;
    private int positionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_state);

        Intent intent = getIntent();
        positionState = intent.getIntExtra("position",0);
        getSupportActionBar().setTitle("Details of "+AffectedStates.stateModelsList.get(positionState).getState());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvStateCode =(TextView) findViewById(R.id.tvStateCode);
        tvLastUpdatedTime =(TextView) findViewById(R.id.tvLastUpdatedTime);
        tvState =(TextView) findViewById(R.id.tvState);
        tvActiveState =(TextView) findViewById(R.id.tvActiveState);
        tvConfirmed =(TextView) findViewById(R.id.tvConfirmed);
        tvDeathsState =(TextView) findViewById(R.id.tvDeathsState);
        tvRecoveredState =(TextView) findViewById(R.id.tvRecoveredState);

        tvStateCode.setText(AffectedStates.stateModelsList.get(positionState).getStatecode());
        tvLastUpdatedTime.setText(AffectedStates.stateModelsList.get(positionState).getLastupdatedtime());
        tvState.setText(AffectedStates.stateModelsList.get(positionState).getState());
        tvActiveState.setText(AffectedStates.stateModelsList.get(positionState).getActive());
        tvConfirmed.setText(AffectedStates.stateModelsList.get(positionState).getConfirmed());
        tvDeathsState.setText(AffectedStates.stateModelsList.get(positionState).getDeaths());
        tvRecoveredState.setText(AffectedStates.stateModelsList.get(positionState).getRecovered());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
