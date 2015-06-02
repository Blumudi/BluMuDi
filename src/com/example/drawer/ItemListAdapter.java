package com.example.drawer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by x54hr on 19/05/15.
 */
public class ItemListAdapter extends BaseAdapter {

    private Activity activity;
    private List<Cancion> listCanciones;


    public ItemListAdapter(Activity activity, List<Cancion> listCanciones){
        this.activity = activity;
        this.listCanciones = listCanciones;
    }

    public int getCount() {
        return listCanciones.size();
    }

    public Object getItem(int position) {
        return listCanciones.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ItemList lstItem = new ItemList(activity, listCanciones.get(position) );

        return lstItem;
    }

}