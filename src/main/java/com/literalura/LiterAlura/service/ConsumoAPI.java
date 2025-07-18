package com.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obterDados(String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.err.println("Erro de I/O ao consumir a API: " + e.getMessage());
            throw new RuntimeException("Não foi possível conectar à API. Verifique sua conexão.", e);
        } catch (InterruptedException e) {
            System.err.println("A requisição foi interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
            throw new RuntimeException("Requisição à API interrompida.", e);
        }

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.err.println("Erro na resposta da API. Código de status: " + response.statusCode());
            return "";
        }
    }
}