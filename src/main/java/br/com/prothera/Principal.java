package br.com.prothera;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Executa todos os itens solicitados no teste prático.
 */
public class Principal {

    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final BigDecimal PERCENTUAL_REAJUSTE = new BigDecimal("0.10");
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    private Principal() {
        // Impede a instanciação: esta classe concentra apenas operações do programa.
    }

    public static void main(String[] args) {
        // 3.1 - Inclusão na mesma ordem apresentada na tabela do enunciado.
        List<Funcionario> funcionarios = criarFuncionarios();

        // 3.2 - Remoção de João.
        removerFuncionarioPorNome(funcionarios, "João");

        // 3.3 - Impressão de todos os dados antes do reajuste.
        imprimirTitulo("3.3 - FUNCIONÁRIOS");
        funcionarios.forEach(Principal::imprimirFuncionarioCompleto);

        // 3.4 - Atualização de cada salário com aumento de 10%.
        aplicarReajuste(funcionarios, PERCENTUAL_REAJUSTE);

        // 3.5 - LinkedHashMap preserva a ordem em que as funções aparecem na lista.
        Map<String, List<Funcionario>> funcionariosPorFuncao =
                agruparPorFuncao(funcionarios);

        // 3.6 - Impressão por função.
        imprimirTitulo("3.6 - FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO (APÓS REAJUSTE)");
        imprimirAgrupadosPorFuncao(funcionariosPorFuncao);

        // O enunciado não contém o item 3.7.

        // 3.8 - Aniversariantes de outubro e dezembro.
        imprimirTitulo("3.8 - ANIVERSARIANTES DOS MESES 10 E 12");
        filtrarAniversariantes(funcionarios, 10, 12)
                .forEach(Principal::imprimirNomeEDataNascimento);

        // 3.9 - A menor data de nascimento identifica o funcionário mais velho.
        imprimirTitulo("3.9 - FUNCIONÁRIO COM MAIOR IDADE");
        buscarFuncionarioMaisVelho(funcionarios).ifPresent(funcionario -> {
            int idade = calcularIdade(funcionario.getDataNascimento(), LocalDate.now());
            System.out.printf("Nome: %s | Idade: %d anos%n", funcionario.getNome(), idade);
        });

        // 3.10 - A lista original não é alterada durante a ordenação.
        imprimirTitulo("3.10 - FUNCIONÁRIOS EM ORDEM ALFABÉTICA");
        ordenarPorNome(funcionarios).forEach(Principal::imprimirFuncionarioCompleto);

        // 3.11 - Soma dos salários já reajustados.
        imprimirTitulo("3.11 - TOTAL DOS SALÁRIOS");
        System.out.println("Total: " + formatarMoeda(calcularTotalSalarios(funcionarios)));

        // 3.12 - Quantidade de salários mínimos com duas casas decimais.
        imprimirTitulo("3.12 - SALÁRIOS MÍNIMOS POR FUNCIONÁRIO");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidade = calcularQuantidadeSalariosMinimos(
                    funcionario.getSalario(), SALARIO_MINIMO);
            System.out.printf("%s: %s salários mínimos%n",
                    funcionario.getNome(), formatarNumero(quantidade));
        });
    }

    /**
     * Monta os dados fornecidos na tabela original do teste.
     */
    public static List<Funcionario> criarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(novoFuncionario("Maria", 2000, 10, 18, "2009.44", "Operador"));
        funcionarios.add(novoFuncionario("João", 1990, 5, 12, "2284.38", "Operador"));
        funcionarios.add(novoFuncionario("Caio", 1961, 5, 2, "9836.14", "Coordenador"));
        funcionarios.add(novoFuncionario("Miguel", 1988, 10, 14, "19119.88", "Diretor"));
        funcionarios.add(novoFuncionario("Alice", 1995, 1, 5, "2234.68", "Recepcionista"));
        funcionarios.add(novoFuncionario("Heitor", 1999, 11, 19, "1582.72", "Operador"));
        funcionarios.add(novoFuncionario("Arthur", 1993, 3, 31, "4071.84", "Contador"));
        funcionarios.add(novoFuncionario("Laura", 1994, 7, 8, "3017.45", "Gerente"));
        funcionarios.add(novoFuncionario("Heloísa", 2003, 5, 24, "1606.85", "Eletricista"));
        funcionarios.add(novoFuncionario("Helena", 1996, 9, 2, "2799.93", "Gerente"));
        return funcionarios;
    }

    private static Funcionario novoFuncionario(String nome, int ano, int mes, int dia,
                                               String salario, String funcao) {
        return new Funcionario(nome, LocalDate.of(ano, mes, dia),
                new BigDecimal(salario), funcao);
    }

    public static boolean removerFuncionarioPorNome(List<Funcionario> funcionarios,
                                                     String nome) {
        return funcionarios.removeIf(funcionario ->
                funcionario.getNome().equalsIgnoreCase(nome));
    }

    public static void aplicarReajuste(List<Funcionario> funcionarios,
                                       BigDecimal percentual) {
        funcionarios.forEach(funcionario -> funcionario.aplicarReajuste(percentual));
    }

    public static Map<String, List<Funcionario>> agruparPorFuncao(
            List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getFuncao,
                        LinkedHashMap::new,
                        Collectors.toList()));
    }

    public static List<Funcionario> filtrarAniversariantes(
            List<Funcionario> funcionarios, int... meses) {
        List<Integer> mesesSelecionados = java.util.Arrays.stream(meses)
                .boxed()
                .toList();

        return funcionarios.stream()
                .filter(funcionario -> mesesSelecionados.contains(
                        funcionario.getDataNascimento().getMonthValue()))
                .toList();
    }

    public static Optional<Funcionario> buscarFuncionarioMaisVelho(
            List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento));
    }

    public static int calcularIdade(LocalDate dataNascimento, LocalDate dataReferencia) {
        return Period.between(dataNascimento, dataReferencia).getYears();
    }

    public static List<Funcionario> ordenarPorNome(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .sorted(Comparator.comparing(
                        Funcionario::getNome,
                        String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public static BigDecimal calcularTotalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal calcularQuantidadeSalariosMinimos(
            BigDecimal salario, BigDecimal salarioMinimo) {
        if (salarioMinimo == null || salarioMinimo.signum() <= 0) {
            throw new IllegalArgumentException("O salário mínimo deve ser maior que zero.");
        }
        return salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    private static void imprimirAgrupadosPorFuncao(
            Map<String, List<Funcionario>> funcionariosPorFuncao) {
        funcionariosPorFuncao.forEach((funcao, funcionarios) -> {
            System.out.println("\nFunção: " + funcao);
            // Exibe todos os dados para deixar inequívoco que cada funcionário
            // pertence à lista associada à função apresentada.
            funcionarios.forEach(Principal::imprimirFuncionarioCompleto);
        });
    }

    private static void imprimirFuncionarioCompleto(Funcionario funcionario) {
        System.out.printf("Nome: %-8s | Nascimento: %s | Salário: %s | Função: %s%n",
                funcionario.getNome(),
                funcionario.getDataNascimento().format(FORMATO_DATA),
                formatarMoeda(funcionario.getSalario()),
                funcionario.getFuncao());
    }

    private static void imprimirNomeEDataNascimento(Funcionario funcionario) {
        System.out.printf("Nome: %s | Nascimento: %s%n",
                funcionario.getNome(),
                funcionario.getDataNascimento().format(FORMATO_DATA));
    }

    private static String formatarMoeda(BigDecimal valor) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(LOCALE_BR);
        return formato.format(valor);
    }

    private static String formatarNumero(BigDecimal valor) {
        NumberFormat formato = NumberFormat.getNumberInstance(LOCALE_BR);
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        return formato.format(valor);
    }

    private static void imprimirTitulo(String titulo) {
        System.out.printf("%n%s%n%s%n", titulo, "=".repeat(titulo.length()));
    }
}
