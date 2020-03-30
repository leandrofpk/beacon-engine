# Inmetro Randomness Beacon

<!--Inmetro Beacon Engine é uma implementação do protocolo [NISTIR 8213]() do NIST.  Este projeto faz parte do Programa 
de [Mestrado em Tecnologia e Qualidade](http://www.inmetro.gov.br/ensino_e_pesquisa/mpmq/index.asp) do [Inmetro](https://www4.inmetro.gov.br/).
  O serviço pode ser encontrado aqui: https://beacon.inmetro.gov.br/
-->

O beacon do inmetro é uma implementação do protocolo NISTIR 8213 
[(KELSEY et al., 2019)](https://csrc.nist.gov/projects/interoperable-randomness-beacons).  <!-- com algumas particularidades.--> 
A poposta deste  trabalho é uma arquitetura flexível para uso interno no [Inmetro](https://www4.inmetro.gov.br/), mas que também possa ser adotada por outros laboratórios. O
objetivo é propor um processo conceitual onde cada etapa tem suas responsabilidades bem definidas onde uma configuração inicial padrão é fornecida com custo zero de configuração,
mas que permita pontos de configuração e pontos de extensão que serão detalhados mais a frente.

## Definição de Beacon

De maneira resumida, o modelo de operação do protocolo NISTR funciona da segunde forma. Primeiro, a cada minuto, 512 bits de entropia são gerados e um registro
único de tempo é adicionado. Segundo, todos os números recebidos no mesmo timestamp são combinados (um hash de todos os valores concatenados) e os demais campos do
protocolo são calculados para formar uma cadeia. Terceiro, o novo pulso é assinado e armazenado no banco de dados. Neste momento o pulso já se torna disponível para
utilização.  

Segundo [(BONNEAU; CLARK; GOLDFEDER, 2015;](https://eprint.iacr.org/2015/1015)[CopenhagenInterpretation)](http://www.copenhagen-interpretation.com/home/cryptography/cryptographic-beacons), beacons devem atender as seguintes características:

* **Imprevisível:** Nenhum adversário pode ser capaz de prever qualquer informação sobre o número até que ele se torne público;
* **Imparcial:** Um pulso deve ser estatisticamente próximo a uma sequência aleatória uniforme;	
*	**Amostragem universal:** após a publicação, qualquer parte tem acesso irrestrito a ele;
*	**Universalmente verificável:** Pode ser verificável por qualquer usuário após a publicação.

## Modules

O inmetro beacon é composto por vários múdulos, são eles:

 * [Beacon Input](https://github.com/leandrofpk/beacon-input): Processo de aquisição da entropia;
 * [Beacon Engine](https://github.com/leandrofpk/beacon-engine): Implementação do protocolo NISTIR. Os novos pulsos são gerados neste processo;
 * [Beacon Interface](https://github.com/leandrofpk/beacon-interface): Responsável pela publicação externa de todos os pulsos gerados;   
 * [Beacon libs](https://github.com/leandrofpk/beacon-libs): Bibliotecas são compartilhadas entre os projetos.

<!--
## Beacon Engine
Uma descrição...
-->


## Built With

* Java 8+ - Programming language
* [Spring](https://spring.io/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* Mysql
* [RabbitMQ](https://www.rabbitmq.com/): Fila de mensagens utilizadas para integrar os módulos

## Installation and Getting Started

A maneira mais fácil de executar a solução é executar via docker container.

```
 1. git clone https://github.com/leandrofpk/beacon-engine.git
 2. cd beacon-engine
 3. docker-compose up
 4. Apontar o navegador web para http://localhost:8080
```

Todas as imagens docker podem ser encontadas aqui: https://hub.docker.com/u/lpcorrea

## Building from Source

1. Criar uma pasta para o projeto (Ex.: beacon) e clonar os repositórios dentro da nova pasta(input, engine, interface e libs). 
Ao final, as pastas devem ter o seguinte formato:
```
beacon
  ├── beacon-engine
  ├── beacon-input
  ├── beacon-interface
  ├── beacon-libs
```

2. Instalar as libs compartilhadas

```
cd beacon\beacon-libs
mvn clean install
```

3. Executar ou compilar os projetos

````
cd /beacon/<projeto>/
Executar: mvn spring-boot:run
Compilar: nvn clean package -DskipTests
````

4 Criar dois bancos de dados no mysql:
```
Bancos: beacon-input e beacon2
Usuario: root e senha 123456
````

5. Instalar a fila de mensagens RabbitMq e importar as configurações da 
rquivo [definitions.json](https://github.com/leandrofpk/beacon-engine/blob/master/docker-files/definitions.json). A 
interface de administração possui uma funcionalidade para importação.

<!--https://gist.github.com/lucianfialhobp/14326023cb7f661eaf80 -->

## Projetos relacionados

 * [Beacon Input](https://github.com/leandrofpk/beacon-input)
 * [Beacon Engine](https://github.com/leandrofpk/beacon-engine)
 * [Beacon Interface](https://github.com/leandrofpk/beacon-interface) 
 * [Beacon libs](https://github.com/leandrofpk/beacon-libs)

## Arquitetura da solução

![Arquitetura Randomness Beacon](https://github.com/leandrofpk/beacon-engine/blob/master/docs/c4-beacon-conteiner-v1.png)

