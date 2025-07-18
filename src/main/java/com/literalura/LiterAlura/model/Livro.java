package com.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    private String idioma;
    private Double numeroDownloads;

    public Livro() {}

    public Livro(String titulo, Autor autor, String idioma, Double numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Título: '" + titulo + '\'' +
                ", Autor: '" + (autor != null ? autor.getNome() : "Desconhecido") + '\'' +
                ", Idioma: '" + idioma + '\'' +
                ", Downloads: " + numeroDownloads;
    }
}