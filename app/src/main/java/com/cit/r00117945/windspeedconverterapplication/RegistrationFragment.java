/**
 * @author Csaba Farkas
 * Email: csaba.farkas@mycit.ie
 * Date of last modification: 02/11/2015
 */

package com.cit.r00117945.windspeedconverterapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Top fragment in RegistrationActivity. Fragment contains GUI components which allow user to enter
 * his/her name and email address and a button that confirms registration when clicked on.
 */
public class RegistrationFragment extends Fragment {

    private IUserRegister userRegister;
    private EditText nameEditText;
    private EditText emailEditText;

    /**
     * IUserRegister interface which is implemented by the Activity which contains this fragment.
     * Google's guideline is to let fragments communicate with each other via an Activity that implements
     * an interface:
     *
     * http://developer.android.com/training/basics/fragments/communicating.html
     */
    public interface IUserRegister {
        public void createUser(String userName, String email);
        public void finishActivity();
        public void displayRegistrationDetails(String registrationDetails);
    }

    /**
     * onCreateView sets the layout of the fragment and by calling the LayoutInflater.inflate method
     * it adjusts the size to the screen size of the device.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    /**
     * When fragment is attached to an activity it creates an IUserRegiste object (by casting
     * the container activity)
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.userRegister = (IUserRegister) activity;
    }


    /**
     * Method defining behavior when the containing activity is created. It initiates the GUI components
     * which will store data that need to be parsed or capture events that need to caught.
     * When user registers, IUserRegister.createUser method is called and all of the GUI components
     * stored in this fragment are disabled.
     * It also creates some text which is displayed in the ConfirmFragment. Text contains details of
     * user.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.nameEditText = (EditText) getActivity().findViewById(R.id.name_edit_text);
        this.emailEditText = (EditText) getActivity().findViewById(R.id.email_edit_text);
        final Button registerButton = (Button) getActivity().findViewById(R.id.reg_confirm);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEditText.getText().toString().equals("") || emailEditText.toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.all_fields_req), Toast.LENGTH_SHORT).show();
                    return;
                }

                nameEditText.setEnabled(false);
                emailEditText.setEnabled(false);
                registerButton.setEnabled(false);

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();

                userRegister.createUser(name, email);

                String display = getString(R.string.reg_conf_text) + "\n" + getString(R.string.name_text) +
                        name + "\n" + getString(R.string.email_text) + email;

                userRegister.displayRegistrationDetails(display);
            }
        });

    }
}
