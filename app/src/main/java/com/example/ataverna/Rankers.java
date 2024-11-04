package com.example.ataverna;

public class Rankers {

    private String nome;
    private String comentario;
    private String album;
    private String nota;
    private Long hora;

    public String getNome() {
        return nome;
    }

    public String setNome(String nome) {
        return this.nome = nome;
    }

    public String getComentario() {
        return comentario;
    }

    public String setComentario(String comentario) {
        return this.comentario = comentario;
    }

    public String getAlbum() {
        return album;
    }

    public String setAlbum(String album) {
        return this.album = album;
    }

    public String getNota() {
        return nota;
    }

    public String setNota(String nota) {
        return this.nota = nota;
    }

    public Long getHora() { return hora; }

    public void setHora(Long hora) {
        this.hora = hora;
    }

}
