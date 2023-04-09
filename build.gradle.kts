plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.github.tywinlanni"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.7")
    implementation("com.aallam.openai:openai-client:3.2.0")
    implementation ("com.aallam.openai:openai-client")
    implementation ("io.ktor:ktor-client-okhttp:2.2.4")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("com.github.tywinlanni.telegramOpenAiBot.MainKt")
}
