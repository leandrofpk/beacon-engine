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

Este projeto foi movido para https://github.com/siccciber/RandBeacon
