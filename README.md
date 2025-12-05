# API Estoque - Controle de Estoque

API REST em Spring Boot 2.7.18 para gerenciar estoque, produtos, clientes, fornecedores, categorias e vendas.

## Tecnologias

- **Java 11**
- **Spring Boot 2.7.18**
- **Spring Data JPA / Hibernate**
- **MySQL 8.0**
- **Maven**
- **Jackson (JSON serialization)**

## Configuração e Execução

### Pré-requisitos

- JDK 11+ instalado
- MySQL 8.0 rodando em `localhost:3306`
- Credenciais MySQL:
  - Usuário: `root`
  - Senha: (vazia)

### Variáveis de Ambiente (application.properties)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estoque_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Build e Execução

```bash
# Build do projeto
.\mvnw clean package -DskipTests

# Executar a aplicação (modo desenvolvimento com logs no console)
.\mvnw spring-boot:run

# Executar o jar compilado
java -jar target\api-estoque-0.0.1-SNAPSHOT.jar
```

A aplicação iniciará na porta **8080** → `http://localhost:8080`

---

## Exemplos de Requisições

A API aceita e retorna JSON. Todos os exemplos usam `Content-Type: application/json`.

### 1. CATEGORIAS

#### 1.1 Criar Categoria
```bash
POST /api/categorias
Content-Type: application/json

{
  "nome": "Eletrônicos"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Eletrônicos",
  "produtos": []
}
```

#### 1.2 Listar Categorias
```bash
GET /api/categorias
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "Eletrônicos",
    "produtos": []
  }
]
```

#### 1.3 Buscar Categoria por ID
```bash
GET /api/categorias/1
```

#### 1.4 Atualizar Categoria
```bash
PUT /api/categorias/1
Content-Type: application/json

{
  "nome": "Eletrônicos e Informática"
}
```

#### 1.5 Deletar Categoria
```bash
DELETE /api/categorias/1
```

---

### 2. FORNECEDORES

#### 2.1 Criar Fornecedor
```bash
POST /api/fornecedores
Content-Type: application/json

{
  "nome": "Fornecedor XYZ"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Fornecedor XYZ",
  "produtos": []
}
```

#### 2.2 Listar Fornecedores
```bash
GET /api/fornecedores
```

#### 2.3 Buscar Fornecedor por ID
```bash
GET /api/fornecedores/1
```

#### 2.4 Atualizar Fornecedor
```bash
PUT /api/fornecedores/1
Content-Type: application/json

{
  "nome": "Novo Nome Fornecedor"
}
```

#### 2.5 Deletar Fornecedor
```bash
DELETE /api/fornecedores/1
```

---

### 3. PRODUTOS

#### 3.1 Criar Produto com DTO
```bash
POST /api/produtos
Content-Type: application/json

{
  "nome": "Notebook Dell XPS 13",
  "preco": 4500.00,
  "categoriaId": 1,
  "fornecedorIds": [1],
  "estoqueQuantidade": 10
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "Notebook Dell XPS 13",
  "preco": 4500.00,
  "categoria": {
    "id": 1,
    "nome": "Eletrônicos"
  },
  "estoque": {
    "id": 1,
    "quantidade": 10
  },
  "fornecedores": [
    {
      "id": 1,
      "nome": "Fornecedor XYZ"
    }
  ]
}
```

#### 3.2 Listar Produtos
```bash
GET /api/produtos
```

#### 3.3 Buscar Produto por ID
```bash
GET /api/produtos/1
```

#### 3.4 Atualizar Produto
```bash
PUT /api/produtos/1
Content-Type: application/json

{
  "nome": "Notebook Dell XPS 15",
  "preco": 5500.00,
  "categoriaId": 1,
  "fornecedorIds": [1],
  "estoqueQuantidade": 8
}
```

#### 3.5 Deletar Produto
```bash
DELETE /api/produtos/1
```

---

### 4. ESTOQUE

#### 4.1 Listar Estoques
```bash
GET /api/estoques
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "quantidade": 10,
    "produto": {
      "id": 1,
      "nome": "Notebook Dell XPS 13",
      "preco": 4500.00
    }
  }
]
```

#### 4.2 Buscar Estoque por ID
```bash
GET /api/estoques/1
```

#### 4.3 Atualizar Estoque
```bash
PUT /api/estoques/1
Content-Type: application/json

{
  "quantidade": 15
}
```

#### 4.4 Deletar Estoque
```bash
DELETE /api/estoques/1
```

---

### 5. CLIENTES

#### 5.1 Criar Cliente (com DTO e Validação)
```bash
POST /api/clientes
Content-Type: application/json

{
  "nome": "João da Silva",
  "email": "joao.silva@email.com"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@email.com",
  "vendas": []
}
```

#### 5.2 Listar Clientes
```bash
GET /api/clientes
```

