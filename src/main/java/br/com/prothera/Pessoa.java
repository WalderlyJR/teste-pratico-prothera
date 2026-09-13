package br.com.prothera;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa os dados comuns de uma pessoa.
 */
public class Pessoa {

    private final String nome;
    private final LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = validarTexto(nome, "nome");
        this.dataNascimento = Objects.requireNonNull(
                dataNascimento, "A data de nascimento não pode ser nula.");
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Centraliza a validação de campos textuais obrigatórios.
     */
    protected static String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }
}

