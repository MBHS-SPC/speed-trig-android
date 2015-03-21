package edu.mbhs.speedtrig;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.speedtrig.R;

import java.io.IOException;

/**
 * Created by AliAnwar7477 on 2/26/2015.
 */
public class About extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if(!MainMenu.speedTrigMainTheme.isPlaying())
            MainMenu.speedTrigMainTheme.start();

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));

        set(navMenuTitles, navMenuIcons);
    }

    public void onPause(){
        super.onPause();
        if(MainMenu.speedTrigMainTheme.isPlaying())
            MainMenu.speedTrigMainTheme.stop();
    }

    public void onResume(){
        super.onResume();
        if(!MainMenu.speedTrigMainTheme.isPlaying())
            MainMenu.speedTrigMainTheme.start();
    }
}
