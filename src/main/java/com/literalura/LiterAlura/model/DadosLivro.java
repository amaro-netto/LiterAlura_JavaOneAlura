package com.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias; // Para mapear campos JSON com nomes diferentes
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Para ignorar propriedades que não precisamos

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos JSON que não estão nesta classe
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") DadosAutor[] autores, // Pode ter múltiplos autores
        @JsonAlias("languages") String[] idiomas, // Pode ter múltiplos idiomas
        @JsonAlias("download_count") Double numeroDownloads
) {}