# Vida QR — compartilhamento de informações clínicas

Aplicação full stack para o trabalhador manter informações clínicas atualizadas e disponibilizá-las, em uma emergência, através de um QR Code e uma senha pública separada.

## Tecnologias

- Backend: Java 21, Spring Boot, Spring Security, JWT, JPA/Hibernate, Flyway e ZXing
- Frontend: React 18, TypeScript, Vite, Axios e React Router
- Banco: PostgreSQL 17
- Infraestrutura: Docker Compose e GitHub Actions

## Arquitetura

O backend segue os princípios da Clean Architecture:

- `dominio/modelo`: entidades e regras centrais sem dependência de framework;
- `dominio/porta`: contratos de persistência;
- `aplicacao`: casos de uso de autenticação e cadastro clínico;
- `infraestrutura`: adaptadores JPA, JWT e configurações;
- `apresentacao`: controllers REST, DTOs, validação e tratamento de erros.

A direção das dependências parte das camadas externas para o domínio. Os casos de uso conhecem interfaces, não o Spring Data diretamente.

## Funcionalidades

- Cadastro e login;
- autenticação stateless com JWT;
- criação e atualização do cadastro clínico;
- nome, sobrenome, sexo, contato de emergência e tipo sanguíneo;
- listas de alergias, medicamentos, doenças e cirurgias;
- senha pública armazenada somente como hash BCrypt;
- geração de link com UUID não sequencial;
- QR Code contendo apenas o link, nunca a senha;
- consulta pública após confirmação da senha;
- exclusão definitiva que invalida imediatamente o link;
- layout responsivo e impressão do QR Code;
- migrations e CI para backend e frontend.

## Executar tudo com Docker

Requisitos: Docker Desktop.

```bash
docker compose up --build
```

Acesse:

- Frontend: http://localhost:5173
- API: http://localhost:8080

Para parar:

```bash
docker compose down
```

Para apagar também os dados locais:

```bash
docker compose down -v
```

## Executar em desenvolvimento

Inicie apenas o PostgreSQL:

```bash
docker compose up banco -d
```

Backend:

```bash
cd backend
mvn spring-boot:run
```

Frontend (outro terminal):

```bash
cd frontend
npm install
npm run dev
```

## Rotas da API

| Método | Endpoint | Autenticação | Função |
|---|---|---|---|
| POST | `/api/autenticacao/cadastro` | Pública | Criar conta |
| POST | `/api/autenticacao/entrar` | Pública | Entrar |
| GET | `/api/perfil` | JWT | Consultar próprio cadastro |
| PUT | `/api/perfil` | JWT | Criar ou atualizar cadastro |
| DELETE | `/api/perfil` | JWT | Excluir e invalidar link |
| POST | `/api/publico/{publicId}` | Senha pública | Consultar dados clínicos |
| GET | `/api/publico/{publicId}/codigo-qr` | JWT | Baixar QR Code em PNG |

Nas rotas protegidas, envie `Authorization: Bearer <token>`.

### Exemplo de cadastro clínico

```json
{
  "nome": "Bruno",
  "sobrenome": "Beneduzi",
  "sexo": "Masculino",
  "contatoEmergencia": "Maria",
  "telefoneContatoEmergencia": "(51) 99999-9999",
  "tipoSanguineo": "O+",
  "alergias": ["Penicilina"],
  "medicamentos": ["Losartana 50mg"],
  "doencas": [],
  "cirurgias": ["Apendicectomia"],
  "senhaPublica": "4827"
}
```

## Segurança e produção

Antes de publicar:

1. defina `CHAVE_JWT` com um segredo aleatório longo;
2. use HTTPS;
3. configure a origem permitida do CORS para o domínio real;
4. não publique senhas no repositório;
5. use credenciais próprias do PostgreSQL;
6. ajuste `URL_PUBLICA` para o endereço do frontend.

O projeto é acadêmico e as informações exibidas não substituem avaliação médica.
