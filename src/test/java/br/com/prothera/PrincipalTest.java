package br.com.prothera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrincipalTest {

    private List<Funcionario> funcionarios;

    @BeforeEach
    void prepararLista() {
        funcionarios = Principal.criarFuncionarios();
        Principal.removerFuncionarioPorNome(funcionarios, "João");
    }

    @Test
    void deveCriarTodosOsRegistrosNaOrdemDaTabela() {
        List<Funcionario> listaCompleta = Principal.criarFuncionarios();

        assertEquals(10, listaCompleta.size());
        assertEquals("Maria", listaCompleta.get(0).getNome());
        assertEquals("Helena", listaCompleta.get(9).getNome());
    }

    @Test
    void deveRemoverJoaoDaLista() {
        assertEquals(9, funcionarios.size());
        assertFalse(funcionarios.stream()
                .anyMatch(funcionario -> funcionario.getNome().equals("João")));
    }

    @Test
    void deveAgruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> grupos = Principal.agruparPorFuncao(funcionarios);

        assertEquals(7, grupos.size());
        assertEquals(2, grupos.get("Operador").size());
        assertEquals(2, grupos.get("Gerente").size());
    }

    @Test
    void deveFiltrarAniversariantesDeOutubroEDezembro() {
        List<String> nomes = Principal.filtrarAniversariantes(funcionarios, 10, 12)
                .stream()
                .map(Funcionario::getNome)
                .toList();

        assertEquals(List.of("Maria", "Miguel"), nomes);
    }

    @Test
    void deveEncontrarCaioComoFuncionarioMaisVelho() {
        Funcionario maisVelho = Principal.buscarFuncionarioMaisVelho(funcionarios)
                .orElseThrow();

        assertEquals("Caio", maisVelho.getNome());
        assertEquals(65, Principal.calcularIdade(
                maisVelho.getDataNascimento(), LocalDate.of(2026, 9, 12)));
    }

    @Test
    void deveOrdenarFuncionariosPorNome() {
        List<String> nomes = Principal.ordenarPorNome(funcionarios)
                .stream()
                .map(Funcionario::getNome)
                .toList();

        assertEquals(List.of("Alice", "Arthur", "Caio", "Heitor", "Helena",
                "Heloísa", "Laura", "Maria", "Miguel"), nomes);
    }

    @Test
    void deveSomarSalariosAposReajuste() {
        Principal.aplicarReajuste(funcionarios, new BigDecimal("0.10"));

        assertEquals(new BigDecimal("50906.82"),
                Principal.calcularTotalSalarios(funcionarios));
    }

    @Test
    void deveCalcularQuantidadeDeSalariosMinimos() {
        BigDecimal quantidade = Principal.calcularQuantidadeSalariosMinimos(
                new BigDecimal("2210.38"), new BigDecimal("1212.00"));

        assertEquals(new BigDecimal("1.82"), quantidade);
    }

    @Test
    void deveRejeitarSalarioMinimoIgualAZero() {
        assertThrows(IllegalArgumentException.class, () ->
            Principal.calcularQuantidadeSalariosMinimos(
                    BigDecimal.TEN, BigDecimal.ZERO));
    }
}
