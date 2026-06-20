plugins {
    java
    `maven-publish`
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "io.github.kiber2009.plugin"
version = "1.0.0"

val mcVersion = "26.1.2"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${mcVersion}.build.+")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    runServer {
        minecraftVersion(mcVersion)
    }

    processResources {
        val props = mapOf("version" to version, "mcVersion" to mcVersion)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}