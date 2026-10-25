package at.arowkey.dbtool.db;

/**
 * Describes one kind of relational database (PostgreSQL, MariaDB, SQLite, ...).
 * <p>
 * To support a new database: implement this interface, register it in
 * {@link DatabaseTypes}, add the JDBC driver to {@code build.gradle.kts} and
 * a matching {@code requires} line to {@code module-info.java}.
 */
public interface DatabaseType {

    /** Value of {@code db.type} in the config file, e.g. {@code "postgresql"}. */
    String key();

    /** Human-readable name, e.g. {@code "PostgreSQL"}. */
    String displayName();

    int defaultPort();

    String buildJdbcUrl(String host, int port, String database);

    // Extension point: database-specific behaviour (e.g. how to list tables or
    // quote identifiers) can be added here as new methods later.
}
