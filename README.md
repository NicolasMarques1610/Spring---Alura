***Primeira Parte - Desenvolvendo uma API REST em Java

*Spring Framework
  É um framework para desenvolvimento de aplicações em Java, muito usado em aplicações Web, podemos definir 
  as depências no arquivo .pom o que facilita o start do projeto. Foco nas configurações. Tem o Spring 
  Security para a segurança lida com controle de autenticação e autorização da aplicação, Spring Data xxx, 
  exemplo JPA, para facilitar o acesso aos dados, Spring MVC para conseguir receber os dados do front end 
  é para desenvolvimento de aplicações Web e API's Rest, Spring Boot que configura tudo facilitando muito 
  já que agiliza a criação do projeto.
*Objetivos e Tecnologias
  O objetivo do curso é usarmos o Spring Boot para desenvolvermos uma API Rest, com algumas funcionalidades. 
  A ideia é desenvolver um CRUD (Create, Read, Update, Delete), sendo as quatro operações fundamentais das 
  aplicações: cadastro, listagem, atualização e exclusão de informações. Vamos ver também como aplicar validações 
  das informações que chegam na nossa API, usando o Bean Validation.
  Utilizaremos o Lombok, responsável por fazer a geração de códigos repetitivos, como getters, setters, 
  toString, entre outros. Tudo via anotações para o código ficar menos verboso.
  Usaremos o banco de dados MySQL para armazenar as informações da API e junto com ele utilizaremos a 
  biblioteca Flyway. Isso para termos o controle do histórico de evolução do banco de dados, um conceito 
  que chamamos de Migration.
  Usaremos o Maven para gerenciar as dependências do projeto, e também para gerar o build da nossa aplicação. 
  Será focado na API Rest (apenas no Back-end), então não vai ter front end. Mas para testarmos a API, 
  usaremos o Insomnia, sendo uma ferramenta usada para testes em API. Com ela, conseguimos simular a requisição 
  para a API e verificar se as funcionalidades implementadas estão funcionando.
*Projeto
  Será trabalhado em um projeto de uma clínica médica fictícia. Temos uma empresa chamada Voll Med, que   
  possui uma clínica que precisa de um aplicativo para monitorar o cadastro de médicos, pacientes e agendamento   
  de consultas.
*Inicialização 
  Com o Spring Initializr iniciamos o projeto escolhendo qual vai ser o tipo do projeto nesse caso Maven, 
  a versão do Spring Boot que foi 3.0.6, o project metadata que definimos o pacote, a versão do Java, já 
  que é uma aplicação Java, e outras configurações padrões como nome, descriscão, etc. Depois disso incluimos 
  as dependências que queremos que o projeto tenha nesse caso foram Spring Boot DevTools para todas vez que 
  for salvo o programa com Ctrl+S é restartado a aplicação, Lombok que ajuda na geração de códigos repetitivos 
  e o Spring Web que busca combinar as várias dependências advindas de um projeto Spring Boot em uma única 
  dependência, retirando-se a necessidade de configuração de múltiplas dependências no Maven.
