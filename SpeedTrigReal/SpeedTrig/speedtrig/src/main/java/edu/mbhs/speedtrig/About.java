package edu.mbhs.speedtrig;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

        /*
         *Start of Social Media Buttons
         *Give the users clickable Social Media buttons that direct them to
         * our social media sites such as Facebook and Google+
         */
        ImageView googlePlusButton = (ImageView) findViewById(R.id.imageButton);
        ImageView faceBookButton = (ImageView) findViewById(R.id.imageButton2);

        BaseActivity.currentSelectedItemView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#963f51b5")));

        googlePlusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent googlePlus = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/b/112188567527171945812/112188567527171945812/posts"));
                startActivity(googlePlus);
            }
        });

        faceBookButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent faceBook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/mhbs.spc"));
                startActivity(faceBook);
            }
        });
//End of Social Media Buttons

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3f51b5")));

        set(navMenuTitles, navMenuIcons);
    }
}
