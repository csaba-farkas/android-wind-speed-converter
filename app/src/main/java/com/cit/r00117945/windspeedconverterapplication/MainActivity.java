/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */
package com.cit.r00117945.windspeedconverterapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main activity that which launches when the application is started.
 * The main functions are:
 * 1. Entering a value to convert
 * 2. Selecting "from" and "to" values from spinners
 * 3. Converting the value
 * 4. Allowing users to register
 */
public class MainActivity extends AppCompatActivity {

    //This values are parallel with the index numbers of measures in spinner 2
    protected final static int BEAUFORT_INDEX = 0;
    protected final static int KM_PER_HOUR_INDEX = 1;
    protected final static int MILES_PER_HOUR_INDEX = 2;
    protected final static int KNOTS_INDEX = 3;
    protected final static int METER_PER_SECOND_INDEX = 4;
    protected final static int FEET_PER_SECOND_INDEX = 5;

    protected final static int REQUEST_GET_USER = 10;

    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button convertButton;
    private Button registerButton;
    private EditText editText;
    private String userName;
    private TextView welcomeText;

    /**
     * onCreate activity creates finds the instance variable UI elements
     * in the layout file and initiates them.
     * It parses the value entered into the EditText object. Creates a
     * WindSpeedCalculator object that converts the value to the desired
     * measure. Then it sends the results to ResultActivity to display.
     * It also offers users to register by navigating to a third activity.
     * Data sent back from the third activity, after registration (name)
     * is displayed in the top TextView.
     *
     * @param savedInstanceState a Bundle
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fromSpinner = (Spinner) findViewById(R.id.from_spinner);
        this.toSpinner = (Spinner) findViewById(R.id.to_spinner);
        this.convertButton = (Button) findViewById(R.id.convert_button);
        this.editText = (EditText) findViewById(R.id.number_edittext);
        this.convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double originalValue = 0;
                if (!editText.getText().toString().equals("")) {
                    originalValue = Integer.parseInt(editText.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_number_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Double.parseDouble(editText.getText().toString()) > 12 && fromSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.beaufort_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                int measureFrom = fromSpinner.getSelectedItemPosition();
                int measureTo = toSpinner.getSelectedItemPosition();

                WindSpeedCalculator calculator;
                FromBeaufortCalculator bCalculator;

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Bundle sentBundle = new Bundle();
                String result;

                switch (fromSpinner.getSelectedItemPosition()) {
                    case 0:
                        bCalculator = new FromBeaufortCalculator(originalValue, measureFrom, measureTo);
                        result = bCalculator.getConvertedString();
                        sentBundle.putBoolean("isWindStrong", bCalculator.getIsWindStrong());
                        break;
                    default:
                        calculator = new WindSpeedCalculator(originalValue, measureFrom, measureTo);
                        result = calculator.getConvertedValue().toString();
                        sentBundle.putBoolean("isWindStrong", calculator.getIsWindStrong());
                }

                sentBundle.putString("convertedValue", result);
                sentBundle.putString("measureFrom", (String) fromSpinner.getSelectedItem());
                sentBundle.putString("measureTo", (String) toSpinner.getSelectedItem());
                sentBundle.putDouble("originalValue", originalValue);
                intent.putExtra("bundle", sentBundle);
                startActivity(intent);
            }
        });

        registerButton = (Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                intent.putExtra("user", new Bundle());
                startActivityForResult(intent, REQUEST_GET_USER);
            }
        });
    }

    /**
     * When RegistrationActivity sends back data to this activity, this method is
     * called. If resultCode is set to RESULT_OK, it parses the userName from the
     * sent Bundle and initiates userName. It also disables Register button so only
     * one registration is allowed.
     *
     * @param requestCode an integer indicating the request code
     * @param resultCode an integer indicating the result code (RESULT_OK or RESULT_CANCEL)
     * @param data an Intent containing the data sent back from RegistrationActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bundle resultBundle = bundle.getBundle("resultBundle");
            this.userName = resultBundle.get("userName").toString();
            this.welcomeText = (TextView) findViewById(R.id.welcome_text);
            this.welcomeText.setText(this.welcomeText.getText() + " " + userName);
            this.registerButton.setEnabled(false);
        }

    }

    /**
     * Save value entered into EditText, userName (if exists)
     * and the position of the two spinners.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save username
        if(this.userName != null) {
            outState.putString("userName", this.userName);
        }
    }

    /**
     * Restore the values of elements that were saved in the previous
     * method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Restore username if exists
        if(savedInstanceState.getString("userName") != null) {
            this.userName = savedInstanceState.getString("userName");
            this.welcomeText = (TextView) findViewById(R.id.welcome_text);
            this.welcomeText.setText(this.welcomeText.getText() + " " + userName);
            this.registerButton.setEnabled(false);
        }
    }


}
