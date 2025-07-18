package com.literalura.repository;

import com.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Indica que esta interface é um componente de repositório do Spring
// Estende JpaRepository, que fornece métodos CRUD (Create, Read, Update, Delete)
// Os parâmetros são: <Tipo da Entidade, Tipo da Chave Primária>
public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Metodo para buscar autores por nome, ignorando maiúsculas/minúsculas
    Autor findByNomeContainsIgnoreCase(String nome);

    // Consulta JPQL para buscar autores vivos em um determinado ano
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosEmAno(Integer ano);

    // Metodo para listar autores em ordem alfabética pelo nome (ascendente)
    List<Autor> findByOrderByNomeAsc();
}