*Paginação(Pageable) e Ordenação(sort)
  .http://localhost:8080/medicos?sort=nome se colocarmos a interrogação e depois sort igual a nome, isso 
  quer dizer que queremos os nomes ordenados de forma crescente.
  .Além disso podemos combinar o sort com o pageable, http://localhost8080/medicos?sort=crm,desc&size=2&page=1 
  nesse caso o sort agora é pelo crm e decrescente e o tamanho da página é 2, então tem dois elementos na 
  pagina, e a página que estamos no momento é a 2 já que começa no 0.
  .Esse tipo de parâmetro com ? é chamado de Query e é usado em métodos Get.
  Por padrão, os parâmetros utilizados para realizar a paginação e a ordenação devem se chamar page, size 
  e sort. Entretanto, o Spring Boot permite que os nomes de tais parâmetros sejam modificados via configuração 
  no arquivo application.properties.
  Por exemplo, poderíamos traduzir para português os nomes desses parâmetros com as seguintes propriedades:
  spring.data.web.pageable.page-parameter=pagina
  spring.data.web.pageable.size-parameter=tamanho
  spring.data.web.sort.sort-parameter=ordem
  Com isso, nas requisições que utilizam paginação, devemos utilizar esses nomes que foram definidos. Por exemplo, 
  para listar os médicos de nossa API trazendo apenas 5 registros da página 2, ordenados pelo e-mail e de 
  maneira decrescente, a URL da requisição deve ser:
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
      Faremos ajustes na classe controller, para seguir as boas práticas do protocolo HTTP quanto ao retorno 
      dos códigos HTTP e das respostas que a API devolve.
  .Tratamento de erros:
      Eventualmente, pode ocorrer um erro na API, e precisamos entender o que o Spring faz ao ocorrer uma 
      exception enquanto o programa é executado, o que é devolvido como resposta para o cliente da API.
  .Autenticação/Autorização:
      Precisamos ter um controle de quem vai enviar as requisições. Isso será feito na aplicação front-end, 
      porém, na API precisamos ter um código que permite o usuário se autenticar, e também ter um controle de 
      acesso de informações públicas e privadas.
      Aprenderemos a aplicar isso com o Spring Security, sendo um módulo do Spring responsável por monitorar esse 
      controle.
  .Tokens JWT:
      No caso, usaremos a autenticação fundamentada em tokens com o padrão JSON Web Token (JWT).
*Códigos de requisição HTTP
  O protocolo HTTP (Hypertext Transfer Protocol, RFC 2616) é o protocolo responsável por fazer a comunicação entre o 
  cliente, que normalmente é um browser, e o servidor. Dessa forma, a cada “requisição” feita pelo cliente, o servidor 
  responde se ele obteve sucesso ou não.
  Os códigos HTTP (ou HTTPS) possuem três dígitos, sendo que o primeiro dígito significa a classificação dentro das 
  possíveis cinco categorias.
  1XX: Informativo – a solicitação foi aceita ou o processo continua em andamento; 
  2XX: Confirmação – a ação foi concluída ou entendida;
  3XX: Redirecionamento – indica que algo mais precisa ser feito ou precisou ser feito para completar a solicitação; 
  4XX: Erro do cliente – indica que a solicitação não pode ser concluída ou contém a sintaxe incorreta; 
  5XX: Erro no servidor – o servidor falhou ao concluir a solicitação.
  Alguns códigos de requisição: 
  .200 - significa OK e mensagem é "No body returned for response";
  .201 - código created dizendo que um registro foi criado na API, devolve no corpo da resposta os dados do novo 
  recurso/registro criado e um cabeçalho do protocolo HTTP (Location), esse Location mostra o endereço para que o 
  front-end, ou aplicativo mobile consiga acessar o recurso cadastrado.
  .204 - no método de excluir é mais adequado e se refere à requisição processada e sem conteúdo na resposta;
  .400 - erro de validação do bean validation;
  .403 - é o erro “Proibido”. Significa que o servidor entendeu a requisição do cliente, mas se recusa a processá-la, 
  pois o cliente não possui autorização para isso;
  .404 - significa que essa URL não te levou a lugar nenhum. Pode ser que a aplicação não exista mais, a URL mudou ou 
  você digitou a URL errada;
  .422  - Unprocessable Entity, indica que o servidor entende o tipo de conteúdo da entidade da requisição, e a sintaxe 
  da requisição esta correta, mas não foi possível processar as instruções presentes;
  .500 - status de erro que indica uma dificuldade de processamento do servidor, a partir de uma incompatibilidade ou 
  configuração incorreta;
  .503 - significa que o serviço acessado está temporariamente indisponível. Causas comuns são um servidor em manutenção 
  ou sobrecarregado. Ataques maliciosos, como o DDoS, causam bastante esse problema.
