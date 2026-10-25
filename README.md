# arows-db-tool
 Arowkeys tool for DB administration

A JavaFX desktop tool for administering a PostgreSQL database (e.g. on a Raspberry Pi
in the local network). It is built so that other relational databases can be added later.

## Requirements

- Java 25. Both Gradle itself (`gradle/gradle-daemon-jvm.properties`) and the app
  (`build.gradle.kts`) use Java 25. Gradle picks an installed JDK 25 or downloads one
  automatically, so it does not matter which Java `JAVA_HOME` points to.

## Configure the database connection

1. Copy `config/database.properties.example` to `config/database.properties`.
2. Fill in host, port, database name, user, and password.

`config/database.properties` is gitignored, so your credentials stay local. The password is
stored in plain text, so keep the file private.

The app reads this file on startup (relative to the working directory, which is the project
root when started with Gradle). If the file is missing or the connection fails, the app shows
an error dialog and the status bar shows "Not connected". Use **File > Reconnect** after
fixing the config.

## Run

```powershell
.\gradlew.bat run      # Windows
./gradlew run          # Linux/macOS
```

Build (compile and package) with `.\gradlew.bat build`.

## Project layout

| Path | Purpose |
|------|---------|
| `src/main/java/at/arowkey/dbtool/App.java` | JavaFX entry point |
| `.../config/` | Loading the connection config file |
| `.../db/` | `DatabaseType` abstraction, PostgreSQL implementation, `ConnectionManager` |
| `.../ui/` + `src/main/resources/at/arowkey/dbtool/ui/` | Main window (FXML, controller, CSS) |

### Adding another database

Implement `DatabaseType`, register it in `DatabaseTypes`, add its JDBC driver to
`build.gradle.kts`, and add a `requires` line for the driver to `module-info.java`.
