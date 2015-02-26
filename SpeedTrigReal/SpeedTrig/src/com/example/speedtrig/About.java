package com.example.speedtrig;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by AliAnwar7477 on 2/26/2015.
 */
public class About extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void destroyActivity(View v){
        finish();
    }

}
