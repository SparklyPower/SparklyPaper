pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "sparklypaper"

include("sparklypaper-api", "sparklypaper-server")
