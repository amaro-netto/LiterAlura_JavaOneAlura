package com.literalura.service;

public interface IConverteDados {
    // Metodo genérico para converter JSON para um objeto de qualquer tipo (T)
    <T> T obterDados(String json, Class<T> classe);
}