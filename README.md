# Projeto de Comparação de Imagens com Java Spring Boot
##
![Imagem ilustrativa](https://th.bing.com/th/id/R.1449fc3011842d6613ee814cfc1bdf26?rik=byXOiNeKerhyKQ&riu=http%3a%2f%2fimages.clipartpanda.com%2fcomparison-clipart-compare-468.jpg&ehk=Hg61tfjP33K%2fi%2f%2b%2bwV6mjUQ7z3V4TpAZLxVt1aBx9zI%3d&risl=&pid=ImgRaw&r=0)

## Descrição
Este projeto utiliza o framework Spring Boot e a linguagem Java para realizar a comparação de imagens. O objetivo é fornecer um serviço que realize a comparação de imagens em uma base de imagens e que identifique similaridade entre elas. Este serviço será útil para a realização de auditoria de imagens fornecidas como prova de execução de um serviço de reparo em equipamentos.

## Tecnologias Utilizadas
##

![image](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white)
![image](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)
![image](https://img.shields.io/badge/HTML-239120?style=for-the-badge&logo=html5&logoColor=white)	
![image](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![image](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![image](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![image](https://img.shields.io/badge/Git-E34F26?style=for-the-badge&logo=git&logoColor=white)
![image](https://img.shields.io/badge/Windows-017AD7?style=for-the-badge&logo=windows&logoColor=white)
![image](https://img.shields.io/badge/Linux-E34F26?style=for-the-badge&logo=linux&logoColor=black)

## Funcionalidades
- Receber uma imagem, armazená-la em uma base de dados e/ou sistema de arquivos.
- Receber uma imagem e compará-la com as imagens armazenadas na base de dados, retornando a imagem com maior percentual de similaridade encontrada.
- Receber duas imagens e realizar a comparação entre elas, retornando um percentual de similaridade.
- Remover da base de dados uma imagem cadastrada previamente.

## Validações
- Validar se o tipo da imagem é válido, apenas imagens JPG serão aceitas.
- Validar se a imagem não está corrompida (em desenvolvimento).

## Como executar
Para executar este projeto, siga os passos abaixo:
1. Clone o repositório.
2. Abra o projeto na sua IDE.
3. Abra o arquivo application.properties e altere o password, colocando a senha seu bando de dados MySql.
'''
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/imagesDB?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=**Password**
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
'''

## Equipe

Giovanna Campos [Github](https://github.com/GiovannaCMa)
Luis Otávio Melo [Github](https://github.com/luismeloneto)
Nahyara Batista [LinkedIn](https://www.linkedin.com/in/nahyarabs), [Github](https://github.com/nahyarabs)
Rosemary Gallindo [LinkedIn](https://www.linkedin.com/in/rosegallindo/), [Github](https://github.com/RoseGall)
Vitor Cavalcanti [LinkedIn](https://www.linkedin.com/in/engvitorcavalcanti/), [Github](https://github.com/VitorCav)

**Mentor** Tiago Chaves [LinkedIn](https://www.linkedin.com/in/txaves/)

## Video ilustrativo das funcionalidades

[![Funcionalidades]          // Title
(https://www.youtube.com/watch?v=UFehSEDBttc&feature=youtu.be "Comparação de imagem")    // Video Link