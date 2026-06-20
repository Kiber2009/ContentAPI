import io.papermc.hangarpublishplugin.model.Platforms

plugins {
    java
    `maven-publish`
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("io.papermc.hangar-publish-plugin") version "0.1.4"
    id("com.modrinth.minotaur") version "2.+"
}

group = "io.github.kiber2009.plugin"
version = "1.0.0"

val mcVersion = "26.1.2"
val pluginId = "ContentAPI"

tasks.register("printVersion") {
    description = ""
    doLast {
        println(project.version)
    }
}

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
    toolchain.languageVersion.set(JavaLanguageVersion.of(file(".java-version").readText().trim()))
}

tasks {
    runServer {
        minecraftVersion(mcVersion)
    }

    processResources {
        val props = mapOf(
            "version" to version,
            "mcVersion" to mcVersion,
            "pluginId" to pluginId
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Kiber2009/ContentAPI")
            credentials {
                username = System.getenv("GITHUB_PACKAGES_USERNAME")
                password = System.getenv("GITHUB_PACKAGES_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

val readme = project.file("README.md").readText()

hangarPublish {
    publications.register("plugin") {
        version = project.version as String
        channel = "Release"
        id = pluginId
        apiKey = System.getenv("HANGAR_API_TOKEN")
        pages.resourcePage(readme)
        platforms {
            register(Platforms.PAPER) {
                jar = tasks.jar.flatMap { it.archiveFile }
                platformVersions = listOf(mcVersion)
            }
        }
    }
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    projectId = pluginId.lowercase()
    versionType = "release"
    gameVersions = listOf(mcVersion)
    loaders = listOf("paper")
    uploadFile.set(tasks.jar)
    syncBodyFrom = readme
}