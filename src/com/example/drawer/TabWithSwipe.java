package com.example.drawer;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabWithSwipe extends Fragment implements ActionBar.TabListener {
//	// TODO: Rename parameter arguments, choose names that match
//	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//	private static final String ARG_PARAM1 = "param1";
//	private static final String ARG_PARAM2 = "param2";
//
//	// TODO: Rename and change types of parameters
//	private String mParam1;
//	private String mParam2;
//	private int check;
//
//	// Intent request codes
//	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
//	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
//	private static final int REQUEST_ENABLE_BT = 3;
//
//	// Layout Views
//	private ListView mConversationView;
//	private EditText mOutEditText;
//	private Button mSendButton;
//	private BluetoothAdapter mBluetoothAdapter = null;


	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	private OnFragmentInteractionListener mListener;

	public static TabWithSwipe newInstance(String param1, String param2) {
		TabWithSwipe fragment = new TabWithSwipe();
		Bundle args = new Bundle();
//		args.putString(ARG_PARAM1, param1);
//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TabWithSwipe() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//		if (getArguments() != null) {
//			mParam1 = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
//		}
//		if (!mBluetoothAdapter.isEnabled()) {
//			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//			check = REQUEST_ENABLE_BT;}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container,
				false);
	}

	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


	public interface OnFragmentInteractionListener {

		public void onFragmentInteraction(Uri uri);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getFragmentManager());

		mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);

		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		mViewPager.setCurrentItem(0);
		actionBar.setSelectedNavigationItem(0);

	}



	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
//		case REQUEST_CONNECT_DEVICE_SECURE:
//			// When DeviceListActivity returns with a device to connect
//			if (resultCode == Activity.RESULT_OK) {
//				//connectDevice(data, true);
//			}
//			break;
//		case REQUEST_CONNECT_DEVICE_INSECURE:
//			// When DeviceListActivity returns with a device to connect
//			if (resultCode == Activity.RESULT_OK) {
//				//connectDevice(data, false);
//			}
//			break;
//		case REQUEST_ENABLE_BT:
//			// When the request to enable Bluetooth returns
//			if (resultCode == Activity.RESULT_OK) {
//				// Bluetooth is now enabled, so set up a chat session
//				//setupChat();
//			} else {
//				// User did not enable Bluetooth or an error occurred
//				//  Log.d(TAG, "BT not enabled");
//				Toast.makeText(getActivity(),"bluetooth no activado ... Saliendo.",Toast.LENGTH_SHORT).show();
//				getActivity().finish();
//			}
		}
	}



	@Override
	public void onDestroyView() {
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.removeAllTabs();
		mSectionsPagerAdapter = null;
		mViewPager = null;
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onDestroyView();
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {

		mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {
		
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {
		
	}


	class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_sub_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_sub_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_sub_section3).toUpperCase(l);
			}
			return null;
		}
	}

}
