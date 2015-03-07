package com.example.speedtrig;

import android.app.ListActivity;
import android.content.res.TypedArray;
import android.os.Bundle;

/**
 * Created by AliAnwar7477 on 3/7/2015.
 */
public class Help extends BaseActivity {

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);
    }
}
