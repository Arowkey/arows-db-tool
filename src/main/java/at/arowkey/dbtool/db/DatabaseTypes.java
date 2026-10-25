package at.arowkey.dbtool.db;

import java.util.List;
import java.util.Optional;

/**
 * Registry of all supported database types.
 */
public final class DatabaseTypes {

    private static final List<DatabaseType> ALL = List.of(
            new PostgresDatabaseType()
    );

    private DatabaseTypes() {
    }

    public static Optional<DatabaseType> byKey(String key) {
        return ALL.stream()
                .filter(type -> type.key().equalsIgnoreCase(key))
                .findFirst();
    }

    public static List<String> supportedKeys() {
        return ALL.stream().map(DatabaseType::key).toList();
    }
}
