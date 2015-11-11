/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */
package com.cit.r00117945.windspeedconverterapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {

    /**
     * onCreate method creates references to GUI components. It creates the string which contains
     * the results of the conversion and displays it in the TextView object.
     * It also creates an AlertDialog that appears when wind speed is too high offering user to
     * call for help.
     * A "Conver Again" button navigates the user back to the MainActivity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle sentBundle = getIntent().getExtras();
        TextView topTextView = (TextView) findViewById(R.id.top_result_text);
        TextView bottomTextView = (TextView) findViewById(R.id.bottom_result_text);
        Bundle parsedBundle = sentBundle.getBundle("bundle");

        String topText = parsedBundle.getDouble("originalValue") + " " + parsedBundle.getString("measureFrom") + " " + getString(R.string.english_is);
        String bottomText = parsedBundle.getString("convertedValue") + " " + parsedBundle.getString("measureTo");

        topTextView.setText(topText);
        bottomTextView.setText(bottomText);


        if(parsedBundle.getBoolean("isWindStrong")) {

            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage(getString(R.string.alert_dialog_text));

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.neutral_button),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.negative_button),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.positive_button),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse(getString(R.string.emergency_num)));
                            startActivity(intent);
                        }
                    });
            dialog.show();
        }

        Button goBack = (Button) findViewById(R.id.again_button);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
