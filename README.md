*Spring Framework
  É um framework para desenvolvimento de aplicações em Java, muito usado em aplicações Web, podemos definir as depências no arquivo .pom o que facilita o start do projeto. Foco nas configurações. Tem o Spring Security para a segurança lida com controle de autenticação e autorização da aplicação, Spring Data xxx, exemplo JPA, para facilitar o acesso aos dados, Spring MVC para conseguir receber os dados do front end é para desenvolvimento de aplicações Web e API's Rest, Spring Boot que configura tudo facilitando muito já que agiliza a criação do projeto.
*Objetivos e Tecnologias
  O objetivo do curso é usarmos o Spring Boot para desenvolvermos uma API Rest, com algumas funcionalidades. A ideia é desenvolver um CRUD, sendo as quatro operações fundamentais das aplicações: cadastro, listagem, atualização e exclusão de informações. Vamos ver também como aplicar validações das informações que chegam na nossa API, usando o Bean Validation.
  Utilizaremos o Lombok, responsável por fazer a geração de códigos repetitivos, como getters, setters, toString, entre outros. Tudo via anotações para o código ficar menos verboso.
  Usaremos o banco de dados MySQL para armazenar as informações da API e junto com ele utilizaremos a biblioteca Flyway. Isso para termos o controle do histórico de evolução do banco de dados, um conceito que chamamos de Migration.
  Usaremos o Maven para gerenciar as dependências do projeto, e também para gerar o build da nossa aplicação. Será focado na API Rest (apenas no Back-end), então não vai ter front end. Mas para testarmos a API, usaremos o Insomnia, sendo uma ferramenta usada para testes em API. Com ela, conseguimos simular a requisição para a API e verificar se as funcionalidades implementadas estão funcionando.
*Projeto
  Será trabalhado em um projeto de uma clínica médica fictícia. Temos uma empresa chamada Voll Med, que possui uma clínica que precisa de um aplicativo para monitorar o cadastro de médicos, pacientes e agendamento de consultas.
*Inicialização 
  Com o Spring Initializr iniciamos o projeto escolhendo qual vai ser o tipo do projeto nesse caso Maven, a versão do Spring Boot que foi 3.0.6, o project metadata que definimos o pacote, a versão do Java, já que é uma aplicação Java, e outras configurações padrões como nome, descriscão, etc. Depois disso incluimos as dependências que queremos que o projeto tenha nesse caso foram Spring Boot DevTools para todas vez que for salvo o programa com Ctrl+S é restartado a aplicação, Lombok que ajuda na geração de códigos repetitivos e o Spring Web que busca combinar as várias dependências advindas de um projeto Spring Boot em uma única dependência, retirando-se a necessidade de configuração de múltiplas dependências no Maven.
*Paginação(Pageable) e Ordenação(sort)
  .http://localhost:8080/medicos?sort=nome se colocarmos a interrogação e depois sort igual a nome, isso quer dizer que queremos os nomes ordenados de forma crescente.
  .Além disso podemos combinar o sort com o pageable, http://localhost8080/medicos?sort=crm,desc&size=2&page=1 nesse caso o sort agora é pelo crm e decrescente e o tamanho da página é 2, então tem dois elementos na pagina, e a página que estamos no momento é a 2 já que começa no 0.
  .Esse tipo de parâmetro com ? é chamado de Query e é usado em métodos Get.
  Por padrão, os parâmetros utilizados para realizar a paginação e a ordenação devem se chamar page, size e sort. Entretanto, o Spring Boot permite que os nomes de tais parâmetros sejam modificados via configuração no arquivo application.properties.
  Por exemplo, poderíamos traduzir para português os nomes desses parâmetros com as seguintes propriedades:
  spring.data.web.pageable.page-parameter=pagina
  spring.data.web.pageable.size-parameter=tamanho
  spring.data.web.sort.sort-parameter=ordem
  Com isso, nas requisições que utilizam paginação, devemos utilizar esses nomes que foram definidos. Por exemplo, para listar os médicos de nossa API trazendo apenas 5 registros da página 2, ordenados pelo e-mail e de maneira decrescente, a URL da requisição deve ser:
  http://localhost:8080/medicos?tamanho=5&pagina=1&ordem=email,desc