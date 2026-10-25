package at.arowkey.dbtool.db;

public class PostgresDatabaseType implements DatabaseType {

    @Override
    public String key() {
        return "postgresql";
    }

    @Override
    public String displayName() {
        return "PostgreSQL";
    }

    @Override
    public int defaultPort() {
        return 5432;
    }

    @Override
    public String buildJdbcUrl(String host, int port, String database) {
        return "jdbc:postgresql://%s:%d/%s".formatted(host, port, database);
    }
}
