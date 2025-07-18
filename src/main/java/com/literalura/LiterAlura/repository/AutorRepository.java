package com.literalura.repository;

import com.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Adicionar esta importação
import org.springframework.stereotype.Repository;

import java.util.List; // Adicionar esta importação
import java.util.Optional; // Adicionar esta importação

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Metodo para buscar autores por nome, ignorando maiúsculas/minúsculas
    // Usado para verificar se o autor já existe
    Autor findByNomeContainsIgnoreCase(String nome);

    // Consulta JPQL para buscar autores vivos em um determinado ano
    // Este metodo é usado em Principal.java:188
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosEmAno(Integer ano);
}