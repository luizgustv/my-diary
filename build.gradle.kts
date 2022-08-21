import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.ir.backend.js.compile

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

	//coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
	runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.2")
	//postgresql
	implementation("org.postgresql:postgresql:42.4.1")

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
