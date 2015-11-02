package edu.mbhs.speedtrig;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainMenu extends BaseActivity /**implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {

    public enum QuizType {
        REGULAR, INVERSE, CUSTOM
    }
    public static QuizType quizType;
    public static final String PREFS_NAME = "MyPrefsFile1";
    public CheckBox dontShowAgain;

    /**
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    boolean mExplicitSignOut = false;
    boolean mInSignInFlow = false; // set to true when you're in the middle of the
    // sign in flow, to know you should not attempt
    // to connect in onStart()
     */

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_menu);



		//if (savedInstanceState == null) {
			//getFragmentManager().beginTransaction()
					//.add(R.id.container, new PlaceholderFragment()).commit();
		//}

        /**
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != 0)
            GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this), this, 0);

        // Create the Google Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                        // add other APIs and scopes here as needed
                .build();

        findViewById(R.id.show_achievements).setOnClickListener(this);
        findViewById(R.id.show_leaderboard).setOnClickListener(this);
         */

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml

        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml

        set(navMenuTitles, navMenuIcons);

        Button quizTypeRegular = (Button) findViewById(R.id.button2);
        quizTypeRegular.setVisibility(Button.VISIBLE);
        quizTypeRegular.setText(R.string.quiz_text_regular);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303F9F")));

        updateFunctionStates();
        updateQuizDuration();
	}
    /**
    @Override
    protected void onStart() {
        super.onStart();
        if (!mInSignInFlow && !mExplicitSignOut) {
            // auto sign in
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
     */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

    /**
    @Override
    public void onConnected(Bundle bundle) {

        // show sign-out button, hide the sign-in button
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);

        // (your code here: update UI, enable functionality that depends on sign in, etc)

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (mResolvingConnectionFailure) {
            // Already resolving
            return;
        }

        // If the sign in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "Sign-in failed. Please try again later.")) {
                mResolvingConnectionFailure = false;
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sign_in_button) {
            // start the asynchronous sign in flow
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
        else if (v.getId() == R.id.sign_out_button) {
            // sign out.
            mSignInClicked = false;
            Games.signOut(mGoogleApiClient);

            // user explicitly signed out, so turn off auto sign in
            mExplicitSignOut = true;
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Games.signOut(mGoogleApiClient);
                mGoogleApiClient.disconnect();
            }

            // show sign-in button, hide the sign-out button
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }

        else if (v.getId() == R.id.show_achievements){
            startActivityForResult(Games.Achievements.getAchievementsIntent(
                    mGoogleApiClient), 1);
        }

        else if(v.getId() == R.id.show_leaderboard){
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                            mGoogleApiClient, getString(R.string.leaderboard_regular_trig_time)),
                    2);
        }
    }
    */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    private void updateFunctionStates(){

        SharedPreferences settings = getSharedPreferences("Settings", MODE_PRIVATE);

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

        SharedPreferences settings = getSharedPreferences("Settings", MODE_PRIVATE);

        Settings.quizDuration = settings.getLong("quizDuration", 180000);
        Settings.quizDuration += 100;
    }

    @SuppressLint("InflateParams")
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
        adb.setMessage("Start a regular quiz?");
        adb.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipRegularInstructions", checkBoxResult);
                // Commit the edits!
                editor.apply();
                startRegular();
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
                editor.apply();
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("skipRegularInstructions", "NOT checked");
        if (!skipMessage.equals("checked")) {
            adb.show();
        }
        else
            startRegular();
	}

    public void startRegular(){
        quizType = QuizType.REGULAR;
        startActivity(new Intent(this, ResponseWindow.class));
    }

    @SuppressLint("InflateParams")
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
        adb.setMessage("Start an inverse quiz?");
        adb.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipInverseInstructions", checkBoxResult);
                // Commit the edits!
                editor.apply();
                startInverse();
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
                editor.apply();
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
        quizType = QuizType.INVERSE;
        startActivity(new Intent(this, ResponseWindow.class));
    }

    @SuppressLint("InflateParams")
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
        adb.setMessage("Start a custom quiz?");
        adb.setPositiveButton("Start Quiz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String checkBoxResult = "NOT checked";
                if (dontShowAgain.isChecked())
                    checkBoxResult = "checked";
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("skipCustomInstructions", checkBoxResult);
                // Commit the edits!
                editor.apply();
                startCustom();
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
                editor.apply();
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
        quizType = QuizType.CUSTOM;
        startActivity(new Intent(this, ResponseWindow.class));
    }

    public void shiftQuizTypeLeft(View v){

        Button quizType = (Button) findViewById(R.id.button2);

        TextView quizTypeDuration = (TextView) findViewById(R.id.textView23);
        TextView quizTypeFunctions = (TextView) findViewById(R.id.textView24);
        TextView quizTypeOtherInfo = (TextView) findViewById(R.id.textView25);

        if(quizType.getText().equals("Regular")){
            quizType.setText(R.string.quiz_text_custom);
            quizType.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startCustomDialog(v);
                }
            });

            quizTypeDuration.setText(R.string.quiz_duration_custom);
            quizTypeFunctions.setText(R.string.quiz_functions_custom);
            quizTypeOtherInfo.setText(R.string.quiz_info_custom);
        }

        else if(quizType.getText().equals("Inverse")){
            quizType.setText(R.string.quiz_text_regular);
            quizType.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startRegularDialog(v);
                }
            });

            quizTypeDuration.setText(R.string.quiz_duration_regular);
            quizTypeFunctions.setText(R.string.quiz_functions_regular);
            quizTypeOtherInfo.setText(R.string.quiz_info_regular);
        }

        else if(quizType.getText().equals("Custom")){
            quizType.setText(R.string.quiz_text_inverse);
            quizType.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startInverseDialog(v);
                }
            });

            quizTypeDuration.setText(R.string.quiz_duration_inverse);
            quizTypeFunctions.setText(R.string.quiz_functions_inverse);
            quizTypeOtherInfo.setText(R.string.quiz_info_inverse);
        }

    }

    public void shiftQuizTypeRight(View v){

        Button quizType = (Button) findViewById(R.id.button2);

        TextView quizTypeDuration = (TextView) findViewById(R.id.textView23);
        TextView quizTypeFunctions = (TextView) findViewById(R.id.textView24);
        TextView quizTypeOtherInfo = (TextView) findViewById(R.id.textView25);

        if(quizType.getText().equals("Inverse")){
            quizType.setText(R.string.quiz_text_custom);
            quizType.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startCustomDialog(v);
                }
            });

            quizTypeDuration.setText(R.string.quiz_duration_custom);
            quizTypeFunctions.setText(R.string.quiz_functions_custom);
            quizTypeOtherInfo.setText(R.string.quiz_info_custom);
        }

        else if(quizType.getText().equals("Custom")){
            quizType.setText(R.string.quiz_text_regular);
            quizType.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startRegularDialog(v);
                }
            });

            quizTypeDuration.setText(R.string.quiz_duration_regular);
            quizTypeFunctions.setText(R.string.quiz_functions_regular);
            quizTypeOtherInfo.setText(R.string.quiz_info_regular);
        }

        else if(quizType.getText().equals("Regular")){
            quizType.setText(R.string.quiz_text_inverse);
            quizType.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    startInverseDialog(v);
                }
            });

            quizTypeDuration.setText(R.string.quiz_duration_inverse);
            quizTypeFunctions.setText(R.string.quiz_functions_inverse);
            quizTypeOtherInfo.setText(R.string.quiz_info_inverse);
        }

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
            return inflater.inflate(R.layout.fragment_main_menu, container, false);
		}
	}

}
