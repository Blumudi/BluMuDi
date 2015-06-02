package com.example.drawer;

public class Cancion {

    private String titulo;
    private String album;
    private String artista;
    private Boolean fav;

    public Cancion() {
    }

    public Cancion(String titulo, String album, String artista, Boolean fav) {
        this.titulo = titulo;
        this.album = album;
        this.artista = artista;
        this.fav = fav;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) { this.artista = artista; }

    public Boolean getFav() { return fav; }

    public void setFav(Boolean fav) { this.fav = fav; }


    @Override
    public String toString() {
        return "Cancion{" +
                "titulo='" + titulo + '\'' +
                ", album='" + album + '\'' +
                ", artista='" + artista + '\'' +
                ", fav=" + fav +
                '}';
    }
}
