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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.cit.r00117945.windspeedconverterapplication.RegistrationFragment.IUserRegister;

/**
 * Fragment which contains a TextView object and a Go Back button.
 */
public class ConfirmFragment extends Fragment {

    private RegistrationFragment.IUserRegister userRegister;

    /**
     * onCreateView method sets the appropriate layout and inflates it.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirm, container, false);

    }

    /**
     * A userRegister object is created when this fragment is attached to the activity.
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userRegister = (IUserRegister) activity;
    }

    /**
     * This is the "constructor" method. It attaches an onClickListener to Go Back button which
     * fires an event that calls the IUserRegister.finishActivity() method.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button goBackButton = (Button) getActivity().findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegister.finishActivity();
            }
        });
    }


}
