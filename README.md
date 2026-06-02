# Simulador de Financiamentos API

API REST para simulação de financiamentos com cálculo de juros compostos mês a mês.

---

## Tecnologias

- Java 25
- Quarkus 3.35.4
- Hibernate ORM + H2 Database
- Maven
- JUnit 5 + REST Assured
- JaCoCo

---

## Como rodar a aplicação

### Pré-requisitos

- Java 25+
- Maven 3.9+

### Iniciando

```bash
cd simulador-financiamentos
mvn quarkus:dev
```

A aplicação sobe em `http://localhost:8080`

Swagger UI: `http://localhost:8080/q/swagger-ui`

---

## Endpoints

### Criar simulação
```
POST /simulacoes
```
```json
{
  "valorInicial": 1000.00,
  "taxaJurosMensal": 1.5,
  "prazoMeses": 12
}
```
Retorna HTTP 201 com a simulação completa e memória de cálculo mês a mês.

### Buscar simulação
GET /simulacoes/{id}
Retorna HTTP 200 com os dados da simulação ou 404 se não encontrada.

---

## Como rodar os testes

```bash
mvn test
```

---

## Como validar a cobertura

```bash
mvn clean verify
```

Para visualizar o relatório completo:

```bash
mvn jacoco:report -Djacoco.dataFile=target/jacoco-merged.exec
```
Relatório gerado em:
target/site/jacoco/index.html

Cobertura atual: **93%** (mínimo exigido: 80%)

---

## Estrutura do projeto
src/main/java/com/financiamento
├── resource      → endpoints HTTP
├── service       → regras de negócio
├── repository    → acesso ao banco
├── entity        → modelos do banco
└── dto           → objetos de entrada e saída