include(":app")

include(":mylibrary")
project(":mylibrary").projectDir = File(rootProject.projectDir, "../mylibrary")

rootProject.name = "App"
