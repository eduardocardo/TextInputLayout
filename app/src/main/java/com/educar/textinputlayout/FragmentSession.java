package com.educar.textinputlayout;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSession extends Fragment {




    public static FragmentSession newInstance() {
        FragmentSession fragment = new FragmentSession();
        return fragment;
    }

    public FragmentSession(){};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_session, container, false);
        return view;
    }
}
