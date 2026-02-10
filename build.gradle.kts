plugins {
	id("java")
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "ups.edu.ec"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly("org.postgresql:postgresql")


	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	
	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	
	// JWT - JSON Web Token
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
	
	// Jackson para manejo de fechas Java 8+ (LocalDateTime, LocalDate, etc.)
	// NECESARIO: ErrorResponse usa LocalDateTime que requiere este módulo
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	
	// Spring Boot Mail - Para envío de correos electrónicos
	implementation("org.springframework.boot:spring-boot-starter-mail")

}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
