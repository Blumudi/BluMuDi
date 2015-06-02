package com.example.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by x54hr on 19/05/15.
 */


public class ItemList extends LinearLayout {


    private TextView lblTitulo, lblAlbum, lblArtist, lblFav;
    private ImageView likebutton;


    private Cancion cancion;

    public ItemList(Context context, Cancion cancion) {
        super(context);
        this.cancion = cancion;
        inicializar();
    }

    private void inicializar() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.item_list, this, true);

        lblTitulo = (TextView) findViewById(R.id.lblTitulo);
        lblAlbum = (TextView) findViewById(R.id.lblAlbum);
        lblArtist = (TextView) findViewById(R.id.lblArtist);
        lblFav = (TextView) findViewById(R.id.lblFav);
        likebutton = (ImageView) findViewById(R.id.likeheart);


        lblTitulo.setText(cancion.getTitulo());
        lblAlbum.setText(cancion.getAlbum());
        lblArtist.setText(cancion.getArtista());
        String s = cancion.getFav().toString();
        lblFav.setText(s);



        if(s.equals("1")){
            likebutton.setImageDrawable(getResources().getDrawable(R.drawable.heart_red));
        }else{
            likebutton.setImageDrawable(getResources().getDrawable(R.drawable.heart_white));

        }


    }

}
