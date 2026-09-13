# Teste Prático Prothera — Desenvolvedor Full Stack Jr.

Aplicação Java de console desenvolvida para o teste técnico da Prothera. O projeto realiza operações de cadastro em memória, remoção, formatação, reajuste salarial, agrupamento, filtragem, ordenação e cálculos sobre uma lista de funcionários.

## Tecnologias e decisões

- Java 17, versão LTS amplamente utilizada;
- Maven para compilação, testes e empacotamento;
- JUnit 5 para os testes automatizados;
- `LocalDate` para representação das datas;
- `BigDecimal` para cálculos monetários com precisão;
- Streams, `Comparator`, `List` e `Map` para manipulação das coleções;
- `Locale` brasileiro para exibição de datas e valores no formato solicitado;
- encapsulamento, herança e validações básicas de domínio.

O projeto foi desenvolvido como aplicação Java de console, conforme o escopo definido no enunciado.

## Pré-requisitos

- [JDK 17 ou superior](https://adoptium.net/);
- [Apache Maven 3.6 ou superior](https://maven.apache.org/).

Confirme as instalações:

```bash
java -version
mvn -version
```

## Como executar

Clone o repositório e acesse a pasta:

```bash
git clone https://github.com/WalderlyJR/teste-pratico-prothera.git
cd teste-pratico-prothera
```

Compile o projeto, execute os testes, gere o arquivo JAR e inicie a aplicação:

```bash
mvn clean package && java -jar target/teste-pratico-prothera-1.0.0.jar
```

Não é necessário cadastrar ou editar informações durante a execução. Os funcionários da tabela são carregados automaticamente pela classe `Principal`.

### Execução pelo VS Code

1. Abra a pasta do projeto no VS Code;
2. Instale o **Extension Pack for Java** sugerido pelo editor;
3. Abra `src/main/java/br/com/prothera/Principal.java`;
4. Clique em **Run** acima do método `main` ou pressione `F5`;
5. Selecione **Executar teste prático**.

## Como executar somente os testes

```bash
mvn test
```

Os testes verificam a criação dos funcionários, a remoção de João, o reajuste salarial, o agrupamento por função, os aniversariantes, o funcionário mais velho, a ordenação alfabética, a soma dos salários e o cálculo da quantidade de salários mínimos.

## Requisitos atendidos

| Item | Implementação |
|---|---|
| 1 | Classe `Pessoa` com nome e data de nascimento |
| 2 | Classe `Funcionario` herdando de `Pessoa`, com salário e função |
| 3.1 | Inclusão dos dez funcionários na ordem original |
| 3.2 | Remoção do funcionário João |
| 3.3 | Impressão das informações com formatação brasileira |
| 3.4 | Reajuste salarial de 10% |
| 3.5 | Agrupamento por função em um `Map` |
| 3.6 | Impressão dos funcionários agrupados por função |
| 3.8 | Filtro dos aniversariantes de outubro e dezembro |
| 3.9 | Identificação do funcionário com maior idade |
| 3.10 | Ordenação alfabética dos funcionários |
| 3.11 | Soma dos salários reajustados |
| 3.12 | Cálculo da quantidade de salários mínimos por funcionário |

> A numeração 3.7 não consta no enunciado original.

## Estrutura do projeto

```text
teste-pratico-prothera/
├── .vscode/
│   ├── extensions.json
│   └── launch.json
├── src/
│   ├── main/java/br/com/prothera/
│   │   ├── Pessoa.java
│   │   ├── Funcionario.java
│   │   └── Principal.java
│   └── test/java/br/com/prothera/
│       ├── FuncionarioTest.java
│       └── PrincipalTest.java
├── .gitignore
├── pom.xml
└── README.md
```

## Observações técnicas

- Os salários são criados a partir de `String`, evitando perda de precisão;
- O reajuste é aplicado antes dos agrupamentos e cálculos posteriores;
- Os salários reajustados são arredondados para duas casas decimais com `HALF_UP`;
- O `LinkedHashMap` mantém as funções na ordem em que aparecem na lista;
- A ordenação alfabética gera uma nova lista e preserva a lista principal;
- A idade é calculada em tempo de execução com base na data atual.

## Autor

**Walderly Junior**

[LinkedIn](https://www.linkedin.com/in/walderly-junior) · [GitHub](https://github.com/WalderlyJR)# teste-pratico-prothera
