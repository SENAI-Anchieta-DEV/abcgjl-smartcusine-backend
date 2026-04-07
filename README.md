# Carteirinha Digital Backend

Backend da aplicação **Carteirinha Digital**, desenvolvido em **Java + Spring Boot**, com foco em autenticação e autorização via JWT, gestão acadêmica, upload de fotos e anexos, versionamento de banco com Flyway e preparação para execução local e implantação em produção.

## Visão geral

Este projeto centraliza as regras de negócio do sistema de carteirinha digital acadêmica. A API permite:

- cadastro de usuários com perfis acadêmicos;
- autenticação com JWT;
- consulta do perfil autenticado;
- gerenciamento de cursos, turmas, unidades curriculares e vínculos acadêmicos;
- aprovação e rejeição de cadastros;
- upload de foto de perfil e anexos;
- armazenamento local em desenvolvimento e storage externo em produção.

## Stack tecnológica

- Java 17+
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- Flyway
- H2 para desenvolvimento
- PostgreSQL para produção
- Firebase Storage opcional para produção
- Maven
- Docker
- Render Blueprint

## Arquitetura

O projeto segue uma organização em camadas, inspirada em DDD e Clean Architecture pragmática.

### Camadas

#### `application`
Contém DTOs e serviços de aplicação. É onde ficam os fluxos de orquestração dos casos de uso.

#### `domain`
Contém o núcleo do sistema: entidades, enums, exceções de negócio e repositórios.

#### `infrastructure`
Contém a implementação técnica: configuração do Spring, segurança JWT, bootstrap do primeiro administrador, integração com storage local ou Firebase e exposição de arquivos estáticos locais.

#### `interface_ui`
Contém os controllers REST e o tratamento global de exceções.

## Estrutura de pacotes

```text
com.senai.carteirinhadigitalbackend
├── application
│   ├── dto
│   └── service
├── domain
│   ├── entity
│   ├── enums
│   ├── exception
│   └── repository
├── infrastructure
│   ├── config
│   ├── security
│   └── storage
└── interface_ui
    ├── controller
    └── exception
```

## Fluxo de autenticação e autorização

### Login

1. O cliente envia credenciais para `POST /auth/login`.
2. A API valida e busca o usuário.
3. O sistema verifica existência, status do cadastro e se o usuário está ativo.
4. Em caso de sucesso, a API devolve access token, refresh token e dados básicos do usuário.

### Refresh token

1. O cliente envia o refresh token para `POST /auth/refresh`.
2. A API valida o token.
3. Um novo access token é emitido.

### Autorização

A autorização é baseada em filtro JWT, `SecurityConfig` e regras por perfil e endpoint.

## Fluxo funcional do domínio

### Cadastro de usuário

A API possui fluxo de cadastro multipart para permitir dados textuais, foto de perfil e anexos.

Principais operações:
- `POST /usuarios/cadastro`
- `POST /usuarios`
- `PUT /usuarios/{id}`

### Aprovação e rejeição

Usuários podem ser aprovados ou rejeitados por endpoints administrativos:
- `PUT /usuarios/{id}/aprovar`
- `PUT /usuarios/{id}/rejeitar`

### Perfil autenticado

- `GET /auth/me`
- `PUT /perfil/me`

### Gestão acadêmica

A API também expõe recursos para cursos, turmas, unidades curriculares, vínculos acadêmicos, visão do aluno autenticado e visão do professor autenticado.

## Storage de fotos e anexos

A camada de storage foi preparada para permitir troca de provider sem alterar a regra de negócio principal.

### Providers suportados atualmente

- `local`
- `firebase`

### Estratégia adotada

#### Desenvolvimento
Por padrão, o ambiente de desenvolvimento usa **storage local**.

#### Produção
O ambiente de produção pode usar **Firebase Storage** por configuração, mantendo a mesma API.

### Metadados persistidos

A entidade `Anexo` armazena metadados como nome original, nome armazenado, provider, chave do objeto, tipo de conteúdo, tamanho em bytes e URL.

## Banco de dados e Flyway

O projeto utiliza **Flyway** para versionar a estrutura do banco.

### Migrations
As migrations ficam em:

```text
src/main/resources/db/migration
```

### Estratégia adotada

- estrutura do banco controlada por migrations;
- `ddl-auto=validate` para evitar alterações automáticas fora do versionamento;
- seeds SQL separadas para desenvolvimento.

## Seeds de desenvolvimento

Os dados de exemplo do ambiente de desenvolvimento ficam em:

```text
src/main/resources/db/dev-seed
```

Essa decisão foi adotada para reforçar o uso de SQL, manter produção limpa e separar dados estruturais de dados de exemplo.

## Bootstrap do primeiro administrador

O `AdminBootstrap` foi mantido apenas para a criação do **primeiro administrador**.

### Regras

- controlado por variável de ambiente;
- idempotente;
- não cria dados de exemplo;
- não mistura inicialização de domínio com seeds de desenvolvimento.

Principais propriedades:

```properties
app.bootstrap.admin.enabled
app.bootstrap.admin.email
app.bootstrap.admin.password
```

## Profiles da aplicação

### `default`
Configurações comuns da aplicação.

### `dev`
Usa H2 em memória, seed SQL de desenvolvimento e storage local por padrão.

### `prod`
Usa PostgreSQL, configuração por variáveis de ambiente e storage configurável, com foco em Firebase.

## Variáveis de ambiente relevantes

### Aplicação
- `PORT`
- `APP_PUBLIC_BASE_URL`
- `APP_CORS_ALLOWED_ORIGINS`

### JWT
- `SECURITY_JWT_SECRET`
- `SECURITY_JWT_ACCESS_EXPIRATION`
- `SECURITY_JWT_REFRESH_EXPIRATION`