#### 5.3 Buscar Cliente por ID
```bash
GET /api/clientes/1
```

#### 5.4 Atualizar Cliente
```bash
PUT /api/clientes/1
Content-Type: application/json

{
  "nome": "João da Silva Santos",
  "email": "joao.santos@email.com"
}
```

#### 5.5 Deletar Cliente
```bash
DELETE /api/clientes/1
```

---

### 6. VENDAS

#### 6.1 Criar Venda - Formato Aninhado (Nested Objects)
```bash
POST /api/vendas
Content-Type: application/json

{
  "cliente": {
    "id": 1
  },
  "itemVenda": [
    {
      "produto": {
        "id": 1
      },
      "quantidade": 2
    },
    {
      "produto": {
        "id": 2
      },
      "quantidade": 1
    }
  ]
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "cliente": {
    "id": 1,
    "nome": "João da Silva",
    "email": "joao.silva@email.com"
  },
  "data": "2025-12-05T19:14:19.777495",
  "total": 13500.00,
  "itens": [
    {
      "id": 1,
      "produto": {
        "id": 1,
        "nome": "Notebook Dell XPS 13",
        "preco": 4500.00
      },
      "quantidade": 2,
      "precoUnitario": 4500.00
    },
    {
      "id": 2,
      "produto": {
        "id": 2,
        "nome": "Mouse Wireless",
        "preco": 50.00
      },
      "quantidade": 1,
      "precoUnitario": 50.00
    }
  ]
}
```

#### 6.2 Criar Venda - Formato ID-Based (IDs Simples)
```bash
POST /api/vendas
Content-Type: application/json

{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 2,
      "quantidade": 1
    }
  ]
}
```

**Resposta (201 Created):** *(Igual ao formato anterior)*

#### 6.3 Listar Vendas
```bash
GET /api/vendas
```

#### 6.4 Buscar Venda por ID
```bash
GET /api/vendas/1
```

#### 6.5 Atualizar Venda
```bash
PUT /api/vendas/1
Content-Type: application/json

{
  "total": 14000.00,
  "data": "2025-12-05T19:14:19.777495"
}
```

#### 6.6 Deletar Venda
```bash
DELETE /api/vendas/1
```

---

## Exemplos com PowerShell (Invoke-RestMethod)

### Criar Cliente
```powershell
$body = '{"nome":"João da Silva","email":"joao.silva@email.com"}'
Invoke-RestMethod -Uri "http://localhost:8080/api/clientes" -Method Post -ContentType "application/json" -Body $body -Verbose
```

### Criar Produto
```powershell
$body = '{"nome":"Notebook Dell XPS 13","preco":4500.00,"categoriaId":1,"fornecedorIds":[1],"estoqueQuantidade":10}'
Invoke-RestMethod -Uri "http://localhost:8080/api/produtos" -Method Post -ContentType "application/json" -Body $body -Verbose
```

### Criar Venda (Formato Aninhado)
```powershell
$body = '{"cliente":{"id":1},"itemVenda":[{"produto":{"id":1},"quantidade":2}]}'
Invoke-RestMethod -Uri "http://localhost:8080/api/vendas" -Method Post -ContentType "application/json" -Body $body -Verbose
```

### Criar Venda (Formato ID-Based)
```powershell
$body = '{"clienteId":1,"itens":[{"produtoId":1,"quantidade":2}]}'
Invoke-RestMethod -Uri "http://localhost:8080/api/vendas" -Method Post -ContentType "application/json" -Body $body -Verbose
```

