# Vehicle Rental API

A Vehicle Rental API é uma API para realização de locações de uma locadora de veículos. Esta API fornece uma interface para o gerenciamento dos usuários, veículos e locações do sistema.

## Objetivos

1. Gerenciamento (CRUD) de usuários (administradores e clientes).
2. Gerenciamento (CRUD) de veículos (carros e motocicletas).
3. Gerenciamento (CRUD) de locações/alugueis de veículos.
4. Autenticação (via JWT) e autorização (por roles/papeis) de uso dos recursos da API.

Consulte a descrição do desafio original disponível em [Desafio - Sistema de Gerenciamento de Recursos](docs/challenge-description.md).

## Modelo Relacional

## Regras de negócio

1. Apenas administradores podem criar, atualizar ou deletar veículos do sistema.
2. Apenas administradores podem visualizar a lista de usuários cadastrados no sistema.
3. Apenas administradores podem atualizar ou deletar registros de locações/aluguel do sistema.
4. Usuários administradores podem locar/alugar veículos para qualquer usuário do sistema (eles inclusos).
5. Usuários não administradores podem locar/alugar veículos apenas para si mesmos.
6. Todos os usuários (administradores ou não) podem visualizar a lista de veículos cadastrados no sistema.

## Requisitos para execução

- Docker
- Docker compose

Obs.: Se optado por executar a aplicação localmente e não em um container Docker, também será necessário instalar e utilizar a JDK 17 e o Maven (gerenciador de dependências). Nesse caso, por favor não esquecer de construir o serviço com o banco de dados da aplicação definido no arquivo [`docker-compose`](docker-compose.yml).

## Executando o projeto via Docker pela primeira vez

1. Crie um clone do repositório (ou faça o download do arquivo .zip):

    ```
    git clone https://github.com/Maelton/challenge-casal
    ```

2. Entre no diretório do projeto e construa sua imagem Docker:

    ```
    docker build -t vehicle-rental-api:latest ./
    ```

3. Crie e execute os containers da aplicação:

    ```
    docker compose up --build --force-recreate --remove-orphans --renew-anon-volumes -d
    ```

A API estará disponível na porta local `8080`, o banco de dados postgres na porta `5433` e o PgAdmin (uso opcional, apague o serviço do [`docker-compose`](docker-compose.yml) se desejar) na porta `8081` . Atente-se às portas sendo expostas pelos containers e as portas em uso no seu ambiente local, mas fique a vontade para personalizar o arquivo [`docker-compose`](docker-compose.yml) presente no diretório do projeto em caso de necessidade, use-o também para verificar os serviços e containers sendo criados para a aplicação.

Obs.: Em ambientes Unix e derivados a execução pode ser facilitada através do uso do arquivo [`start.sh`](start.sh) presente no diretório do projeto. O arquivo também pode ser consultado em caso de dúvidas durante a disponibilização da aplicação via Docker.

## Documentação da API

Assim que o container da aplicação estiver em execução e a API estiver disponível, você poderá acessar a interface de usuário do Swagger para documentação de APIs através do link:


`http://localhost:8080/swagger-ui/index.html`

Obs.: Modifique a porta no endereço caso a porta padrão da aplicação seja alterada.

## Endpoints da API

- Authentication: `/v1/auth/users`
- Users: `/v1/users`
- Vehicles: `/v1/vehicles`
- Car: `/v1/cars`
- Motorcycle: `/v1/motorcycles`
- Rentals: `/v1/rentals`

## Guia de uso

Acesse o guia de uso da API para explicações mais específicas: [Vehicle Rental API - Guia de Uso](docs/guide/usage-guide.md).