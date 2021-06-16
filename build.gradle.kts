import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	kotlin("plugin.jpa") version "1.4.21"
	kotlin("plugin.allopen") version "1.4.0"
	kotlin("plugin.noarg") version "1.4.0"
	kotlin("kapt") version "1.4.21"
}

apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-kapt")

group = "com.trip.in.my.room"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("io.jsonwebtoken:jjwt:0.9.0")
	implementation("com.google.code.gson:gson:2.8.5")

	// https://spring.io/guides/tutorials/spring-boot-oauth2/
	implementation("org.springframework.security:spring-security-oauth2-client")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	// QueryDsl (:jpa - JPAAnnotationProcessor settings)
	implementation("com.querydsl:querydsl-jpa:4.3.1")
	kapt("com.querydsl:querydsl-apt:4.3.1:jpa")

	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.5.RELEASE")
	implementation("software.amazon.awssdk:s3:2.16.46")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// swagger
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")

	// DB
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2:1.4.200")
	runtimeOnly("mysql:mysql-connector-java:8.0.23")

	// Utils
	// 예외 발생 시 최초 방생한 조상 Exception을 식별하는 기능을 제공
	implementation("commons-io:commons-io:2.8.0")
	implementation("org.apache.commons:commons-lang3:3.9")

	// log
	implementation("ch.qos.logback:logback-classic:1.2.3")
	implementation("ch.qos.logback:logback-core:1.2.3")
	implementation("io.github.microutils:kotlin-logging:1.12.5")
}

// kotlin에서는 클래스와 프로퍼티, 함수가 기본적으로 final이므로 상속이 가능하도록 자동 open 설정
allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

// hibernate에서는 인자가 없는 생성자가 필요하므로 자동으로 기본 생성자를 생성하도록 설정
noArg {
	annotation("javax.persistence.Entity")
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