### Bootstrap admin
- `APP_BOOTSTRAP_ADMIN_ENABLED`
- `APP_BOOTSTRAP_ADMIN_EMAIL`
- `APP_BOOTSTRAP_ADMIN_PASSWORD`

### Banco
- `DATABASE_HOST`
- `DATABASE_PORT`
- `DATABASE_NAME`
- `DATABASE_USER`
- `DATABASE_PASSWORD`

### Storage
- `APP_STORAGE_PROVIDER`
- `APP_STORAGE_LOCAL_BASE_PATH`
- `APP_STORAGE_LOCAL_PUBLIC_PATH`
- `APP_STORAGE_FIREBASE_BUCKET`
- `APP_STORAGE_FIREBASE_PROJECT_ID`
- `APP_STORAGE_FIREBASE_CREDENTIALS_FILE`
- `APP_STORAGE_FIREBASE_CREDENTIALS_BASE64`
- `APP_STORAGE_FIREBASE_CREDENTIALS_JSON`
- `APP_STORAGE_FIREBASE_FOLDER`

## Como executar localmente

### Pré-requisitos
- JDK 17 ou superior
- Maven ou Maven Wrapper

### Execução
```bash
./mvnw spring-boot:run
```

No Windows:
```bash
mvnw.cmd spring-boot:run
```

A aplicação sobe por padrão em:
```text
http://localhost:8080
```

### H2 Console
No profile de desenvolvimento, o console H2 fica disponível em:
```text
http://localhost:8080/h2-console
```

## Como executar os testes

```bash
./mvnw test
```

## Testes existentes no projeto

Atualmente o projeto possui testes unitários e de integração para fluxos centrais, incluindo autenticação, serviço de usuário, vínculo acadêmico, bootstrap do primeiro admin, storage local, tratamento global de exceções e alguns fluxos REST principais.

## Docker

O projeto inclui `Dockerfile` para empacotamento da aplicação.

Exemplo de build:
```bash
docker build -t carteirinha-digital-backend .
```

Exemplo de execução:
```bash
docker run -p 8080:8080 carteirinha-digital-backend
```

## Implantação no Render

O projeto já possui `render.yaml` para deploy via Blueprint.

### Arquivos relevantes
- `render.yaml`
- `.env.render.example`
- `Dockerfile`

### Estratégia
- aplicação como Web Service;
- banco PostgreSQL provisionado no Render;
- healthcheck via Actuator.

## Endpoints principais

### Autenticação
- `POST /auth/login`
- `POST /auth/refresh`
- `GET /auth/me`

### Usuários
- `POST /usuarios/cadastro`
- `POST /usuarios`
- `GET /usuarios`
- `GET /usuarios/{id}`
- `PUT /usuarios/{id}`
- `PUT /usuarios/{id}/aprovar`
- `PUT /usuarios/{id}/rejeitar`
- `DELETE /usuarios/{id}`

### Perfil
- `PUT /perfil/me`

### Cursos
- `GET /cursos`
- `POST /cursos`
- `PUT /cursos/{id}`
- `DELETE /cursos/{id}`

### Turmas
- `GET /turmas`
- `POST /turmas`
- `PUT /turmas/{id}`
- `DELETE /turmas/{id}`

### Unidades curriculares
- `GET /ucs`
- `POST /ucs`
- `PUT /ucs/{id}`
- `DELETE /ucs/{id}`

### Aluno autenticado
- `GET /alunos/me`
- `GET /alunos/me/ucs`
- `GET /alunos/me/ucs/{id}`

### Professor autenticado
- `GET /professores/me/turmas`
- `GET /professores/me/turmas/{turmaId}/alunos`
- `GET /professores/me/alunos/{alunoId}/ucs`
- `PUT /professores/me/alunos/{alunoId}/ucs/{ucId}/avaliacao`

### Vínculos acadêmicos
- `GET /vinculos-academicos/professores`
- `POST /vinculos-academicos/professores`
- `DELETE /vinculos-academicos/professores/{id}`
- `GET /vinculos-academicos/alunos`
- `POST /vinculos-academicos/alunos`
- `DELETE /vinculos-academicos/alunos/{id}`

### Públicos
- `GET /public/cursos`

## Swagger / OpenAPI

A API agora expõe documentação interativa via Springdoc OpenAPI.

### Dependência utilizada
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.16`

### Endereços
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`
- `http://localhost:8080/v3/api-docs.yaml`

### O que foi documentado
- todos os controllers REST do projeto;
- exemplos de sucesso e de erro para os endpoints principais;
- esquema de segurança Bearer JWT;
- descrição funcional por agrupamento de endpoint.

## Coleção Postman

A pasta `postman/` foi adicionada ao repositório com os arquivos:

- `Carteirinha-Digital-Backend.postman_collection.json`
- `Carteirinha-Digital-Backend.postman_environment.json`

A coleção inclui:
- autenticação e renovação de token;
- casos de sucesso e falha dos fluxos principais;
- validação automática de status code;
- captura automática de `accessToken`, `refreshToken` e ids gerados.


## Fluxo recomendado para publicação no GitHub

Para publicar o projeto limpo, a recomendação é:

1. renomear o repositório para `carteirinha-digital-backend`;
2. limpar o histórico anterior;
3. criar um commit inicial único com a versão final;
4. publicar a coleção Postman e a documentação Swagger na sequência.

## Próximas evoluções planejadas

- ampliação da cobertura de integração;
- documentação complementar em pasta `docs/`;
- refinamento de observabilidade e hardening de produção.

## Licença e uso didático

Este projeto foi estruturado para servir como base de estudo, demonstração e evolução em ambiente educacional, sem abrir mão de práticas de mercado como versionamento de schema, separação de ambientes, autenticação JWT, abstração de storage e preparo para deploy real.
