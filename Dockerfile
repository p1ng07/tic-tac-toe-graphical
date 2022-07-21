FROM gradle:6.9.2-jdk11
COPY . .
CMD ["java", "-jar", "desktop/build/libs/desktop-1.0.jar"]
