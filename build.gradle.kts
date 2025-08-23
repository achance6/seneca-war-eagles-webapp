plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.spring") version "2.2.10"
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.seneca.wareagles"
version = "0.0.1-SNAPSHOT"
description = "seneca-war-eagles-webapp"

val mockitoAgent: Configuration by configurations.creating

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.wimdeblauwe:htmx-spring-boot:4.0.1")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Resolves JVM warning concerning dynamically attaching agents
    mockitoAgent("org.mockito:mockito-core:5.18.0") {
        isTransitive = false
    }
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion)
        vendor = JvmVendorSpec.AMAZON
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}

@Suppress("UnstableApiUsage")
testing.suites {
    withType<JvmTestSuite> {
        dependencies {
            implementation(project())
        }
        useJUnitJupiter()
    }
    val test by getting(JvmTestSuite::class)

    val integrationTest by registering(JvmTestSuite::class) {
        targets {
            all {
                testTask {
                    shouldRunAfter(test)
                }
            }
        }
    }

    tasks.check {
        dependsOn(
            test,
            integrationTest
        )
    }
}

configurations.named("integrationTestImplementation") {
    extendsFrom(
        configurations.implementation.get(),
        configurations.testImplementation.get()
    )
}
