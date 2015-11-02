package edu.mbhs.speedtrig;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

/**
 * Activity for the credits page
 * @author AliAnwar7477
 * Created on 3/7/2015.
 */
public class Credits extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));

        set(navMenuTitles, navMenuIcons);
    }
}
