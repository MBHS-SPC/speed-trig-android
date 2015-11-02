package edu.mbhs.speedtrig;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Activity for the learning page
 * @author suyhmed
 * Created on 3/15/15.
 */
public class Learn extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // (START) URL for The Online Speed Trig Quiz Generator
        ImageView TrigGenOnline = (ImageView) findViewById(R.id.imageView28);

        TrigGenOnline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent TrigGenOnline = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mbhs.edu/~ansarma/rose/"));
                startActivity(TrigGenOnline);
            }
        });
        //(END) URL for The Online Speed Trig Quiz Generator

        findViewById(R.id.unit_circle_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Learn.this, UnitCircle.class));
            }
        });

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons); // load icons from strings.xml

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));

        set(navMenuTitles, navMenuIcons);
    }
}
