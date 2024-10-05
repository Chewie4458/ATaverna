package com.example.ataverna;

import java.io.Serializable;

public class Album implements Serializable {
    private String nome;
    private String artista;
    private String url;

    public Album()  {}

    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getArtista()
    {
        return artista;
    }
    public void setArtista(String artista)
    {
        this.artista = artista;
    }

    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
}
