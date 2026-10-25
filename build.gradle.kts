plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "at.arowkey"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

javafx {
    version = "25.0.5"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.14")
}

application {
    mainModule = "at.arowkey.dbtool"
    mainClass = "at.arowkey.dbtool.App"
    // JavaFX loads native libraries; without this flag Java 25 prints a warning on startup.
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
