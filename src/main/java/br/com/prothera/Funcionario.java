package br.com.prothera;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Especialização de Pessoa que acrescenta os dados profissionais.
 */
public class Funcionario extends Pessoa {

    private BigDecimal salario;
    private final String funcao;

    public Funcionario(String nome, LocalDate dataNascimento,
                       BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        setSalario(salario);
        this.funcao = validarTexto(funcao, "função");
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        Objects.requireNonNull(salario, "O salário não pode ser nulo.");
        if (salario.signum() < 0) {
            throw new IllegalArgumentException("O salário não pode ser negativo.");
        }
        // Salários são valores monetários e, por isso, ficam com duas casas decimais.
        this.salario = salario.setScale(2, RoundingMode.HALF_UP);
    }

    public String getFuncao() {
        return funcao;
    }

    /**
     * Aplica o percentual informado ao salário atual usando BigDecimal,
     * evitando as imprecisões de tipos de ponto flutuante como double.
     */
    public void aplicarReajuste(BigDecimal percentual) {
        Objects.requireNonNull(percentual, "O percentual não pode ser nulo.");
        if (percentual.signum() < 0) {
            throw new IllegalArgumentException("O percentual não pode ser negativo.");
        }

        BigDecimal fatorDeReajuste = BigDecimal.ONE.add(percentual);
        setSalario(salario.multiply(fatorDeReajuste));
    }
}

