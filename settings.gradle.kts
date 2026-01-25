pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LearnApp"
include(":app")
include(":feature:deck")
include(":feature:authorization")
include(":core:security")
include(":core:storage")
include(":core:di")
include(":core:navigation")
include(":shared:designsystem")
include(":core:network")
include(":feature:splash")
