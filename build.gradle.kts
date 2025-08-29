plugins {
    id("java")
    id("org.springframework.boot") version "3.2.2" // latest stable
    id("io.spring.dependency-management") version "1.1.3"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.azure:azure-identity:1.15.4")
    implementation("com.microsoft.graph:microsoft-graph:6.37.0")
    implementation("org.postgresql:postgresql:42.2.23")


}

tasks.test {
    useJUnitPlatform()
}