### Listar Todos os Clientes
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/clientes" -Method Get -ContentType "application/json" -Verbose
```

---

## Códigos de Status HTTP

| Status | Significado |
|--------|-------------|
| 200 OK | Requisição bem-sucedida (GET, PUT) |
| 201 Created | Recurso criado com sucesso (POST) |
| 204 No Content | Recurso deletado (DELETE) |
| 400 Bad Request | Erro de validação ou dados inválidos |
| 404 Not Found | Recurso não encontrado |
| 409 Conflict | Conflito (ex: estoque insuficiente) |
| 415 Unsupported Media Type | Content-Type inválido |
| 500 Internal Server Error | Erro no servidor |

---

## Tratamento de Erros

A API retorna erros em formato JSON com detalhes:

### Exemplo - Validação Falha (400 Bad Request)
```json
{
  "timestamp": "2025-12-05T21:35:43.516+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "nome is required"
}
```

### Exemplo - Estoque Insuficiente (409 Conflict)
```json
{
  "timestamp": "2025-12-05T21:35:43.516+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Insufficient stock"
}
```

### Exemplo - Content-Type Inválido (415 Unsupported Media Type)
```json
{
  "timestamp": "2025-12-05T21:35:43.516+00:00",
  "status": 415,
  "error": "Unsupported Media Type",
  "message": "Content type 'text/plain' not supported"
}
```

---

## Fluxo Completo de Uso

1. **Criar Categoria** → GET `/api/categorias/1`
2. **Criar Fornecedor** → GET `/api/fornecedores/1`
3. **Criar Produto** (referenciando categoria e fornecedor)
4. **Verificar Estoque** → GET `/api/estoques`
5. **Criar Cliente**
6. **Criar Venda** (validação automática de cliente e estoque)
7. **Consultar Venda** → GET `/api/vendas/1`

---

## Validações Implementadas

### ClienteCreateDto
- `nome`: obrigatório (`@NotBlank`)
- `email`: obrigatório e válido (`@NotBlank @Email`)

### FornecedorCreateDto
- `nome`: obrigatório (`@NotBlank`)

### ProdutoCreateDto
- `nome`: obrigatório
- `preco`: obrigatório (BigDecimal)
- `categoriaId`: obrigatório
- `fornecedorIds`: lista de IDs (obrigatório)
- `estoqueQuantidade`: quantidade inicial de estoque

### VendaCreateDto
- `clienteId`: obrigatório (cliente deve existir)
- `itens`: lista obrigatória com pelo menos 1 item
  - cada item requer `produtoId` e `quantidade` válidos
  - valida existência do produto
  - valida quantidade disponível em estoque
  - deduz automaticamente do estoque ao criar

---

## DTOs (Data Transfer Objects)

### ClienteCreateDto
```java
public class ClienteCreateDto {
    @NotBlank
    private String nome;
    
    @NotBlank
    @Email
    private String email;
}
```

### FornecedorCreateDto
```java
public class FornecedorCreateDto {
    @NotBlank
    private String nome;
}
```

### ProdutoCreateDto
```java
public class ProdutoCreateDto {
    private String nome;
    private BigDecimal preco;
    private Long categoriaId;
    private Set<Long> fornecedorIds;
    private Integer estoqueQuantidade;
}
```

### ItemVendaDto
```java
public class ItemVendaDto {
    private Long produtoId;
    private Integer quantidade;
}
```

### VendaCreateDto
```java
public class VendaCreateDto {
    private Long clienteId;
    private List<ItemVendaDto> itens;
}
```

---

## Estrutura do Projeto

```
src/main/java/com/controleestoque/api_estoque/
├── controller/
│   ├── CategoriaController.java
│   ├── ClienteController.java
│   ├── EstoqueController.java
│   ├── FornecedorController.java
│   ├── ProdutoController.java
│   └── VendaController.java
├── dto/
│   ├── ClienteCreateDto.java
│   ├── FornecedorCreateDto.java
│   ├── ItemVendaDto.java
│   ├── ProdutoCreateDto.java
│   └── VendaCreateDto.java
├── exception/
│   └── ApiExceptionHandler.java
├── model/
│   ├── Categoria.java
│   ├── Cliente.java
│   ├── Estoque.java
│   ├── Fornecedor.java
│   ├── ItemVenda.java
│   ├── Produto.java
│   └── Venda.java
├── repository/
│   ├── CategoriaRepository.java
│   ├── ClienteRepository.java
│   ├── EstoqueRepository.java
│   ├── FornecedorRepository.java
│   ├── ItemVendaRepository.java
│   ├── ProdutoRepository.java
│   └── VendaRepository.java
└── ApiEstoqueApplication.java

src/main/resources/
└── application.properties
```

---

## Notas Importantes

- **Relacionamentos Bidirecionais**: Usado `@JsonManagedReference` e `@JsonBackReference` para evitar serialização infinita de objetos relacionados.
- **Lazy Loading**: Estoque usa `FetchType.LAZY` por padrão para otimizar queries. ItemVenda usa `FetchType.EAGER` para carregar produto na venda.
- **Deduções de Estoque**: Ao criar uma venda, o estoque é automaticamente deduzido. Se o estoque for insuficiente, a venda falha com erro 409.
- **Validação em Cascata**: DTOs com `@Valid` garantem validação dos dados de entrada antes de processar.

---

## Melhorias Futuras Recomendadas

1. Adicionar **otimistic locking** (campo `@Version`) em `Estoque` para evitar race conditions
2. Implementar **testes unitários e de integração** com MockMvc
3. Adicionar **autenticação e autorização** (JWT/OAuth)
4. Implementar **paginação** em endpoints GET (findAll)
5. Adicionar **logging estruturado** com SLF4J
6. Criar **endpoints de relatório** (vendas por período, estoque baixo, etc.)
7. Implementar **soft delete** para dados sensíveis
8. Adicionar **cache** com Spring Cache para consultas frequentes

---

## Suporte

Para dúvidas ou problemas, verifique:
- Logs da aplicação (porta 8080)
- Status de conexão com MySQL
- Validação dos dados enviados (emails válidos, nomes não vazios)
- Headers das requisições (`Content-Type: application/json`)
