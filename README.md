README — ABCGJL SmartCuisine Backend
📌 Sobre o projeto

API backend do sistema SmartCuisine, desenvolvida em Java 21 + Spring Boot 3.5.6, com foco em gestão de cozinha, insumos, fichas técnicas e autenticação segura.

🚀 Tecnologias utilizadas
Java 21
Spring Boot
Spring Web
Spring Data JPA
Spring Validation
Spring Security
PostgreSQL (produção)
H2 Database (testes/dev)
Flyway (migrations)
JWT (autenticação)
Lombok
Swagger/OpenAPI
JUnit (testes)
Maven

▶️ Como executar o projeto
1. Pré-requisitos

Antes de começar, você precisa ter instalado:

Java 21
Maven 3+
PostgreSQL (caso rode em produção)
IDE (IntelliJ / VS Code / Eclipse)
2. Clonar o projeto
   git clone https://github.com/SENAI-Anchieta-DEV/abcgjl-smartcusine-backend.git
   cd abcgjl-smartcusine-backend
3. Configurar variáveis de ambiente

Crie um arquivo application.properties ou configure variáveis:

Exemplo (PostgreSQL):
spring.datasource.url=jdbc:postgresql://localhost:5432/smartcuisine
spring.datasource.username=postgres
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# JWT
jwt.secret=sua-chave-secreta
jwt.expiration=86400000
4. Rodar o projeto
   mvn spring-boot:run
   🧪 Banco de dados

O projeto usa:

PostgreSQL em ambiente real
H2 Database para testes locais
🧱 Migrations (Flyway)

As migrations são executadas automaticamente ao iniciar a aplicação.

Localização padrão:

src/main/resources/db/migration
🔐 Autenticação (JWT)

A API utiliza autenticação via JSON Web Token (JWT).

Fluxo:

Usuário faz login
API retorna token JWT
Token deve ser enviado no header:
Authorization: Bearer <token>
📖 Swagger (Documentação da API)

Após subir o projeto, acesse:

http://localhost:8080/swagger-ui/index.html
🧪 Rodar testes
mvn test

📂 Estrutura do projeto
src/
├── main/
│   ├── java/com/senai/abcgjl_smartcusine_backend
│   │   ├── application/service
│   │   ├── domain
│   │   ├── infrastructure
│   │   └── api/controller
│   └── resources
│       ├── db/migration
│       └── application.properties
└── test/

⚠️ Problemas comuns
Erro de banco

Verifique:

PostgreSQL rodando
usuário/senha corretos
database existe
Porta ocupada
server.port=8081

👨‍💻 Autor

Amanda Marques Cardozo Rosa
Bianca Neves Torres
Catarina Macedo Lopes
Gabrieli da Silva Marcelino
Jamily Vitoria Alecrim 
Laura Basilio MitterBach