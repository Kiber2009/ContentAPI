import io.papermc.hangarpublishplugin.model.Platforms
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Base64

plugins {
    java
    signing
    `maven-publish`
    id("xyz.jpenilla.run-paper") version "3.0.2"
    id("io.papermc.hangar-publish-plugin") version "0.1.4"
    id("com.modrinth.minotaur") version "2.+"
}

val namespace = "io.github.kiber2009"

group = "$namespace.plugin"
version = "1.0.0"

val mcVersion = "26.1.2"
val pluginId = "ContentAPI"
val author = "Kiber2009"

tasks {
    register("printVersion") {
        description = ""
        doLast {
            println(project.version)
        }
    }

    register("publishMavenCentral") {
        description = ""
        dependsOn("publishAllPublicationsToMavenCentralRepository")
        doLast {
            val path = "https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/$namespace?publishing_type=automatic"
            val auth = Base64.getEncoder().encodeToString(
                "${System.getenv("MAVEN_CENTRAL_USERNAME")}:${System.getenv("MAVEN_CENTRAL_PASSWORD")}"
                    .toByteArray()
            )
            val response = HttpClient.newHttpClient().send(
                HttpRequest.newBuilder()
                    .uri(uri(path))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .header("Authorization", "Bearer $auth")
                    .build(), HttpResponse.BodyHandlers.ofString()
            )
            assert(response.statusCode() == 200) {
                response.body()
            }
        }
    }

    jar {
        manifest {
            attributes(
                "Implementation-Title" to rootProject.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to author
            )
        }
    }

    runServer {
        minecraftVersion(mcVersion)
    }

    processResources {
        val props = mapOf(
            "version" to version,
            "mcVersion" to mcVersion,
            "pluginId" to pluginId,
            "author" to author
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
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
    toolchain.languageVersion = JavaLanguageVersion.of(file(".java-version").readText().trim())
    withJavadocJar()
    withSourcesJar()
}

signing {
    sign(publishing.publications)
    useInMemoryPgpKeys(
        System.getenv("GPG_KEY_ID"),
        System.getenv("GPG_KEY_ASCII"),
        System.getenv("GPG_KEY_PASSWORD")
    )
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
        maven {
            name = "MavenCentral"
            url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_CENTRAL_USERNAME")
                password = System.getenv("MAVEN_CENTRAL_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name = "ContentAPI"
                description = "API for adding custom content"
                url = "https://github.com/Kiber2009/ContentAPI"
                licenses {
                    license {
                        name = "MIT"
                        url = "https://raw.githubusercontent.com/Kiber2009/ContentAPI/refs/heads/main/LICENSE"
                    }
                }
                developers {
                    developer {
                        id = "Kiber2009"
                        name = "Kiber2009"
                        email = "92222946+Kiber2009@users.noreply.github.com"
                        url = "https://github.com/Kiber2009"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/Kiber2009/ContentAPI.git"
                    developerConnection = "scm:git@github.com:Kiber2009/ContentAPI.git"
                    url = "https://github.com/Kiber2009/ContentAPI"
                }
            }
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