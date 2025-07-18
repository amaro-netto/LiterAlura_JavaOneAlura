package com.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient; // Classe HttpClient 
import java.net.http.HttpRequest; // Classe HttpRequest 
import java.net.http.HttpResponse; // Interface HttpResponse 

public class ConsumoAPI {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient(); // Cria uma nova instância de HttpClient
        HttpRequest request = HttpRequest.newBuilder() // Constrói a requisição
                .uri(URI.create(endereco)) // Define a URI para a requisição
                .build();
        HttpResponse<String> response = null; // Resposta da requisição HTTP

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Envia a requisição e recebe a resposta como String
        } catch (IOException | InterruptedException e) {
            // Trata exceções de IO (entrada/saída) ou interrupção
            e.printStackTrace();
        }
        String json = response.body(); // Obtém o corpo da resposta (JSON)
        return json; // Retorna o JSON
    }
}