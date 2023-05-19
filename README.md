***Primeira Parte - Desenvolvendo uma API REST em Java

*Spring Framework
  É um framework para desenvolvimento de aplicações em Java, muito usado em aplicações Web, podemos definir as depências no arquivo .pom o que facilita o start do projeto. Foco nas configurações. Tem o Spring Security para a segurança lida com controle de autenticação e autorização da aplicação, Spring Data xxx, exemplo JPA, para facilitar o acesso aos dados, Spring MVC para conseguir receber os dados do front end é para desenvolvimento de aplicações Web e API's Rest, Spring Boot que configura tudo facilitando muito já que agiliza a criação do projeto.
*Objetivos e Tecnologias
  O objetivo do curso é usarmos o Spring Boot para desenvolvermos uma API Rest, com algumas funcionalidades. A ideia é desenvolver um CRUD (Create, Read, Update, Delete), sendo as quatro operações fundamentais das aplicações: cadastro, listagem, atualização e exclusão de informações. Vamos ver também como aplicar validações das informações que chegam na nossa API, usando o Bean Validation.
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
*O que aprendi nessa primeira parte do projeto de desenvolvimento de uma API REST
  .Criar um projeto Spring Boot utilizando o site do Spring Initializr;
  .Importar o projeto no IntelliJ e executar uma aplicação Spring Boot pela classe contendo o método main;
  .Criar uma classe Controller e mapear uma URL nela utilizando as anotações @RestController e @RequestMapping;
  .Realizar uma requisição de teste no browser acessando a URL mapeada no Controller.
  .Mapear requisições POST em uma classe Controller;
  .Enviar requisições POST para a API utilizando o Insomnia;
  .Enviar dados para API no formato JSON;
  .Utilizar a anotação @RequestBody para receber os dados do corpo da requisição em um parâmetro no Controller;
  .Utilizar o padrão DTO (Data Transfer Object), via Java Records, para representar os dados recebidos em uma requisição POST.
  .Adicionar novas dependências no projeto;
  .Mapear uma entidade JPA e criar uma interface Repository para ela;
  .Utilizar o Flyway como ferramenta de Migrations do projeto;
  .Realizar validações com Bean Validation utilizando algumas de suas anotações, como a @NotBlank.
  .Utilizar a anotação @GetMapping para mapear métodos em Controllers que produzem dados;
  .Utilizar a interface Pageable do Spring para realizar consultas com paginação;
  .Controlar a paginação e a ordenação dos dados devolvidos pela API com os parâmetros page, size e sort;
  .Configurar o projeto para que os comandos SQL sejam exibidos no console.
  .Mapear requisições PUT com a anotação @PutMapping;
  .Escrever um código para atualizar informações de um registro no banco de dados;
  .Mapear requisições DELETE com a anotação @DeleteMapping;
  .Mapear parâmetros dinâmicos em URL com a anotação @PathVariable;
  .Implementar o conceito de exclusão lógica com o uso de um atributo booleano.

***Segunda parte - Aplicando boas práticas e protegendo uma API REST

*Objetivos
  .Boas práticas na API referente ao protocolo HTTP: 
      Faremos ajustes na classe controller, para seguir as boas práticas do protocolo HTTP quanto ao retorno dos códigos HTTP e das respostas que a API devolve.
  .Tratamento de erros:
      Eventualmente, pode ocorrer um erro na API, e precisamos entender o que o Spring faz ao ocorrer uma exception enquanto o programa é executado, o que é devolvido como resposta para o cliente da API.
  .Autenticação/Autorização:
      Precisamos ter um controle de quem vai enviar as requisições. Isso será feito na aplicação front-end, porém, na API precisamos ter um código que permite o usuário se autenticar, e também ter um controle de acesso de informações públicas e privadas.
      Aprenderemos a aplicar isso com o Spring Security, sendo um módulo do Spring responsável por monitorar esse controle.
  .Tokens JWT:
      No caso, usaremos a autenticação fundamentada em tokens com o padrão JSON Web Token (JWT).
*Códigos de requisição HTTP
  O protocolo HTTP (Hypertext Transfer Protocol, RFC 2616) é o protocolo responsável por fazer a comunicação entre o cliente, que normalmente é um browser, e o servidor. Dessa forma, a cada “requisição” feita pelo cliente, o servidor responde se ele obteve sucesso ou não.
  Os códigos HTTP (ou HTTPS) possuem três dígitos, sendo que o primeiro dígito significa a classificação dentro das possíveis cinco categorias.
  1XX: Informativo – a solicitação foi aceita ou o processo continua em andamento; 
  2XX: Confirmação – a ação foi concluída ou entendida;
  3XX: Redirecionamento – indica que algo mais precisa ser feito ou precisou ser feito para completar a solicitação; 
  4XX: Erro do cliente – indica que a solicitação não pode ser concluída ou contém a sintaxe incorreta; 
  5XX: Erro no servidor – o servidor falhou ao concluir a solicitação.
  Alguns códigos de requisição: 
  .200 - significa OK e mensagem é "No body returned for response";
  .201 - código created dizendo que um registro foi criado na API, devolve no corpo da resposta os dados do novo recurso/registro criado e um cabeçalho do protocolo HTTP (Location), esse Location mostra o endereço para que o front-end, ou aplicativo mobile consiga acessar o recurso cadastrado.
  .204 - no método de excluir é mais adequado e se refere à requisição processada e sem conteúdo na resposta;
  .400 - erro de validação do bean validation;
  .403 - é o erro “Proibido”. Significa que o servidor entendeu a requisição do cliente, mas se recusa a processá-la, pois o cliente não possui autorização para isso;
  .404 - significa que essa URL não te levou a lugar nenhum. Pode ser que a aplicação não exista mais, a URL mudou ou você digitou a URL errada;
  .422  - Unprocessable Entity, indica que o servidor entende o tipo de conteúdo da entidade da requisição, e a sintaxe da requisição esta correta, mas não foi possível processar as instruções presentes;
  .500 - status de erro que indica uma dificuldade de processamento do servidor, a partir de uma incompatibilidade ou configuração incorreta;
  .503 - significa que o serviço acessado está temporariamente indisponível. Causas comuns são um servidor em manutenção ou sobrecarregado. Ataques maliciosos, como o DDoS, causam bastante esse problema.

*O que aprendi
  .Utilizar a classe ResponseEntity, do Spring, para personalizar os retornos dos métodos de uma classe Controller;
  .Modificar o código HTTP devolvido nas respostas da API;
  .Adicionar cabeçalhos nas respostas da API;
  .Utilizar os códigos HTTP mais apropriados para cada operação realizada na API;
  .Criar uma classe para isolar o tratamento de exceptions da API, com a utilização da anotação @RestControllerAdvice;
  .Utilizar a anotação @ExceptionHandler, do Spring, para indicar qual exception um determinado método da classe de tratamento de erros deve capturar;
  .Tratar erros do tipo 404 (Not Found) na classe de tratamento de erros;
  .Tratar erros do tipo 400 (Bad Request), para erros de validação do Bean Validation, na classe de tratamento de erros;
  .Simplificar o JSON devolvido pela API em casos de erro de validação do Bean Validation;