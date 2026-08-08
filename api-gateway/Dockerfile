# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .

RUN mvn clean package -pl api-gateway -am -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/api-gateway/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]