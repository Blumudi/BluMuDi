package com.example.drawer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutUs extends Fragment {

	public static AboutUs newInstance(String param1, String param2) {
		AboutUs fragment = new AboutUs();
		
		return fragment;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us, container, false);
    }

	@SuppressWarnings("unused")
	private void finish() {
	}


	@SuppressWarnings("unused")
	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Determines whether to always show the simplified settings UI, where
	 * settings are presented in a single list. When false, settings are shown
	 * as a master/detail two-pane view on tablets. When true, a single pane is
	 * shown on tablets.
	 */



}

