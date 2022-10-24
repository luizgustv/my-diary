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

    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")

//    //spring cloud starter sleuth - autoconfiguration for distributed tracing
//    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
//    {
//        exclude(group = "org.springframework.cloud", module = "spring-cloud-sleuth-brave")
//    }
//    //for autoconfigure metrics
//    implementation("org.springframework.cloud:spring-cloud-sleuth-otel-autoconfigure")
//
//    //for export metric to somewhere
//    implementation("io.opentelemetry:opentelemetry-exporter-otlp-trace")
//    //context extension for coroutines
//    runtimeOnly("io.opentelemetry:opentelemetry-extension-kotlin")

    /*
    https://github.com/spring-projects-experimental/spring-cloud-sleuth-otel

    https://towardsdev.com/opentelemetry-spring-boot-kafka-and-jaeger-in-action-8ef1912c8044

    https://betterprogramming.pub/distributed-tracing-with-opentelemetry-spring-cloud-sleuth-kafka-and-jaeger-939e35f45821

    https://github.com/spring-projects-experimental/spring-cloud-sleuth-otel

    https://github.com/spring-cloud/spring-cloud-sleuth/issues/1908
     */

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

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
