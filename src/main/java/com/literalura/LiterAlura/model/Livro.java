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

    @ManyToOne // Agora, Livro se relaciona com Autor
    @JoinColumn(name = "autor_id") // Define a coluna da chave estrangeira na tabela 'livros'
    private Autor autor; // << ESTE ATRIBUTO É UMA REFERÊNCIA AO OBJETO AUTOR

    private String idioma;
    private Double numeroDownloads;

    public Livro() {}

    public Livro(String titulo, Autor autor, String idioma, Double numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "Título: '" + titulo + '\'' +
                ", Autor: '" + (autor != null ? autor.getNome() : "Desconhecido") + '\'' +
                ", Idioma: '" + idioma + '\'' +
                ", Downloads: " + numeroDownloads;
    }
}