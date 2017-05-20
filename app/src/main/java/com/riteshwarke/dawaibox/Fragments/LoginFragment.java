package com.riteshwarke.dawaibox.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.riteshwarke.dawaibox.Activities.MainActivity;
import com.riteshwarke.dawaibox.Helpers.AppConstants;
import com.riteshwarke.dawaibox.Helpers.General;
import com.riteshwarke.dawaibox.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.password)
    TextView password;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }


    @OnClick({R.id.btn_login})
    public void onButtonsClick(View v) {


        if (btn_login.getId() == v.getId()) {
            if(username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
                Toast.makeText(getContext(), "All fields are mandatory!!", Toast.LENGTH_SHORT).show();
            }
            else if(username.getText().toString().equals("Ritesh") && password.getText().toString().equals("123")){
                General.setSharedPreferences(getContext(), AppConstants.IS_LOGGED_IN_USER,"yes");
                SplashFragment splashfragment = new SplashFragment();
                ((MainActivity)getActivity()).loadFragment(splashfragment, null, R.id.my_container, "null");
            }
            else{
                Toast.makeText(getContext(), "Wrong Credentials!!", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void init() {

    }

}
