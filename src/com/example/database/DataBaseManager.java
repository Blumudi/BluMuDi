package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
Ejemplo de operaciones
manager.insertar("Sweet child o' mine1","Welcome to the jungle1","Guns N' Roses1");
manager.eliminar("Sweet child o' mine1");
manager.eliminarTodo();
*/

public class DataBaseManager {
    public static final String TABLE_NAME = "canciones";

    public static final String CN_ID = "_id";
    public static final String CN_TITLE = "title";
    public static final String CN_ALBUM = "album";
    public static final String CN_ARTIST = "artist";
    public static final String CN_FAV = "fav";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_TITLE + " text not null,"
            + CN_ARTIST + " text,"
            + CN_ALBUM + " text,"
            + CN_FAV + " boolean);";


    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String title, String album, String artist, Boolean fav ){
        ContentValues valores = new ContentValues();
        valores.put(CN_TITLE,title);
        valores.put(CN_ALBUM,album);
        valores.put(CN_ARTIST, artist);
        valores.put(CN_FAV, fav);
        return valores;
    }

    public void insertar (String title, String album, String artist){
        db.insert(TABLE_NAME, null, generarContentValues(title, album, artist, false));

    }

    public Boolean find (String title, String album, String artist){
        String[] columnas = new String[]{CN_ID,CN_TITLE,CN_ALBUM,CN_ARTIST, CN_FAV};
        Boolean hay =null;
        Cursor findEntry = db.query(TABLE_NAME, columnas, CN_TITLE + "=? and " + CN_ALBUM + "=? and " + CN_ARTIST + "=?", new String[] { title, album,artist }, null, null, null);
        if(findEntry!=null && findEntry.getCount()>0){
            hay = true;
        }

        return hay;
    }

    public void insertar2 (String title, String artist, String album){
        db.execSQL("insert into " + TABLE_NAME + " values (null,'" + title + "'," + album + "','" + artist + "')");

    }

    public void eliminar (String title){
        db.delete(TABLE_NAME, CN_TITLE + "=?", new String[]{title});
    }

    public void eliminarTodo(){
        db.execSQL("delete from " + TABLE_NAME);
    }

    public void modificarTitulo (String title, String album, String artist, Boolean fav){
        db.update(TABLE_NAME, generarContentValues(title, album, artist, fav), CN_TITLE + "=?", new String[]{title});
    }

    public Cursor cargarCursorCanciones(){
        String[] columnas = new String[]{CN_ID,CN_TITLE,CN_ALBUM,CN_ARTIST, CN_FAV};
        return db.query(TABLE_NAME,columnas, null, null, null, null,null);
    }

    public Cursor cargarFavoritos(){
        String favorito = "1";
        String[] columnas = new String[]{CN_ID,CN_TITLE,CN_ALBUM,CN_ARTIST, CN_FAV};
        return db.query(TABLE_NAME, columnas,CN_FAV + "=?",new String[]{favorito},null,null,null);
    }
    
}