*Spring Security
  Nós fornece uma ferramenta para implementarmos autenticação e autorização no projeto e nos proteger dos principais ataques. 
  Isso para não precisarmos implementar o código que protege a aplicação, sendo que já temos disponível.
  Objetivos:
    .Autenticação - como o usuário efetua o login na aplicação, pode ser através de um preenchimento de um formulário/ 
    autenticação via token/ algum protocolo. Assim, ele possui uma maior flexibilidade para lidar com diversas possibilidades 
    de aplicar um controle de autenticação;
    .Autorização (controle de acesso) - por exemplo, temos esses usuários e eles possuem a permissão "A", já estes usuários possuem 
    a permissão "B". Os usuários com a permissão "A" podem acessar as URLs, os que tiverem a permissão "B", além dessas URLs, podem acessar outras URLs;
    .Prot eção contra-ataques (CSRF, clickjacking, etc.) - há, também, um mecanismo de proteção contra os principais ataques que ocorre em uma aplicação, 
    como o CSRF (Cross Site Request Forgery) e o clickjacking.
  Tem diferença na autentificação de uma API REST para uma aplicação Web tradicional, o tradicional temos o conceito Stateful que mantém no servidor as 
  sessões (espaços de memória) de cada usuário que contém seus dados. Já na API REST não devemos armazenar estado porque um dos conceitos é ser Stateless, 
  o servidor processa a requição e devolve a resposta, na próxima requisição o servidor não saberá identificar quem envio a requisição. A autentificação 
  que será feita é através de tokens, usando o JWT - JSON Web Tokens.
  Para criação de hash de senha foi usado o algoritmo BCrypt.
*Geração do token usando a biblioteca java-jwt
  api.security.token.secret=${JWT_SECRET:12345678}, esse comando é usado para pegar uma variável de ambiente
  quando se está fazendo um deploy se caso não tiver é usado o outro valor como secret.
  Aqui geramos o token.
*Controlar o acesso 
  Aqui fizemos o armazenamento do token pelo cliente, aplicação mobile/front-end, e o envio do token em cada 
  requisiçao feita e ainda sim, a validadação desse token.
  Filter é um dos recursos que fazem parte da especificação de Servlets, a qual padroniza o tratamento de 
  requisições e respostas em aplicações Web no Java. Ou seja, tal recurso não é específico do Spring, podendo 
  assim ser utilizado em qualquer aplicação Java.
  É um recurso muito útil para isolar códigos de infraestrutura da aplicação, como, por exemplo, segurança, 
  logs e auditoria, para que tais códigos não sejam duplicados e misturados aos códigos relacionados às regras 
  de negócio da aplicação.
  Precisamos dizer para o spring qual é a ordem dos filtros, se vai primeiro o nosso ou o dele, se não for 
  especificado primeiro vai o dele, que é ver se o usuário está logado.
*O que aprendi
  Boas práticas da API: 
    .Utilizar a classe ResponseEntity, do Spring, para personalizar os retornos dos métodos de uma classe Controller;
    .Modificar o código HTTP devolvido nas respostas da API;
    .Adicionar cabeçalhos nas respostas da API;
    .Utilizar os códigos HTTP mais apropriados para cada operação realizada na API;
  Tratamento de erros:
    .Criar uma classe para isolar o tratamento de exceptions da API, com a utilização da anotação @RestControllerAdvice;
    .Utilizar a anotação @ExceptionHandler, do Spring, para indicar qual exception um determinado método da classe de tratamento de erros deve capturar;
    .Tratar erros do tipo 404 (Not Found) na classe de tratamento de erros;
    .Tratar erros do tipo 400 (Bad Request), para erros de validação do Bean Validation, na classe de tratamento de erros;
    .Simplificar o JSON devolvido pela API em casos de erro de validação do Bean Validation;
  Autenticação/Autorização (Spring Security):
    .Funciona o processo de autenticação e autorização em uma API Rest;
    .Adicionar o Spring Security ao projeto;
    .Funciona o comportamento padrão do Spring Security em uma aplicação;
    .Implementar o processo de autenticação na API, de maneira Stateless, utilizando as classes e configurações do Spring Security.
  JSON Web Token:
    .Adicionar a biblioteca Auth0 java-jwt como dependência do projeto;
    .Utilizar essa biblioteca para realizar a geração de um token na API;
    .Injetar uma propriedade do arquivo application.properties em uma classe gerenciada pelo Spring, utilizando a anotação @Value;
    .Devolver um token gerado na API quando um usuário se autenticar nela.
  Controle de acesso:
  Funcionam os Filters em uma requisição;
    .Implementar um filter criando uma classe que herda da classe OncePerRequestFilter, do Spring;
    .Utilizar a biblioteca Auth0 java-jwt para realizar a validação dos tokens recebidos na API;
    .Realizar o processo de autenticação da requisição, utilizando a classe SecurityContextHolder, do Spring;
    .Liberar e restringir requisições, de acordo com a URL e o verbo do protocolo HTTP.

