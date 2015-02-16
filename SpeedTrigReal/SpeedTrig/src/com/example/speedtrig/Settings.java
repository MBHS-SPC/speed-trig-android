package com.example.speedtrig;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

/**
 * Created by alia7_000 on 2/16/2015.
 */
public class Settings extends PreferenceActivity {

    static boolean isSinActive;
    static boolean isCosActive = true;
    static boolean isCscActive = true;
    static boolean isSecActive = true;
    static boolean isCotActive = true;
    static boolean isTanActive = true;

    static boolean isArcsinActive = true;
    static boolean isArccosActive = true;
    static boolean isArccscActive = true;
    static boolean isArcsecActive = true;
    static boolean isArccotActive = true;
    static boolean isArctanActive = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        isSinActive = settings.getBoolean("isSinActive", true);
        isCosActive = true;
        isCscActive = true;
        isSecActive = true;
        isCotActive = true;
        isTanActive = true;

        isArcsinActive = true;
        isArccosActive = true;
        isArccscActive = true;
        isArcsecActive = true;
        isArccotActive = true;
        isArctanActive = true;

    }

    public static boolean isOperationActive(String operation){

        boolean booleanToReturn = true;

        if(operation.equals("sin"))
            booleanToReturn = isSinActive;
        if(operation.equals("cos"))
            booleanToReturn = isCosActive;
        if(operation.equals("csc"))
            booleanToReturn = isCscActive;
        if(operation.equals("sec"))
            booleanToReturn = isSecActive;
        if(operation.equals("tan"))
            booleanToReturn = isCotActive;
        if(operation.equals("cot"))
            booleanToReturn = isTanActive;

        if(operation.equals("arcsin"))
            booleanToReturn = isArcsinActive;
        if(operation.equals("arccos"))
            booleanToReturn = isArccosActive;
        if(operation.equals("arccsc"))
            booleanToReturn = isArccscActive;
        if(operation.equals("arcsec"))
            booleanToReturn = isArcsecActive;
        if(operation.equals("arctan"))
            booleanToReturn = isArctanActive;
        if(operation.equals("arccot"))
            booleanToReturn = isArccotActive;

        return booleanToReturn;
    }

    public void onCheckboxClicked(View view) {

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {

            case R.id.checkbox_sin:
                if (checked)
                    editor.putBoolean("isSinActive", true);
                else
                    editor.putBoolean("isSinActive", false);
                break;

            case R.id.checkbox_cos:
                if (checked)
                    isCosActive = true;
                else
                    isCosActive = false;
                break;

            case R.id.checkbox_csc:
                if (checked)
                    isCscActive = true;
                else
                    isCscActive = false;
                break;

            case R.id.checkbox_sec:
                if (checked)
                    isSecActive = true;
                else
                    isSecActive = false;
                break;

            case R.id.checkbox_tan:
                if (checked)
                    isTanActive = true;
                else
                    isTanActive = false;
                break;

            case R.id.checkbox_cot:
                if (checked)
                    isCotActive = true;
                else
                    isCotActive = false;
                break;
        }

        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends PreferenceFragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_inverse_trig,
                    container, false);
            return rootView;
        }
    }
}
