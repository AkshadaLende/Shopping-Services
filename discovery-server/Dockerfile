# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package -pl discovery-server -am -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/discovery-server/target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]