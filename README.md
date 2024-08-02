# DESAFIO PARA VAGA DE ESTÁGIO CASAL

## Sistema de Gerenciamento de Recursos (Requisitos mínimos)

### 1. Tipos de usuários:

- Teremos dois tipos de usuário: **"admin"** e **"user"**.

### 2. Modelo de recursos:

- Cada recurso terá um ID único e um nome.

### 3. CRUD para usuários:

- Implementar as operações CRUD (Create, Read, Update, Delete) para usuários.

### 4. CRUD para recursos:

- Implementar as operações CRUD para recursos.

### 5. Funcionalidades específicas para **admins**:

- **Admins** poderão:
  - Registrar novos recursos.
  - Atualizar informações de recursos existentes.
  - Deletar recursos.
  - Deletar usuários.

### 6. Funcionalidades específicas para **users**:

- **Users** poderão:
  - Listar todos os recursos disponíveis.
  - Alocar recursos, especificando a data de início e devolução.

### 7. Container Docker:

- O aplicativo deve ser empacotado em um container Docker para facilitar a implamtação e a escabilidade.

### 8. Documentação:

- Criar uma documentação clara da aplicação, incluindo detalhes sobre os endpoints da API e o banco de dados relacional.

### Sugestões para a documentação:

- De classes, esquemas de db: **Javadoc**.
- De sequência, relacionamento: **Diagramas UML**.
- De estrutura do db: **Diagramas ER**.
- De API: **Swagger**.
- Guias (instruções de instalação e uso) + Tutoriais (como exemplos e mídias): **Markdown, HTML, PDF, Wiki, Confluence**.
  OBS.: Neste último item, pode incluir: visão geral do sistema, detalhes técnicos sobre sua arquitetura de software,
  fluxos de trabalho, comentários sobre a lógica de consultas SQL complexas, passos necessários para migração 
  (backup, restauração e atualização) do banco de dados.