package com.example.speedtrig;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/**
 * Created by alia7_000 on 2/16/2015.
 */
public class Settings extends PreferenceActivity {

    static boolean isSinActive;
    static boolean isCosActive;
    static boolean isCscActive;
    static boolean isSecActive;
    static boolean isCotActive;
    static boolean isTanActive;

    static boolean isArcsinActive;
    static boolean isArccosActive;
    static boolean isArccscActive;
    static boolean isArcsecActive;
    static boolean isArccotActive;
    static boolean isArctanActive;

    String[] functions = {"sin", "cos", "csc", "sec", "tan", "cot", "arcsin", "arccos", "arccsc", "arcsec", "arctan", "arccot"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        updateFunctionStates();

        CheckBox checkbox_sin = (CheckBox) findViewById(R.id.checkbox_sin);
        CheckBox checkbox_cos = (CheckBox) findViewById(R.id.checkbox_cos);
        CheckBox checkbox_csc = (CheckBox) findViewById(R.id.checkbox_csc);
        CheckBox checkbox_sec = (CheckBox) findViewById(R.id.checkbox_sec);
        CheckBox checkbox_tan = (CheckBox) findViewById(R.id.checkbox_tan);
        CheckBox checkbox_cot = (CheckBox) findViewById(R.id.checkbox_cot);

        CheckBox checkbox_arcsin = (CheckBox) findViewById(R.id.checkbox_arcsin);
        CheckBox checkbox_arccos = (CheckBox) findViewById(R.id.checkbox_arccos);
        CheckBox checkbox_arccsc = (CheckBox) findViewById(R.id.checkbox_arccsc);
        CheckBox checkbox_arcsec = (CheckBox) findViewById(R.id.checkbox_arcsec);
        CheckBox checkbox_arctan = (CheckBox) findViewById(R.id.checkbox_arctan);
        CheckBox checkbox_arccot = (CheckBox) findViewById(R.id.checkbox_arccot);

        CheckBox[] functionCheckBoxes = {checkbox_sin, checkbox_cos, checkbox_csc, checkbox_sec, checkbox_tan, checkbox_cot,
                checkbox_arcsin, checkbox_arccos, checkbox_arccsc, checkbox_arcsec, checkbox_arctan, checkbox_arccot};

        for(int i = 0; i < functions.length; i++){

            if(isFunctionActive(functions[i]))
                functionCheckBoxes[i].setChecked(true);
            else
                functionCheckBoxes[i].setChecked(false);
        }
    }

