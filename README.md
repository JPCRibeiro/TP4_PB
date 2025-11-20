# Sistema de Gestão de Biblioteca de Jogos (Refatorado)

Este projeto é uma aplicação web em Java utilizando o framework **Javalin**, focada na gestão de usuários e suas bibliotecas de jogos. O sistema utiliza **SQLite** para persistência de dados e renderização de HTML no servidor (Server-Side Rendering).

Recentemente, o projeto passou por uma refatoração profunda para adotar princípios de **Clean Code**, **Injeção de Dependência** e **Testes Unitários**.

---

## Manual de Execução

### Pré-requisitos
* **Java JDK 17** ou superior.
* **Maven** instalado e configurado.

### Como rodar a aplicação integrada

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/JPCRibeiro/TP4_PB
    cd TP4_PB
    ```

2.  **Instalar dependências e compilar:**
    Execute o comando abaixo para baixar as bibliotecas (Javalin, SQLite, JUnit, Mockito) e compilar o projeto:
    ```bash
    mvn clean install
    ```

3.  **Executar a aplicação:**
    Execute a classe Main

4.  **Acessar:**
    Abra o navegador e acesse: `http://localhost:7000`.

### Como rodar os testes
Para verificar a integridade da refatoração, execute os testes unitários:
```bash
mvn test
