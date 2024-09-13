plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.9.0"
    id("io.freefair.lombok") version "6.6.1"
    id("org.zeroturnaround.gradle.jrebel") version "1.2.0" apply false
}

allprojects {
    apply (plugin = "org.zeroturnaround.gradle.jrebel")
}

dependencies {
    implementation("cn.hutool:hutool-all:5.8.11")
}

group = "com.nancheung.plugins.jetbrains"
version = "1.4.4"

repositories {
    mavenCentral()
    gradlePluginPortal()
}


// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2020.1")
    type.set("IC") // Target IDE Platform
    updateSinceUntilBuild.set(false)

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
        options.encoding = "UTF-8"
    }

    processResources{
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    patchPluginXml {
        sinceBuild.set("201")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    runIde {
        // 如果想要使用jrebel热部署，这里jrebel64.dll的路径需要修改为自己电脑上的jrebel64.dll的路径
        // 不用jrebel热部署修改为 jvmArgs = listOf() 即可
//        jvmArgs = listOf("-agentpath:C:\\Users\\Administrator\\AppData\\Roaming\\JetBrains\\IntelliJIdea2021.2\\plugins\\jr-ide-idea\\lib\\jrebel6\\lib\\jrebel64.dll")
        jvmArgs = listOf()
    }

}
