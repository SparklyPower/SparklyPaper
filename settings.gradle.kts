pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "SparklyPaper"

include("SparklyPaper-API", "SparklyPaper-Server")