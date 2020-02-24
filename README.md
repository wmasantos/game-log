# Quake log parser API

<img src="https://github.com/wmasantos/game-log/blob/master/Quake.jpeg"/>
  
## 1 - Solução proposta  
  
Desenvolvimento de um parser do arquivo de log do jogo Quake 3 Arena.<br>  
Com a finalidade de expor endpoints para listagem dos jogos ou detalhamento de um jogo específico passado como parâmetro.<br>  
  
Java foi a linguagem escolhida para o desenvolvimento, contando com as seguintes implementações que ajudam a manter a consistência de código, documentação, erros e métricas da API  
  
 - **Travis CI** mantendo cada commit monitorado por testes unitários, integração e build do projeto  
 - **Actuator + Prometheus + Grafana** fazendo o monitoramento e métricas contabilizando acessos aos endpoints da API.  
 - **Sentry** capturando os erros que são lançados pela API.  
 - **Java Code Coverage** atuando juntamente com o CI para garantir a cobertura de código nos testes.  
 - **SonarCloud** também integrado ao CI para garantir não existir quebras de código, bugs, vulnerabilidades no código e bibliotecas com vulnerabilidades descobertas.  
 - **DockerFile** o projeto conta também com o arquivo Dockerfile para deploy simplificado em um ambiente de containeres.  
 - **Swagger** para gerar automaticamente a documentação dos endpoints.
 - **Segurança** basic auth habilitada na aplicação para proteção dos endpoints, dentro do projeto existe um arquivo export do postman chamado **GameLog.postman_collection.json** , este arquivo já conta com os headers de autenticação configurados, basta importá-lo para utilização.
  
## 2 - Setup da aplicação  
  
#### 2.1 - Utilizando Docker  
  
Na raiz do projeto se encontra o arquivo Dockerfile, onde a variavel de ambiente SENTRY_DSN poderá ser colocada utilizando sua chave de API do sentry(OPCIONAL).  
  
```dockerfile  
FROM maven  
EXPOSE 1337  
ADD / game-log  
WORKDIR game-log  
ENV SENTRY_DSN=""  
RUN mvn clean && mvn install  
ENTRYPOINT ["java", "-jar", "target/game-logs.jar"]  
```  
  
Execute o comando abaixo para gerar a imagem do projeto.  
```  
docker build -f Dockerfile -t game-logs .  
```  
Após gerada a imagem, execute o docker run para criar o container.  
```  
docker run -d -p 1337:1337 --name "Quake3Arena-Logs" game-logs  
```  
Executado os comandos, a aplicação estará de pé rodando na porta 1337. Consulte a documentação na [Seção 3](#3-Documentação)  
  
#### 2.2 - Executando em ambiente windows ou linux  
Certifique-se de ter o java e o maven instalados previamente.  
  
Adicione a variável de ambiente SENTRY_DSN com sua chave de API(OPCIONAL).<br>  
Dentro do projeto execute os comandos.  
```  
mvn clean  
```  
e na sequencia  
```  
mvn install  
```  
o pacote "game-logs.jar" será gerado. Para subir a aplicação basta executar o comando:  
```  
java -jar target/game-logs.jar  
```  
  
## 3 - Documentação  
Após a aplicação subir, a documentação dos endpoints será gerada automaticamente pelo swagger e poderá ser consultada no seguinte endereço, conforme imagem abaixo:  
```  
http://localhost:1337/swagger-ui.html  
```  
  
<img src="https://github.com/wmasantos/game-log/blob/master/Swagger.jpg"/>  
  
## 4 - Observação  
Ao analisar a quantidade de endpoints, será percebido que nos métodos de listagem full e listagem pelo número do jogo, existem 2 versões de cada, ou seja, v1 e v2. Os métodos apresentados por V1 retornam a forma como me senti bem trabalhando no retorno dos resultados, já os métodos da V2 retornam da forma como foi solicitado no enunciado do desafio. Tomei essa liberdade de alteração devido à sugestão no **item 6**:<br>  
  
**"6. Siga o que considera boas práticas de programação, coisas que um bom desenvolvedor olhe no seu código e não ache "feio" ou "ruim"."**  
  
## 5 - Anexos adicionais 
### 5.1 - Grafana 
Uma réplica da aplicação foi lançada em um servidor na AWS utilizando o arquivo **DockerFile** que possibilitou habilitar as métricas do grafana, caso desejar verificar o painel, utilize o endereço abaixo:
```
http://metrics.ifitnessworkout.com.br:3000
```
usuário e senha serão enviados via e-mail para segurança do acesso.

**Abaixo o painel**:
<img src="https://github.com/wmasantos/game-log/blob/master/Grafana.jpg"/>  

### 5.2 - TravisCI
O projeto contou com o auxilio desta ferramenta para fazer o CI, os processos chamados por ela são: **Unit Tests**, **Integrated Tests**, **Java Code Coverage** (onde atualmente o projeto conta com 97% de cobertura de código), **SonarCloud** (Fazendo auxilio no código produzido reportando bugs, vulnerabilidades, code smell e atualizações de bibliotecas potencialmente vulneráveis). 
**Imagem**
<img src="https://github.com/wmasantos/game-log/blob/master/CI.jpg"/>  

### 5.3 - SonarCloud
Quando iniciado um processo de merge request ou pull request, sempre que o **TravisCI** roda todos os comandos, um report no sonar é gerado, tanto em sua página principal quanto na própria timeline do MR ou PR.
**Imagem**
<img src="https://github.com/wmasantos/game-log/blob/master/Sonar.jpg"/>  

### 5.4 - Sentry
Além do monitoramento de acessos obtido pelo grafana, também fora implementado uma chave opcional que possibilita o uso do sentry na aplicação, basta adicionar a variável de ambiente chamada **SENTRY_DSN** com sua chave de acesso para receber reports de erros imprevistos gerados pela API.
**Imagem**
<img src="https://github.com/wmasantos/game-log/blob/master/Sentry.jpg"/>  
  
Caso haja qualquer dúvida em qualquer parte do projeto, por favor não deixe de perguntar.
## Muito Obrigado :)
