module at.arowkey.dbtool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // The JDBC driver is loaded at runtime via DriverManager, but it must be part of
    // the module graph. Add a "requires" line here for every new database driver.
    requires org.postgresql.jdbc;

    // FXML needs reflective access to the controller's @FXML fields and methods.
    opens at.arowkey.dbtool.ui to javafx.fxml;

    exports at.arowkey.dbtool;
}
