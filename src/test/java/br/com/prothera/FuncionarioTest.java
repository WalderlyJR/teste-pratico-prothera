package br.com.prothera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    @Test
    void deveAplicarReajusteComPrecisaoMonetaria() {
        Funcionario funcionario = new Funcionario(
                "Maria", LocalDate.of(2000, 10, 18),
                new BigDecimal("2009.44"), "Operador");

        funcionario.aplicarReajuste(new BigDecimal("0.10"));

        assertEquals(new BigDecimal("2210.38"), funcionario.getSalario());
    }

    @Test
    void naoDeveAceitarSalarioNegativo() {
        LocalDate nascimento = LocalDate.of(2000, 10, 18);

        assertThrows(IllegalArgumentException.class, () ->
                new Funcionario("Maria", nascimento,
                        new BigDecimal("-1.00"), "Operador"));
    }
}

