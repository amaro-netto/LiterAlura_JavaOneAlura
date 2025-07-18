package com.literalura.service;

public interface IConverteDados {
    // Método genérico para converter JSON para um objeto de qualquer tipo (T)
    <T> T obterDados(String json, Class<T> classe);
}