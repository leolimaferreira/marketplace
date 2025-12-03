# 🛒 Marketplace API

API RESTful para um sistema de marketplace desenvolvida com Spring Boot, implementando funcionalidades de e-commerce com gerenciamento de usuários, produtos, pedidos e pagamentos.

## 📋 Índice

- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Modelo de Dados](#-modelo-de-dados)
- [Configuração do Ambiente](#-configuração-do-ambiente)
- [Executando o Projeto](#-executando-o-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Segurança e Autenticação](#-segurança-e-autenticação)
- [Validações Customizadas](#-validações-customizadas)
- [Simulação de Pagamentos](#-simulação-de-pagamentos)
- [Estrutura do Projeto](#-estrutura-do-projeto)

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.5.8**
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - Spring Web
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Lombok**
- **Auth0 JWT** (4.4.0)
- **Maven**

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas com separação clara de responsabilidades:

```
├── Controller     → Camada de apresentação (REST API)
├── Service        → Regras de negócio
├── Repository     → Acesso a dados (JPA)
├── Mapper         → Conversão entre DTOs e Entidades
├── Security       → Autenticação e Autorização
├── Exception      → Tratamento de erros
├── Validator      → Validações customizadas
└── Model          → Entidades JPA
```

### Padrões Implementados

- **DTO Pattern**: Separação entre entidades de domínio e objetos de transferência
- **Repository Pattern**: Abstração da camada de persistência
- **Service Layer**: Encapsulamento da lógica de negócio
- **Custom Validators**: Validações de negócio reutilizáveis
- **JWT Authentication**: Autenticação stateless baseada em tokens
- **Role-Based Access Control (RBAC)**: Autorização baseada em cargos

## 📊 Modelo de Dados

### Entidades Principais

#### 👤 Usuários
Hierarquia de herança com estratégia `JOINED`:
- **Usuario** (classe base)
  - **Cliente**: Usuários compradores
  - **Dono**: Proprietários de lojas

**Cargos disponíveis:**
- `ADMIN`: Acesso total ao sistema
- `VENDEDOR`: Gerenciamento de lojas e produtos
- `CLIENTE`: Realização de pedidos e compras

#### 🏪 Lojas
- Vinculadas a um Dono
- Relacionamento Many-to-Many com Produtos
- Status de ativação

#### 📦 Produtos
- Controle de estoque
- Preços de compra e venda
- Validação de margem de lucro
- Relacionamento Many-to-Many com Lojas

#### 🛍️ Pedidos
Fluxo completo de pedidos com status:
- `PENDENTE` → `CONFIRMADO` → `EM_PREPARACAO` → `ENVIADO` → `ENTREGUE`
- Possibilidade de `CANCELADO` em qualquer etapa

#### 💳 Pagamentos
Formas de pagamento aceitas:
- `CARTAO_DEBITO`
- `CARTAO_CREDITO`
- `BOLETO` (10% desconto)
- `PIX` (10% desconto)

Status de pagamento:
- `PENDENTE`
- `CONCLUIDO`
- `CANCELADO`

### Diagrama de Relacionamentos

```
Usuario (JOINED)
  ├── Cliente
  │     ├── Enderecos (OneToMany)
  │     └── Pedidos (OneToMany)
  └── Dono
        └── Lojas (OneToMany)

Loja ←→ Produto (ManyToMany)

Pedido
  ├── Cliente (ManyToOne)
  ├── Loja (ManyToOne)
  ├── ItensPedido (OneToMany)
  └── Pagamento (OneToOne)

ItemPedido
  ├── Pedido (ManyToOne)
  └── Produto (ManyToOne)
```

## ⚙️ Configuração do Ambiente

### Pré-requisitos

- Java 17+
- Docker & Docker Compose
- Maven 3.6+

### Variáveis de Ambiente

Crie um arquivo `.env` na pasta `/env` com as seguintes variáveis:

```env
# Database
DATABASE_NAME=marketplace
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres
DATABASE_PORT=5432
DATABASE_URL=jdbc:postgresql://localhost:5432/marketplace

# Security
MARKETPLACE_API_SECRET=your-secret-key-here-minimum-32-characters

# Debug (opcional)
SHOW_SQL=false
```

### Estrutura de Arquivos

```
marketplace/
├── env/
│   └── .env                    # Variáveis de ambiente
├── schema/
│   └── schema.sql              # Schema do banco de dados
├── src/
│   └── main/
│       ├── java/
│       └── resources/
│           └── application.yaml
├── compose.yaml                # Docker Compose
└── pom.xml
```

## 🎯 Executando o Projeto

### 1. Clone o repositório

```bash
git clone <seu-repositorio>
cd marketplace
```

### 2. Configure as variáveis de ambiente

```bash
mkdir -p env
# Edite env/.env com suas configurações
```

### 3. Inicie o banco de dados

```bash
docker-compose up -d
```

O Docker irá:
- Criar o container PostgreSQL
- Executar o script `schema.sql` automaticamente
- Mapear a porta configurada em `DATABASE_PORT`

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### 🔐 Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/autorizacao/login` | Login de usuário | ❌ |
| POST | `/autorizacao/registrar` | Registro de novo cliente | ❌ |

**Exemplo de Login:**
```json
POST /autorizacao/login
{
  "email": "usuario@email.com",
  "senha": "SenhaForte123!"
}

Resposta:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 👥 Clientes

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/clientes` | Criar novo cliente | Público |
| GET | `/clientes` | Listar todos os clientes | ADMIN |
| GET | `/clientes/{id}` | Buscar cliente por ID | ADMIN, CLIENTE (próprio) |
| PUT | `/clientes/{id}` | Atualizar cliente | ADMIN, CLIENTE (próprio) |
| DELETE | `/clientes/{id}` | Desativar cliente | ADMIN |

**Exemplo de Criação:**
```json
POST /clientes
{
  "nome": "João Silva Santos",
  "email": "joao.silva@email.com",
  "senha": "SenhaForte123!",
  "cpf": "123.456.789-10",
  "rg": "12.345.678-9",
  "celular": "(11) 98765-4321",
  "dataNascimento": "1990-05-15",
  "imagem": "https://exemplo.com/foto.jpg",
  "endereco": {
    "logradouro": "Rua das Flores",
    "numero": "123",
    "complemento": "Apto 45",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01234-567",
    "principal": true
  }
}
```

### 📍 Endereços

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/enderecos/{clienteId}` | Adicionar endereços | ADMIN, CLIENTE (próprio) |
| GET | `/enderecos/{clienteId}` | Listar endereços do cliente | ADMIN, CLIENTE (próprio) |

**Exemplo:**
```json
POST /enderecos/{clienteId}
[
  {
    "logradouro": "Av. Paulista",
    "numero": "1000",
    "complemento": "Sala 10",
    "bairro": "Bela Vista",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01310-100",
    "principal": false
  }
]
```

### 🏪 Lojas

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/lojas/com-dono-novo` | Criar loja com novo dono | Público |
| POST | `/lojas/com-dono-existente` | Vincular loja a dono existente | ADMIN, VENDEDOR |
| GET | `/lojas` | Listar todas as lojas | Público |
| GET | `/lojas/ativas` | Listar lojas ativas | Público |
| PUT | `/lojas/{id}` | Atualizar loja | ADMIN, VENDEDOR |

**Exemplo com novo dono:**
```json
POST /lojas/com-dono-novo
{
  "nome": "Loja Tech",
  "descricao": "Produtos de tecnologia",
  "imagem": "https://exemplo.com/loja.jpg",
  "cnpj": "12.345.678/0001-90",
  "dono": {
    "nome": "Maria Silva",
    "email": "maria@email.com",
    "senha": "SenhaForte123!",
    "cpf": "987.654.321-00",
    "rg": "98.765.432-1",
    "celular": "(11) 91234-5678",
    "dataNascimento": "1985-10-20",
    "imagem": "https://exemplo.com/maria.jpg"
  }
}
```

### 📦 Produtos

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/produtos` | Criar produto | ADMIN, VENDEDOR |
| GET | `/produtos` | Listar produtos (com filtros) | Público |
| GET | `/produtos/{id}` | Buscar produto por ID | Público |
| PUT | `/produtos/{id}` | Atualizar produto | ADMIN, VENDEDOR |
| DELETE | `/produtos/{id}` | Desativar produto | ADMIN, VENDEDOR |

**Parâmetros de filtro (GET /produtos):**
- `nome`: Busca por nome (parcial)
- `precoMin`: Preço mínimo
- `precoMax`: Preço máximo
- `pagina`: Número da página (default: 0)
- `tamanho`: Itens por página (default: 10)

**Exemplo:**
```json
POST /produtos
{
  "nome": "Notebook Dell",
  "descricao": "Intel i7, 16GB RAM, SSD 512GB",
  "precoCompra": 3000.00,
  "precoVenda": 4500.00,
  "quantidade": 10,
  "imagem": "https://exemplo.com/notebook.jpg"
}
```

### 🛍️ Pedidos

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/pedidos` | Criar pedido | CLIENTE |
| GET | `/pedidos/cliente/{clienteId}` | Listar pedidos do cliente | ADMIN, VENDEDOR, CLIENTE |
| GET | `/pedidos/{id}` | Buscar pedido por ID | ADMIN, VENDEDOR, CLIENTE |
| PUT | `/pedidos/{id}` | Atualizar status do pedido | ADMIN, VENDEDOR |

**Exemplo:**
```json
POST /pedidos
{
  "clienteId": "uuid-do-cliente",
  "lojaId": "uuid-da-loja"
}
```

### 🛒 Itens de Pedido

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/itens-pedido` | Adicionar item ao pedido | CLIENTE |

**Exemplo:**
```json
POST /itens-pedido
{
  "pedidoId": "uuid-do-pedido",
  "produtoId": "uuid-do-produto",
  "quantidade": 2
}
```

**Lógica de Carrinho:**
- Se o produto já existe no pedido, a quantidade é somada
- O valor total do item é calculado automaticamente
- O valor total do pedido é atualizado

### 💳 Pagamentos

| Método | Endpoint | Descrição | Permissões |
|--------|----------|-----------|------------|
| POST | `/pagamentos` | Criar pagamento | CLIENTE |
| GET | `/pagamentos/cliente/{clienteId}` | Listar pagamentos do cliente | ADMIN, VENDEDOR, CLIENTE |
| GET | `/pagamentos/{id}` | Buscar pagamento por ID | ADMIN, VENDEDOR, CLIENTE |
| PUT | `/pagamentos/{id}` | Atualizar status | ADMIN, VENDEDOR |
| POST | `/pagamentos/{id}/simular-aprovacao` | Simular aprovação (teste) | ADMIN |
| POST | `/pagamentos/{id}/simular-rejeicao` | Simular rejeição (teste) | ADMIN |

**Exemplo:**
```json
POST /pagamentos
{
  "pedidoId": "uuid-do-pedido",
  "formaPagamento": "PIX",
  "valor": 100.00
}

Resposta (com desconto aplicado):
{
  "id": "uuid-do-pagamento",
  "formaPagamento": "PIX",
  "status": "PENDENTE",
  "valor": 90.00,
  "criadoEm": "2025-12-03T10:00:00",
  "atualizadoEm": "2025-12-03T10:00:00"
}
```

## 🔒 Segurança e Autenticação

### JWT (JSON Web Token)

A API utiliza JWT para autenticação stateless. Após o login, um token é retornado e deve ser enviado no header de todas as requisições protegidas:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Estrutura do Token

```json
{
  "sub": "uuid-do-usuario",
  "role": "CLIENTE",
  "iss": "marketplace-api",
  "exp": 1733241234
}
```

### Matriz de Permissões

| Recurso | GET (todos) | GET (ID) | POST | PUT | DELETE |
|---------|-------------|----------|------|-----|--------|
| Clientes | ADMIN | ADMIN, CLIENTE* | Público | ADMIN, CLIENTE* | ADMIN |
| Produtos | Público | Público | ADMIN, VENDEDOR | ADMIN, VENDEDOR | ADMIN, VENDEDOR |
| Lojas | Público | Público | ADMIN, VENDEDOR** | ADMIN, VENDEDOR | ADMIN |
| Pedidos | - | ADMIN, VENDEDOR, CLIENTE | CLIENTE | ADMIN, VENDEDOR | - |
| Pagamentos | - | ADMIN, VENDEDOR, CLIENTE | CLIENTE | ADMIN, VENDEDOR | - |
| Endereços | - | ADMIN, CLIENTE* | ADMIN, CLIENTE* | - | - |

\* *Apenas o próprio usuário ou ADMIN*  
\** *Público para criar loja com novo dono*

### SecurityFilter

O filtro de segurança intercepta todas as requisições para:
1. Extrair o token JWT do header `Authorization`
2. Validar o token
3. Carregar o usuário autenticado
4. Configurar o contexto de segurança do Spring

## ✅ Validações Customizadas

### @SenhaForte
Valida que a senha contém:
- Mínimo 8 caracteres
- Letras maiúsculas e minúsculas
- Números
- Caracteres especiais

```java
@SenhaForte
private String senha;
```

### @PrecoVendaValido
Garante que o preço de venda seja maior que o preço de compra:

```java
@PrecoVendaValido
public class ProdutoCriacaoDTO {
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
}
```

### @FormaPagamentoValida
Valida se a forma de pagamento é uma das aceitas pelo sistema.

### @PedidoStatusValido / @PagamentoStatusValido
Validam transições de status permitidas.

## 💰 Simulação de Pagamentos

Para fins de teste, a API oferece endpoints de simulação de pagamento:

### Simular Aprovação
```bash
POST /pagamentos/{id}/simular-aprovacao?segundos=3
Authorization: Bearer {token-admin}
```

**Parâmetros:**
- `segundos` (opcional): Tempo de delay para simular processamento (padrão: 3)

### Simular Rejeição
```bash
POST /pagamentos/{id}/simular-rejeicao
Authorization: Bearer {token-admin}
```

### Descontos Automáticos

Pagamentos via **PIX** ou **BOLETO** recebem **10% de desconto** automático:

```java
// Exemplo
Valor original: R$ 100,00
Forma: PIX
Valor final: R$ 90,00
```

## 📂 Estrutura do Projeto

```
src/main/java/com/marketplace/
│
├── annotation/                    # Anotações customizadas de validação
│   ├── FormaPagamentoValida.java
│   ├── PagamentoStatusValido.java
│   ├── PedidoStatusValido.java
│   ├── PrecoVendaValido.java
│   └── SenhaForte.java
│
├── config/                        # Configurações do Spring
│   ├── EncoderConfig.java        # BCrypt para senhas
│   └── SecurityConfig.java       # Spring Security + JWT
│
├── controller/                    # Camada de apresentação
│   ├── ControllerGenerico.java
│   └── impl/
│       ├── AutorizacaoController.java
│       ├── ClienteController.java
│       ├── EnderecoController.java
│       ├── ItemPedidoController.java
│       ├── LojaController.java
│       ├── PagamentoController.java
│       ├── PedidoController.java
│       └── ProdutoController.java
│
├── dto/                           # Data Transfer Objects
│   ├── endereco/
│   ├── error/
│   ├── itempedido/
│   ├── login/
│   ├── loja/
│   ├── pagamento/
│   ├── pedido/
│   ├── produto/
│   ├── recuperacao/
│   └── usuario/
│
├── exception/                     # Exceções customizadas
│   ├── AtualizacaoStatusInvalidaException.java
│   ├── CampoInvalidoException.java
│   ├── ConflitoException.java
│   ├── MesmaSenhaException.java
│   ├── NaoAutorizadoException.java
│   ├── NaoEncontradoException.java
│   ├── QuantidadeInsuficienteException.java
│   ├── TokenRecuperacaoExpiradoException.java
│   └── handler/
│       └── GlobalExceptionHandler.java
│
├── mapper/                        # Conversores DTO ↔ Entidade
│   ├── ClienteMapper.java
│   ├── DonoMapper.java
│   ├── EnderecoMapper.java
│   ├── ItemPedidoMapper.java
│   ├── LojaMapper.java
│   ├── PagamentoMapper.java
│   ├── PedidoMapper.java
│   ├── ProdutoMapper.java
│   ├── TokenRecuperacaoSenhaMapper.java
│   └── UsuarioMapper.java
│
├── model/                         # Entidades JPA
│   ├── Cliente.java
│   ├── Dono.java
│   ├── Endereco.java
│   ├── ItemPedido.java
│   ├── Loja.java
│   ├── Pagamento.java
│   ├── Pedido.java
│   ├── Produto.java
│   ├── TokenRecuperacaoSenha.java
│   ├── Usuario.java
│   └── enums/
│       ├── Cargo.java
│       ├── FormaPagamento.java
│       ├── PagamentoStatus.java
│       └── PedidoStatus.java
│
├── repository/                    # Camada de persistência
│   ├── ClienteRepository.java
│   ├── DonoRepository.java
│   ├── EnderecoRepository.java
│   ├── ItemPedidoRepository.java
│   ├── LojaRepository.java
│   ├── PagamentoRepository.java
│   ├── PedidoRepository.java
│   ├── ProdutoRepository.java
│   ├── TokenRecuperacaoSenhaRepository.java
│   ├── UsuarioRepository.java
│   └── specs/
│       └── ProdutoSpecification.java
│
├── security/                      # Autenticação e Autorização
│   ├── CustomUserDetailsService.java
│   ├── SecurityFilter.java
│   └── TokenService.java
│
├── service/                       # Lógica de negócio
│   ├── AutorizacaoService.java
│   ├── ClienteService.java
│   ├── DonoService.java
│   ├── EnderecoService.java
│   └── ...
│
├── utils/                         # Utilitários
│   └── Constantes.java
│
└── validator/                     # Implementações de validadores
    ├── FormaPagamentoValidator.java
    ├── PagamentoStatusValidator.java
    ├── PedidoStatusValidator.java
    ├── PrecoVendaValidator.java
    └── SenhaForteValidator.java
```

## 🔍 Features Implementadas

### ✨ Funcionalidades Principais

- ✅ Autenticação JWT com roles
- ✅ Registro e gerenciamento de clientes
- ✅ Sistema de lojas com donos
- ✅ Catálogo de produtos com busca e filtros
- ✅ Carrinho de compras inteligente (merge automático)
- ✅ Fluxo completo de pedidos
- ✅ Sistema de pagamentos com descontos
- ✅ Múltiplos endereços por cliente
- ✅ Soft delete (desativação lógica)
- ✅ Validações customizadas de negócio
- ✅ Tratamento global de exceções
- ✅ Paginação e ordenação
- ✅ Relacionamentos Many-to-Many (Loja-Produto)
- ✅ Herança de entidades (JOINED strategy)
- ✅ Timestamps automáticos (@PrePersist, @PreUpdate)

### 🔐 Recursos de Segurança

- ✅ Senha criptografada com BCrypt
- ✅ Validação de senha forte
- ✅ Token JWT com expiração
- ✅ Autorização baseada em roles
- ✅ Proteção contra acesso não autorizado
- ✅ CORS configurado
- ✅ Sessões stateless

## 🧪 Testando a API

### Exemplo de Fluxo Completo

```bash
# 1. Criar um cliente
POST /clientes
{...dados do cliente...}

# 2. Fazer login
POST /autorizacao/login
{
  "email": "cliente@email.com",
  "senha": "SenhaForte123!"
}
# Resposta: { "token": "..." }

# 3. Criar um pedido (usar token no header)
POST /pedidos
Authorization: Bearer {token}
{
  "clienteId": "uuid-do-cliente",
  "lojaId": "uuid-da-loja"
}

# 4. Adicionar itens ao pedido
POST /itens-pedido
Authorization: Bearer {token}
{
  "pedidoId": "uuid-do-pedido",
  "produtoId": "uuid-do-produto",
  "quantidade": 2
}

# 5. Criar pagamento
POST /pagamentos
Authorization: Bearer {token}
{
  "pedidoId": "uuid-do-pedido",
  "formaPagamento": "PIX",
  "valor": 100.00
}

# 6. Simular aprovação (apenas ADMIN)
POST /pagamentos/{id}/simular-aprovacao
Authorization: Bearer {token-admin}
```

## 📝 Observações Importantes

### Regras de Negócio

1. **Produtos**: O preço de venda deve ser maior que o preço de compra
2. **Endereços**: Apenas um endereço pode ser marcado como principal por cliente
3. **Pedidos**: Itens duplicados são automaticamente mesclados (quantidade somada)
4. **Pagamentos**: Descontos de 10% para PIX e BOLETO
5. **Usuários**: CPF e email devem ser únicos
6. **Senhas**: Devem atender aos critérios de senha forte

### Imagens

O sistema armazena imagens em **base64** (TEXT) no banco de dados. Para produção, considere usar um serviço de armazenamento de arquivos (S3, Cloudinary, etc.).

### Soft Delete

Entidades não são deletadas fisicamente, apenas marcadas como inativas:
- `Usuario.ativo = false`
- `Produto.ativo = false`
- `Loja.ativo = false`

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto é um projeto de demonstração educacional.

## 👨‍💻 Autor

Leonardo Lima

---

**Desenvolvido com ☕ e Spring Boot**

