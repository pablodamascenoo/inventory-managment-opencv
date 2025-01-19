# Inventory Management com OpenCV

## Descrição
Este projeto é uma aplicação web para gerenciamento de inventário utilizando OpenCV para processamento de imagens.

## Funcionalidades
- Cadastro de produtos
- Atualização de estoque
- Remoção de produtos
- Visualização de inventário
- Processamento de imagens com OpenCV

## Tecnologias Utilizadas
- Java
- Spring Boot
- OpenCV
- MySQL
- HTML/CSS/JavaScript

## Requisitos
- JDK 11 ou superior
- Maven
- MySQL
- OpenCV

## Instalação
1. Clone o repositório:
    ```sh
    git clone https://github.com/usuario/inventory-managment-opencv.git
    ```
2. Navegue até o diretório do projeto:
    ```sh
    cd inventory-managment-opencv
    ```
3. Configure o banco de dados MySQL com as credenciais apropriadas.
4. Compile e execute o projeto:
    ```sh
    mvn spring-boot:run
    ```

## Uso
Acesse a aplicação em `http://localhost:8080` e utilize a interface web para gerenciar seu inventário.

### Execução em Desenvolvimento

Para que as alterações em estilo feitas com tailwindCss tomem efeito, é necessário executar o seguinte comando no diretório do projeto em paralelo a execução do projeto em si:

```bash
npx tailwindcss -i ./src/main/resources/static/inputs.css -o ./src/main/resources/static/output.css --watch
```

## Contribuição
Contribuições são bem-vindas! Por favor, abra uma issue ou envie um pull request.

## Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
