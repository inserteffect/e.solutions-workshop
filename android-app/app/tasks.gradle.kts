val taskA = tasks.register<Copy>("taskA") {
    description = "Copy Stuff"
    from("../a")
    into("../b")
    include("**/*")

    println("taskA")
}

val taskB = tasks.register<Delete>("taskB") {
    description = "Delete Stuff"
    delete("../b")

    doFirst {
        println("taskB before running")
    }
    doLast {
        println("taskB after running")
    }

    println("taskB during configuration")
}

val taskC = tasks.register("taskC", Delete::class) {
    description = "Detete Stuff"
    delete("../b")

    println("taskC")
}

val taskE by tasks.registering(Delete::class) {
    description = "Delete stuff"
    delete("../b")

    println("taskE")
}

val printCredentials = tasks.register("printCredentials") {
    if (project.hasProperty("username")) {
        println("username: ${project.property("username")}")
    }
    if (project.hasProperty("password")) {
        println("password: ${project.property("password")}")
    }

    println("android.useAndroidX: ${project.property("android.useAndroidX")}")
}
