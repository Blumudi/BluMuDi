package com.example.drawer;

//import com.example.database.DataBaseManager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        TabWithSwipe.OnFragmentInteractionListener {

    private CharSequence mTitle;

    public Boolean check = false;
//    private DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inserts de prueba
//      manager = new DataBaseManager(this);
//      manager.insertar("Lose Yourself to Dance", "Random Access Memories", "Daft Punk");
//      manager.insertar("Touch", "Random Access Memories", "Daft Punk");
//      manager.insertar("Nothing For Me Here [Bonus Track]", "No Regrets", "Dope");
//      manager.insertar("I Don't Give A... [Bonus Track]", "No Regrets", "Dope");
//      manager.insertar("Rebel Yell [Bonus Track]", "No Regrets", "Dope");
        
    }

    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setTitle("Salir")
                .setMessage("Esta usted seguro?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentBack = new Intent(Intent.ACTION_MAIN);
                        intentBack.addCategory(Intent.CATEGORY_HOME);
                        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentBack);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();

        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TabWithSwipe.newInstance(null, null)).commit();
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, AboutUs.newInstance(null, null)).commit();
                break;
            case 2:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void onSectionAttached(int number) {
        ActionBar actionBar = getActionBar();
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;/*
            case 4:
                mTitle = getString(R.string.title_section4);
                break;*/
        }
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

}
