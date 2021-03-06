package edu.mbhs.speedtrig;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

/**
 * Activity for the achievements page
 * @author AliAnwar7477
 * Created on 3/15/2015.
 */
public class Achievements extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));

        set(navMenuTitles, navMenuIcons);
    }
}
