# e.solutions Workshop 2021-02-03

## History

### Allgemein
- Initiales Android-Projekt [commit](https://github.com/inserteffect/e.solutions-workshop/commit/29ffb33d0ec93bdb049441f56b2e7a82218cb682)
- *.gradle -> *.gradle.kts [commit](https://github.com/inserteffect/e.solutions-workshop/commit/96f037f3c67dede99408f4dba5a0126e85943a59)
- Tasks [commit](https://github.com/inserteffect/e.solutions-workshop/commit/20ea8ff53a8e018cb2ae02f4382fbd5ac8f176fc)
- Task-Abhängigkeiten [commit](https://github.com/inserteffect/e.solutions-workshop/commit/25ba8b0091cf42ee0e076e327011dae4c436ea40)
- Plugin [commit](https://github.com/inserteffect/e.solutions-workshop/commit/7bf5ca8b12c4cf7318f5d2e9c7c9d6495e8f9e15)
- Plugin-Konfiguration [commit](https://github.com/inserteffect/e.solutions-workshop/commit/2edb126fba99f212d1fc8f90c2b2c7b674c0da6a)
- DSL für Plugin-Konfiguration [commit](https://github.com/inserteffect/e.solutions-workshop/commit/bc5ddc24830d51dcc9207561ff3c61addfae2acb)
- Auslagerung der Tasks in eigene Datei [commit](https://github.com/inserteffect/e.solutions-workshop/commit/6e5bef7afac35919fd7cc444224c552b5c3c4171)
- Submodule/Subproject [commit](https://github.com/inserteffect/e.solutions-workshop/commit/bcae18a17e79f7b3bcc4302be5eb2c8579a468c3)
- Submodule/Subproject außerhalb des Projekts [commit](https://github.com/inserteffect/e.solutions-workshop/commit/3820290763524b676e16ff60f61af915b60fa826)
- gradle.properties [commit](https://github.com/inserteffect/e.solutions-workshop/commit/353f67445602a79bed9851040c5dcf557f1bb17e)

### Android
- BuildConfig-Feld [commit](https://github.com/inserteffect/e.solutions-workshop/commit/bc7d72c3ec42bd19392bdf26d756a4e778b3ecba)
- Keystore-Script [commit](https://github.com/inserteffect/e.solutions-workshop/commit/3ce2717b606bdae337a9fa056c6e41e9f8b2019f)
- SigningConfig [commit](https://github.com/inserteffect/e.solutions-workshop/commit/d5061a95a39846062977ead1aea02530c7343d96)

## Gradle

### Lifecycle

#### Initialization
In dieser Phase wird geschaut, welches der Projekte für das Build einbezogen werden muss und es wird eine `Project`-Instanz für jedes dieser Projekte erstellt.

#### Configuration
Es werden alle, vorher erstellten, `Project`-Objekte konfiguriert und alle Build-Scripte der beteiligten Projekte ausgeführt.

#### Execution
Anhand des auszuführenden Tasks, via `gradle(w) TaskName`, werden die benötigten Tasks ermittelt, welche in der vorherigen Phase konfiguriert wurden und in Abhängigkeit zu dem auszuführenden Task stehen. \
Diese Tasks werden jetzt in der ermittelten Reihenfolge ausgeführt.

### settings.gradle(.kts)
Diese Datei wird während der __Initialization__-Phase ausgeführt und beschreibt, welche Projekte in einem `Multi-Project-Build` vorhanden sind.

Beispiel:

`settings.gradle(.kts)`
```
rootProject.name = "basic"
println("This is executed during the initialization phase.")
```

`build.gradle(.kts)`
```
println("This is executed during the configuration phase.")

tasks.register("configured") {
    println("This is also executed during the configuration phase.")
}

tasks.register("test") {
    doLast {
        println("This is executed during the execution phase.")
    }
}

tasks.register("testBoth") {
    doFirst {
        println("This is executed first during the execution phase.")
    }
    doLast {
        println("This is executed last during the execution phase.")
    }
    println("This is executed during the configuration phase as well.")
}
```

`$ gradle test testBoth`
```
This is executed during the initialization phase.

> Configure project :
This is executed during the configuration phase.
This is executed during the configuration phase as well.

> Task :test
This is executed during the execution phase.

> Task :testBoth
This is executed first during the execution phase.
This is executed last during the execution phase.

BUILD SUCCESSFUL in 0s
2 actionable tasks: 2 executed
```

Wenn `gradle(w)` in einem `Subproject` ausgeführt wird (`project/sub`), wird nach einer `settings.gradle(.kts)` in diesem Verzeichnis gesucht. \
Falls keine gefunden wird, wird in der Verzeichnishierarchie eine Ebene höher gesucht, bis zum Root (`project/`). \
Wird keine `settings.gradle(.kts)` gefunden, wird das `Subproject` als `Single-Project-Build` ausgeführt.

Wenn das zu bauende Projekt eine `settings.gradle(.kts)` enthält, dann wird das Projekt ausgeführt als:

`Single-Project`: Wenn nur ein `include(":name")` existiert. \
`Multi-Project`: Wenn mehrere `include(":name")`; `include(":name2")`; etc. existieren.

Jedes Projekt kann wiederum `Subprojects` haben (`project/sub/child`).

Analog wird für `project/sub/child`, nach einer `settings.gradle(.kts)`, in der beschriebenen Weise gesucht, falls `gradle(w)` in diesem Projekt ausgeführt wird.

`Multi-Project` heißt in diesem Fall, dass die Abhängigkeit zu anderen Projekten ermittelt und wenn gegeben, diese ebenso gebaut werden.

Zusammengefasst von: [Build Lifecycle (Gradle User Manual)](https://docs.gradle.org/current/userguide/build_lifecycle.html)
