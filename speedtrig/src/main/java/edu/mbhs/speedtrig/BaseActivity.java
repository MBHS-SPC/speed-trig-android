package edu.mbhs.speedtrig;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BaseActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

    public static MediaPlayer speedTrigMainTheme;
    public static int speedTrigMainThemeProgress;

    public static View currentSelectedItemView = null;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer);
		// if (savedInstanceState == null) {
		// // on first time display view for first nav item
		// // displayView(0);
		// }
	}

	public void set(String[] navMenuTitles, TypedArray navMenuIcons) {
		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();

		// adding nav drawer items
		if (navMenuIcons == null) {
			for (String navMenuTitle : navMenuTitles) {
				navDrawerItems.add(new NavDrawerItem(navMenuTitle));
			}
		} else {
			for (int i = 0; i < navMenuTitles.length; i++) {
				navDrawerItems.add(new NavDrawerItem(navMenuTitles[i],
						navMenuIcons.getResourceId(i, -1)));
			}
		}

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

        updateSoundStatus();

        if(speedTrigMainTheme == null && Settings.isMusicEnabled) {
            speedTrigMainTheme = MediaPlayer.create(this, R.raw.speed_trig_main_theme);
            speedTrigMainTheme.seekTo(speedTrigMainThemeProgress);
            speedTrigMainTheme.setLooping(true);
            speedTrigMainTheme.start();
        }

		// enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				//R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
				// accessibility
				R.string.app_name // nav drawer close - description for
		// accessibility
		) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override @SuppressWarnings("deprecation")
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (Build.VERSION.SDK_INT > 15)
				view.setBackground(new ColorDrawable(Color.parseColor("#963f51b5")));
			else
            	view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#963f51b5")));
            currentSelectedItemView = view;
			// display view for selected nav drawer item
			displayView(position);
		}
	}

    public void onBackPressed(){

        if(!getClass().equals(MainMenu.class)) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        }
        else {
            finish();
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public void on(){

    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Displaying fragment view for selected nav drawer list item
	 */
	@SuppressLint("InflateParams")
	private void displayView(int position) {

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerList);

		switch (position) {
		case 0:
			Intent intent = new Intent(this, MainMenu.class);
			startActivity(intent);
			finish();// finishes the current activity
			break;
        case 1:
            Intent intent1 = new Intent(this, Learn.class);
            startActivity(intent1);
            finish();// finishes the current activity
            break;
        case 2:
            //Intent intent2 = new Intent(this, Leaderboards.class);
            //startActivity(intent2);
            //finish();// finishes the current activity
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            LayoutInflater adbInflater = LayoutInflater.from(this);

            adb.setView(adbInflater.inflate(R.layout.checkbox_submit_button, null));
            adb.setTitle("Coming Soon");
            adb.setMessage("This feature is coming soon.");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            adb.setNegativeButton("", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            adb.show();
            break;
        case 3:
            //Intent intent3 = new Intent(this, Achievements.class);
            //startActivity(intent3);
            //finish();
            AlertDialog.Builder adbTwo = new AlertDialog.Builder(this);
            LayoutInflater adbInflaterTwo = LayoutInflater.from(this);
            adbTwo.setView(adbInflaterTwo.inflate(R.layout.checkbox_submit_button, null));
            adbTwo.setTitle("Coming Soon");
            adbTwo.setMessage("This feature is coming soon.");
            adbTwo.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            adbTwo.setNegativeButton("", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            adbTwo.show();
            break;
		case 4:
			Intent intent4 = new Intent(this, Settings.class);
			startActivity(intent4);
			finish();// finishes the current activity
			break;
		case 5:
		    Intent intent5 = new Intent(this, Help.class);
		    startActivity(intent5);
		    finish();
		    break;
		case 6:
		    Intent intent6 = new Intent(this, About.class);
		    startActivity(intent6);
		    finish();
		    break;
		case 7:
		    Intent intent7 = new Intent(this, Credits.class);
		    startActivity(intent7);
		    finish();
		    break;
		default:
			break;
		}
	}

    protected void updateSoundStatus(){

        SharedPreferences settings = getSharedPreferences("Settings", MODE_PRIVATE);
        Settings.areBlairTalksSoundsEnabled = settings.getBoolean("areBlairTalksSoundsEnabled", true);
        Settings.isMusicEnabled = settings.getBoolean("isMusicEnabled", true);
    }

	@SuppressWarnings("deprecation")
    public void onPause(){
        super.onPause();
        //speedTrigMainThemeProgress = speedTrigMainTheme.getCurrentPosition();
        if(speedTrigMainTheme != null)
            speedTrigMainTheme.pause();
        if(currentSelectedItemView != null) {
			if (Build.VERSION.SDK_INT > 15)
				currentSelectedItemView.setBackground(new ColorDrawable(Color.parseColor("#963f51b5")));
			else
				currentSelectedItemView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#963f51b5")));
		}
    }

	@SuppressWarnings("deprecation")
    public void onResume(){
        super.onResume();
        if(speedTrigMainTheme != null && Settings.isMusicEnabled)
            speedTrigMainTheme.start();
		if(currentSelectedItemView != null) {
			if (Build.VERSION.SDK_INT > 15)
				currentSelectedItemView.setBackground(new ColorDrawable(Color.parseColor("#963f51b5")));
			else
				currentSelectedItemView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#963f51b5")));
		}
    }

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
