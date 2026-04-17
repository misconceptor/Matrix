plugins {
    java
    application
}

application {
    mainClass.set("Main")
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