    public static boolean isFunctionActive(String operation){

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

    private void updateFunctionStates(){

        SharedPreferences settings = getPreferences(MODE_PRIVATE);

        isSinActive = settings.getBoolean("isSinActive", true);
        isCosActive = settings.getBoolean("isCosActive", true);
        isCscActive = settings.getBoolean("isCscActive", true);
        isSecActive = settings.getBoolean("isSecActive", true);
        isTanActive = settings.getBoolean("isTanActive", true);
        isCotActive = settings.getBoolean("isCotActive", true);

        isArcsinActive = settings.getBoolean("isArcsinActive", true);
        isArccosActive = settings.getBoolean("isArccosActive", true);
        isArccscActive = settings.getBoolean("isArccscActive", true);
        isArcsecActive = settings.getBoolean("isArcsecActive", true);
        isArctanActive = settings.getBoolean("isArctanActive", true);
        isArccotActive = settings.getBoolean("isArccotActive", true);
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
                    editor.putBoolean("isCosActive", true);
                else
                    editor.putBoolean("isCosActive", false);
                break;

            case R.id.checkbox_csc:
                if (checked)
                    editor.putBoolean("isCscActive", true);
                else
                    editor.putBoolean("isCscActive", false);
                break;

            case R.id.checkbox_sec:
                if (checked)
                    editor.putBoolean("isSecActive", true);
                else
                    editor.putBoolean("isSecActive", false);
                break;

            case R.id.checkbox_tan:
                if (checked)
                    editor.putBoolean("isTanActive", true);
                else
                    editor.putBoolean("isTanActive", false);
                break;

            case R.id.checkbox_cot:
                if (checked)
                    editor.putBoolean("isCotActive", true);
                else
                    editor.putBoolean("isCotActive", false);
                break;

            case R.id.checkbox_arcsin:
                if (checked)
                    editor.putBoolean("isArcsinActive", true);
                else
                    editor.putBoolean("isArcsinActive", false);
                break;

            case R.id.checkbox_arccos:
                if (checked)
                    editor.putBoolean("isArccosActive", true);
                else
                    editor.putBoolean("isArccosActive", false);
                break;

            case R.id.checkbox_arccsc:
                if (checked)
                    editor.putBoolean("isArccscActive", true);
                else
                    editor.putBoolean("isArccscActive", false);
                break;

            case R.id.checkbox_arcsec:
                if (checked)
                    editor.putBoolean("isArcsecActive", true);
                else
                    editor.putBoolean("isArcsecActive", false);
                break;

            case R.id.checkbox_arctan:
                if (checked)
                    editor.putBoolean("isArctanActive", true);
                else
                    editor.putBoolean("isArctanActive", false);
                break;

            case R.id.checkbox_arccot:
                if (checked)
                    editor.putBoolean("isArccotActive", true);
                else
                    editor.putBoolean("isArccotActive", false);
                break;
        }

        editor.commit();

        updateFunctionStates();
    }

    public void reset(View v){

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putBoolean("isSinActive", true);
        editor.putBoolean("isCosActive", true);
        editor.putBoolean("isCscActive", true);
        editor.putBoolean("isSecActive", true);
        editor.putBoolean("isTanActive", true);
        editor.putBoolean("isCotActive", true);

        editor.putBoolean("isArcsinActive", true);
        editor.putBoolean("isArccosActive", true);
        editor.putBoolean("isArccscActive", true);
        editor.putBoolean("isArcsecActive", true);
        editor.putBoolean("isArctanActive", true);
        editor.putBoolean("isArccotActive", true);

        CheckBox checkbox_sin = (CheckBox) findViewById(R.id.checkbox_sin);
        CheckBox checkbox_cos = (CheckBox) findViewById(R.id.checkbox_cos);
        CheckBox checkbox_csc = (CheckBox) findViewById(R.id.checkbox_csc);
        CheckBox checkbox_sec = (CheckBox) findViewById(R.id.checkbox_sec);
        CheckBox checkbox_tan = (CheckBox) findViewById(R.id.checkbox_tan);
        CheckBox checkbox_cot = (CheckBox) findViewById(R.id.checkbox_cot);

        CheckBox checkbox_arcsin = (CheckBox) findViewById(R.id.checkbox_arcsin);
        CheckBox checkbox_arccos = (CheckBox) findViewById(R.id.checkbox_arccos);
        CheckBox checkbox_arccsc = (CheckBox) findViewById(R.id.checkbox_arccsc);
        CheckBox checkbox_arcsec = (CheckBox) findViewById(R.id.checkbox_arcsec);
        CheckBox checkbox_arctan = (CheckBox) findViewById(R.id.checkbox_arctan);
        CheckBox checkbox_arccot = (CheckBox) findViewById(R.id.checkbox_arccot);

        CheckBox[] functionCheckBoxes = {checkbox_sin, checkbox_cos, checkbox_csc, checkbox_sec, checkbox_tan, checkbox_cot,
                checkbox_arcsin, checkbox_arccos, checkbox_arccsc, checkbox_arcsec, checkbox_arctan, checkbox_arccot};

        for(int i = 0; i < functions.length; i++){
                functionCheckBoxes[i].setChecked(true);
        }

        editor.commit();

        updateFunctionStates();
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
