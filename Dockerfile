# =============================================================================
# DOCKERFILE ДЛЯ ORDERHUB - PRODUCTION BUILD (Gradle Version)
# =============================================================================
# Многоступенчатая сборка (multi-stage build):
#   Этап 1 (builder): собирает приложение с Gradle и JDK
#   Этап 2 (final): только JRE и собранный jar
# =============================================================================

# -----------------------------------------------------------------------------
# ЭТАП 1: СБОРКА (BUILDER)
# -----------------------------------------------------------------------------
FROM gradle:9.4.1-jdk25-alpine AS builder

ARG APP_VERSION
ARG BUILD_DATE

LABEL version=${APP_VERSION}
LABEL build-date=${BUILD_DATE}
LABEL maintainer="Евгений Сапруно"

WORKDIR /app

COPY build.gradle.kts ./
COPY settings.gradle.kts ./
COPY gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon

COPY src ./src

RUN ./gradlew bootJar -x test --no-daemon

# -----------------------------------------------------------------------------
# ЭТАП 2: ФИНАЛЬНЫЙ ОБРАЗ (PRODUCTION)
# -----------------------------------------------------------------------------
FROM eclipse-temurin:25-jre-alpine

ARG APP_VERSION
ARG BUILD_DATE

LABEL version=${APP_VERSION}
LABEL build-date=${BUILD_DATE}
LABEL description="OrderHub Production Application (Gradle Build)"
LABEL org.opencontainers.image.source="https://github.com/Oleborn/OrderHub"

WORKDIR /app

RUN apk add --no-cache curl

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

USER spring:spring

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]