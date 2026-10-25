package at.arowkey.dbtool.config;

/**
 * Thrown when the connection config file is missing or invalid.
 */
public class ConfigException extends Exception {

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
