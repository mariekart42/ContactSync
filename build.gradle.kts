plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.azure:azure-identity:1.15.4")
    implementation("com.microsoft.graph:microsoft-graph:6.37.0")
}

tasks.test {
    useJUnitPlatform()
}

