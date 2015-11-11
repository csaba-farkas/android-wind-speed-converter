/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */
package com.cit.r00117945.windspeedconverterapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity which provides functionality for the user to enter his/her name and
 * email address, thus creating a mock registration. Activity consists of two fragments,
 * which are communicating with each other and with the activity (through IUserRegister interface).
 */
public class RegistrationActivity extends Activity implements RegistrationFragment.IUserRegister {

    private User user;
    private RegistrationFragment regFragment;
    private TextView confTextView;
    private Button confButton;

    /**
     * onCreate method constructs the activity. Sets up the layout and initialises some of the
     * instance variables.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.confTextView = (TextView) findViewById(R.id.conf_frag_text);
        this.confButton = (Button) findViewById(R.id.reg_confirm);
    }

    /**
     * createUser method is an implemented IUserRegister interface method. It creates a user object
     * using the parameters of the method. It also creates a notification which thanks the user for
     * registering and offering him/her to call a number for more fun with winds.
     *
     * @param userName String object indicating the user's name
     * @param email String object indicating the user's email address.
     */
    @Override
    public void createUser(String userName, String email) {
        this.user = new User(userName, email);
        this.regFragment = (RegistrationFragment) getFragmentManager().findFragmentById(R.id.reg_fragment);
        //Create notification
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(getString(R.string.thank_reg))
                .setContentText(getString(R.string.notif_text));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0211234567"));

        PendingIntent pendingCallIntent = PendingIntent.getActivity(this, 0, callIntent, 0);

        nBuilder.setContentIntent(pendingCallIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(0, nBuilder.build());

    }

    /**
     * Implemented IUserRegister method. If registration was successful (Register button was pressed with
     * both of the EditTexts containing values) it sends back the data (user's details) RESULT_OK
     * resultCode. If user presses on the Go Back button before finishing registration, data isn't sent
     * back, and resultCode is set to RESULT_CANCELED.
     */
    @Override
    public void finishActivity() {
        if (this.user != null) {
            Bundle bundle = getIntent().getExtras();
            bundle.putString("userName", this.user.getName());
            bundle.putString("userEmail", this.user.getEmail());
            getIntent().putExtra("resultBundle", bundle);
            setResult(RESULT_OK, getIntent());
        } else {
            setResult(RESULT_CANCELED, getIntent());
        }
        finish();
    }

    /**
     * If registration was successful (confTextView contains text) then save the text of confTextView,
     * userName, userEmail.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!confTextView.getText().toString().equals("")) {
            String text = confTextView.getText().toString();
            outState.putString("confTextString", text);
            String userName = this.user.getName();
            String userEmail = this.user.getEmail();
            outState.putString("userName", userName);
            outState.putString("userEmail", userEmail);
        }
    }

    /**
     * If bundle contains the text parsed from confTextView, then it contains
     * every other variable. Parse all the data, restore confTextView instance variable,
     * user object and the text in confTextView.
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState.getString("confTextString") != null) {
            this.confTextView.setText(savedInstanceState.getString("confTextString"));
            this.user = new User(savedInstanceState.getString("userName"), savedInstanceState.getString("userEmail"));
            this.confButton.setEnabled(false);
            findViewById(R.id.name_edit_text).setEnabled(false);
            findViewById(R.id.email_edit_text).setEnabled(false);
        }
    }

    /**
     * Implemented IUserRegister interface method. Displays the results of the registration in
     * RegistrationFragment and displays it in ConfirmFragment's TextView. In this way Fragments are
     * not communicating with each other directly.
     */
    @Override
    public void displayRegistrationDetails(String registrationDetails) {
        TextView textView = (TextView) findViewById(R.id.conf_frag_text);
        textView.setText(registrationDetails);
    }

}
