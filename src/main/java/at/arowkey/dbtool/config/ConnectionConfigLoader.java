package at.arowkey.dbtool.config;

import at.arowkey.dbtool.db.DatabaseType;
import at.arowkey.dbtool.db.DatabaseTypes;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Reads a {@link ConnectionConfig} from a properties file.
 */
public final class ConnectionConfigLoader {

    /** Relative to the working directory; {@code gradlew run} uses the project root. */
    public static final Path DEFAULT_PATH = Path.of("config", "database.properties");

    private ConnectionConfigLoader() {
    }

    public static ConnectionConfig load(Path file) throws ConfigException {
        if (!Files.exists(file)) {
            throw new ConfigException("Config file not found: " + file.toAbsolutePath()
                    + "\n\nCopy config/database.properties.example to config/database.properties"
                    + " and fill in your connection details.");
        }

        Properties props = new Properties();
        try (Reader reader = Files.newBufferedReader(file)) {
            props.load(reader);
        } catch (IOException e) {
            throw new ConfigException("Could not read config file " + file.toAbsolutePath() + ": " + e.getMessage(), e);
        }

        String typeKey = props.getProperty("db.type", "postgresql").trim();
        DatabaseType type = DatabaseTypes.byKey(typeKey)
                .orElseThrow(() -> new ConfigException("Unknown db.type '" + typeKey + "'. Supported: " + DatabaseTypes.supportedKeys()));

        String host = required(props, "db.host");
        int port = parsePort(props.getProperty("db.port", ""), type);
        String database = required(props, "db.name");
        String user = required(props, "db.user");
        String password = props.getProperty("db.password", "");

        return new ConnectionConfig(type, host, port, database, user, password);
    }

    private static String required(Properties props, String key) throws ConfigException {
        String value = props.getProperty(key, "").trim();
        if (value.isEmpty()) {
            throw new ConfigException("Missing value for '" + key + "' in the config file.");
        }
        return value;
    }

    private static int parsePort(String value, DatabaseType type) throws ConfigException {
        if (value.isBlank()) {
            return type.defaultPort();
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid value for 'db.port': '" + value + "' is not a number.");
        }
    }
}