***Terceira parte - Documentação, teste e deploy de uma API

*Objetivos
  .Funcionalidades de agendamento de consultas;
  .Documentação da API;
  .Teste automatizados;
  .Build do projeto.
*Códigos para novas funcionalidades
  .Controller;
  .DTOs;
  .Entidade JPA;
  .Repository;
  .Migration;
  .Regras de negócio.
*Exemplos de como resolver o problema se o usuário escrever os nomes dos atributos no JSON diferente de como ta na API
  public record DadosCompra(
  @JsonAlias(“produto_id”) Long idProduto,
  @JsonAlias(“data_da_compra”) LocalDate dataCompra
  ){}
  Podemos fazer dessa forma acima usando JsonAlias que mapeia "apelidos" alternativos para o atributo.
*Exemplo de como podemos formatar a data
  @NotNull
  @Future
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  LocalDateTime data
*Padrão Service
  O Padrão Service é muito utilizado na programação e seu nome é muito comentado. Mas apesar de ser um nome único, 
  Service pode ser interpretado de várias maneiras: pode ser um Use Case (Application Service); um Domain Service, 
  que possui regras do seu domínio; um Infrastructure Service, que usa algum pacote externo para realizar tarefas; etc.
  Apesar da interpretação ocorrer de várias formas, a ideia por trás do padrão é separar as regras de negócio, as regras 
  da aplicação e as regras de apresentação para que elas possam ser facilmente testadas e reutilizadas em outras partes do sistema.
  Existem duas formas mais utilizadas para criar Services. Você pode criar Services mais genéricos, responsáveis por todas as 
  atribuições de um Controller; ou ser ainda mais específico, aplicando assim o S do SOLID: 
  Single Responsibility Principle (Princípio da Responsabilidade Única). Esse princípio nos diz que uma classe/função/arquivo 
  deve ter apenas uma única responsabilidade.
  Mas é importante ficarmos atentos, pois muitas vezes não é necessário criar um Service e, consequentemente, adicionar mais uma camada 
  e complexidade desnecessária à nossa aplicação. Uma regra que podemos utilizar é a seguinte: se não houverem regras de negócio, 
  podemos simplesmente realizar a comunicação direta entre os controllers e os repositories da aplicação.
*Strategy
  A ideia do Strategy é ter várias classes implementando uma estratégia, mas utilizaríamos apenas uma das estratégias.
  Aqui não estamos usando só uma das estratégias, estamos chamando todas.
*Princípios do SOLID
  SOLID é uma sigla que representa cinco princípios de programação:
  .Single Responsibility Principle (Princípio da Responsabilidade Única)
  .Open-Closed Principle (Princípio Aberto-Fechado)
  .Liskov Substitution Principle (Princípio da Substituição de Liskov)
  .Interface Segregation Principle (Princípio da Segregação de Interface)
  .Dependency Inversion Principle (Princípio da Inversão de Dependência)
  Nesse momento das regras de negócio estamos aplicando no código, de uma vez, três princípios do SOLID.
  Estamos aplicando os seguintes princípios do SOLID:
  .Single Responsibility Principle (Princípio da responsabilidade única): porque cada classe de validação tem apenas uma responsabilidade.
  .Open-Closed Principle (Princípio aberto-fechado): na classe service, AgendadeConsultas, porque ela está fechada para modificação, não 
  precisamos mexer nela. Mas ela está aberta para extensão, conseguimos adicionar novos validadores apenas criando as classes implementando a interface.
  .Dependency Inversion Principle (Princípio da inversão de dependência): porque nossa classe service depende de uma abstração, que é a interface, 
  não depende dos validadores, das implementações especificamente. O módulo de alto nível, a service, não depende dos módulos de baixo nível, que são os validadores.
  Com isso ganhamos um código fácil de entender, fácil de dar manutenção, fácil de estender e de testar com testes automatizados.
