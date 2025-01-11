# Usar a imagem do OpenJDK
FROM openjdk:21-jdk-slim

# Criar o diretório da aplicação
RUN mkdir /app

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo .jar gerado pelo Maven para o contêiner
COPY devJavaSpringSenior/target/*.jar /app/app.jar

# Comando para executar a aplicação Java
CMD ["java", "-jar", "/app/app.jar"]
