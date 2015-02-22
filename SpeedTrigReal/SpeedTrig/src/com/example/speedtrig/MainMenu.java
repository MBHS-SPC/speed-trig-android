package com.example.speedtrig;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainMenu extends Activity {

    public static boolean isRegularTrig = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

        Settings.quizDuration = settings.getLong("quizDuration", 1000);
        Settings.quizDuration += 100;
    }
	
	public void startRegular(View v){
		//Toast.makeText(this, "Ready!", Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, "Set!", Toast.LENGTH_SHORT).show();
		//Toast.makeText(this, "Go!", Toast.LENGTH_SHORT).show();
		RegularTrig.entranceButtonClicked = true;
        isRegularTrig = true;
		startActivity(new Intent(this, RegularTrig.class));
	}

	public void startInverse(View v){
		//Toast.makeText(this, "Coming Soon!!!", Toast.LENGTH_SHORT).show();
		//startActivity(new Intent(this, InverseTrig.class));
        InverseTrig.entranceButtonClicked = true;
        isRegularTrig = false;
        startActivity(new Intent(this, InverseTrig.class));
	}

    public void startSettings(View v){

        startActivity(new Intent(this, Settings.class));
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
