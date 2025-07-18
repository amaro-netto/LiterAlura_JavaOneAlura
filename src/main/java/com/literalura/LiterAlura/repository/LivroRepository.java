package com.literalura.repository;

import com.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Indica que esta interface é um componente de repositório do Spring
// Estende JpaRepository, que fornece métodos CRUD (Create, Read, Update, Delete)
// Os parâmetros são: <Tipo da Entidade, Tipo da Chave Primária>
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Metodo para verificar se um livro com o título já existe, ignorando maiúsculas/minúsculas
    Optional<Livro> findByTituloContainsIgnoreCase(String titulo);

    // Metodo para buscar livros por idioma
    List<Livro> findByIdioma(String idioma);

    // Metodo para encontrar os 10 livros com mais downloads, ordenados de forma decrescente
    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();
}