package com.hikmah.dewi.fastprint.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hikmah.dewi.fastprint.R;


public class AboutFragment extends Fragment {
    View rootView;
    public AboutFragment() {
        // Required empty public constructor
    }
    public static AboutFragment newInstance(){
        return new AboutFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=  inflater.inflate(R.layout.fragment_about, container, false);
        getActivity().setTitle("About");
        return rootView;
    }

}
