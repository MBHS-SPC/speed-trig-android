package com.example.speedtrig;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends BaseActivity {

    public static boolean isRegularTrig = true;
    public static boolean isCustomTrig = false;
    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_menu);
		//if (savedInstanceState == null) {
			//getFragmentManager().beginTransaction()
					//.add(R.id.container, new PlaceholderFragment()).commit();
		//}

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);

        updateFunctionStates();
        updateQuizDuration();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    private void updateFunctionStates(){

        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        Settings.isSinActive = settings.getBoolean("isSinActive", true);
        Settings.isCosActive = settings.getBoolean("isCosActive", true);
        Settings.isCscActive = settings.getBoolean("isCscActive", true);
        Settings.isSecActive = settings.getBoolean("isSecActive", true);
        Settings.isTanActive = settings.getBoolean("isTanActive", true);
        Settings.isCotActive = settings.getBoolean("isCotActive", true);

        Settings.isArcsinActive = settings.getBoolean("isArcsinActive", true);
        Settings.isArccosActive = settings.getBoolean("isArccosActive", true);
        Settings.isArccscActive = settings.getBoolean("isArccscActive", true);
        Settings.isArcsecActive = settings.getBoolean("isArcsecActive", true);
        Settings.isArctanActive = settings.getBoolean("isArctanActive", true);
        Settings.isArccotActive = settings.getBoolean("isArccotActive", true);
    }

    private void updateQuizDuration(){

        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        Settings.quizDuration = settings.getLong("quizDuration", 180000);
        Settings.quizDuration += 100;
    }
	
	public void startRegularDialog(View v){
		//Toast.makeText(this, "Ready!", Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, "Set!", Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, "Go!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setTitle("Regular Quiz");
        adb.setMessage("You have chosen to take a Regular Speed Trig Quiz." +
                " You will have three minutes to compute twelve non-inverse trig functions." +
                "\n\nControls are listed in the \"Help\" section.");
        adb.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipRegularInstructions", checkBoxResult);
                // Commit the edits!
                editor.commit();
                startRegular();
                return;
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipRegularInstructions", checkBoxResult);
                // Commit the edits!
                editor.commit();
                return;
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipRegularInstructions", "NOT checked");
        if (!skipMessage.equals("checked")) {
            adb.show();
        }
        else
            startInverse();
	}

    public void startRegular(){

        RegularTrig.entranceButtonClicked = true;
        isRegularTrig = true;
        startActivity(new Intent(this, RegularTrig.class));
    }

    public void startInverseDialog(View v){
        //Toast.makeText(this, "Ready!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Set!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Go!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setTitle("Inverse Quiz");
        adb.setMessage("You have chosen to take an Inverse Speed Trig Quiz." +
                " You will have three minutes to compute twelve trig functions (both inverse and non-inverse)." +
                "\n\nControls are listed in the \"Help\" section.");
        adb.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipInverseInstructions", checkBoxResult);
                // Commit the edits!
                editor.commit();
                startInverse();
                return;
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipInverseInstructions", checkBoxResult);
                // Commit the edits!
                editor.commit();
                return;
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipInverseInstructions", "NOT checked");
        if (!skipMessage.equals("checked"))
            adb.show();
        else
            startInverse();
    }

	public void startInverse(){
		//Toast.makeText(this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
		//startActivity(new Intent(this, InverseTrig.class));
        InverseTrig.entranceButtonClicked = true;
        isRegularTrig = false;
        startActivity(new Intent(this, InverseTrig.class));
	}

    public void startCustomDialog(View v){
        //Toast.makeText(this, "Ready!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Set!", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Go!", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        adb.setView(eulaLayout);
        adb.setTitle("Custom Quiz");
        adb.setMessage("You have chosen to take a Custom Speed Trig Quiz." +
                " The duration and active functions of a Custom quiz can be altered in the Settings menu. By default, a Custom quiz is three minutes long and has both inverse and non-inverse functions." +
                "\n\nControls are listed in the \"Help\" section.");
        adb.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipCustomInstructions", checkBoxResult);
                // Commit the edits!
                editor.commit();
                startCustom();
                return;
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipCustomInstructions", checkBoxResult);
                // Commit the edits!
                editor.commit();
                return;
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipCustomInstructions", "NOT checked");
        if (!skipMessage.equals("checked"))
            adb.show();
        else
            startCustom();
    }

    public void startCustom(){
        //Toast.makeText(this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(this, InverseTrig.class));
        CustomTrig.entranceButtonClicked = true;
        isCustomTrig = true;
        startActivity(new Intent(this, CustomTrig.class));
    }

    public void startSettings(View v){

        startActivity(new Intent(this, Settings.class));
    }

    public void startAbout(View v){

        startActivity(new Intent(this, About.class));
    }

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_menu,
					container, false);
			return rootView;
		}
	}

}
