package at.arowkey.dbtool.config;

import at.arowkey.dbtool.db.DatabaseType;

/**
 * Everything needed to open a database connection.
 */
public record ConnectionConfig(
        DatabaseType type,
        String host,
        int port,
        String database,
        String user,
        String password) {

    /** Short, password-free description, e.g. for the status bar. */
    public String describe() {
        return "%s@%s:%d/%s (%s)".formatted(user, host, port, database, type.displayName());
    }

    @Override
    public String toString() {
        return "ConnectionConfig[" + describe() + "]";
    }
}