*Benefícios da documentação
  A documentação gerada seguindo o padrão OpenAPI permite que ferramentas, como o Swagger UI, possam ser utilizadas para realizar testes na API.
  A documentação facilita muito a vida de quem deseja se integrar à uma API Rest, pois expõe as suas funcionalidades e como elas devem ser consumidas.
*Teste automatizados com Spring Boot
  Foi testado os controllers que tem os endpoints e o repository que são as queries, como também a parte das regras 
  de negócio.
  Quando fazemos testes na interface repository utilizando o spring dataJPA todos os testes são feitos de forma isolada
  os valores inseridos não interferem nos outros teste porque acontece um rollback depois de feito o teste.
*Native Image
  Imagem nativa é uma tecnologia utilizada para compilar uma aplicação Java, incluindo todas as suas dependências, gerando um arquivo binário 
  executável que pode ser executado diretamente no sistema operacional, sem a necessidade de se utilizar a JVM. Mesmo sem executar numa JVM, a 
  aplicação também contará com os recursos dela, como gerenciamento de memória, garbage collector e controle de execução de threads.
  Para saber mais detalhes sobre a tecnologia de imagens nativas acesse a documentação no site: https://www.graalvm.org/native-image
*O que aprendi
  Funcionalidade agendamento de consultas:
    .Implementar uma nova funcionalidade no projeto;
    .Avaliar quando é necessário criar uma classe Service na aplicação;
    .Criar uma classe Service, com o objetivo de isolar códigos de regras de negócio, utilizando para isso a anotação @Service;
    .Implementar um algoritmo para a funcionalidade de agendamento de consultas;
    .Realizar validações de integridade das informações que chegam na API;
    .Implementar uma consulta JPQL (Java Persistence Query Language) complexa em uma interface repository, utilizando para isso a anotação @Query.
  Regras de negócio:
    .Isolar os códigos de validações de regras de negócio em classes separadas, utilizando nelas a anotação @Component do Spring;
    .Finalizar a implementação do algoritmo de agendamento de consultas;
    .Utilizar os princípios SOLID para deixar o código da funcionalidade de agendamento de consultas mais fácil de entender, evoluir e testar.
  Documentação da API:
    .Adicionar a biblioteca SpringDoc no projeto para que ela faça a geração automatizada da documentação da API;
    .Analisar a documentação do SpringDoc para entender como realizar a sua configuração em um projeto;
    .Acessar os endereços que disponibilizam a documentação da API nos formatos yaml e html;
    .Utilizar o Swagger UI para visualizar e testar uma API Rest;
    .Configurar o JWT na documentação gerada pelo SpringDoc.
  Testes Automatizados:
    .Escrever testes automatizados em uma aplicação com Spring Boot;
    .Escrever testes automatizados de uma interface Repository, seguindo a estratégia de usar o mesmo banco de dados que a aplicação utiliza;
    .Sobrescrever propriedades do arquivo application.properties, criando outro arquivo chamado application-test.properties que seja carregado apenas ao executar os testes, utilizando para isso a anotação @ActiveProfiles;
    .Escrever testes automatizados de uma classe Controller, utilizando a classe MockMvc para simular requisições na API;
    .Testar cenários de erro 400 e código 200 no teste de uma classe controller.
  Build do projeto:
    .Funciona o build de uma aplicação com Spring Boot;
    .Utilizar arquivos de propriedades específicos para cada profile, alterando em cada arquivo as propriedades que precisam ser modificadas;
    .Configurar informações sensíveis da aplicação, como dados de acesso ao banco de dados, via variáveis de ambiente;
    .Realizar o build do projeto via Maven;
    .Executar a aplicação via terminal, com o comando java -jar, passando as variáveis de ambiente como parâmetro.