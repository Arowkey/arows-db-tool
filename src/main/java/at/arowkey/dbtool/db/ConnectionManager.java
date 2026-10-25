package at.arowkey.dbtool.db;

import at.arowkey.dbtool.config.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Owns the single JDBC connection of the application.
 */
public class ConnectionManager implements AutoCloseable {

    private Connection connection;

    /** Opens a new connection, closing any existing one first. */
    public synchronized void connect(ConnectionConfig config) throws SQLException {
        close();

        String url = config.type().buildJdbcUrl(config.host(), config.port(), config.database());
        Properties props = new Properties();
        props.setProperty("user", config.user());
        props.setProperty("password", config.password());

        connection = DriverManager.getConnection(url, props);
    }

    public synchronized boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /** The open connection; use it for queries (later: table and row services). */
    public synchronized Connection getConnection() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected to a database.");
        }
        return connection;
    }

    @Override
    public synchronized void close() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error while closing the connection: " + e.getMessage());
        } finally {
            connection = null;
        }
    }
}
