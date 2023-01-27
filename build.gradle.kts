import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "br.com.katet"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }

    //undertow - it's like a tomcat
    implementation("org.springframework.boot:spring-boot-starter-undertow")

    //actuator - enables endpoints that shows more information
    //for more : https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/#overview
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //prometheus
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //logback
    implementation("net.logstash.logback:logstash-logback-encoder:5.2")

    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.4")

    //postgresql
    implementation("org.postgresql:postgresql:42.5.0")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
