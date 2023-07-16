package sk.hapeter.db;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializer {

    public static final String CREATE_TABLE_SQL = """
            CREATE TABLE susers (
                            %s serial PRIMARY KEY,
                            %s text NOT NULL,
                            %s text NOT NULL
                        );""".formatted(UserField.user_id.name(), UserField.user_guid.name(), UserField.username.name());
    private final EmbeddedPostgres pg;

    public DbInitializer() {
        try {
            pg = EmbeddedPostgres.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start db", e);
        }
    }

    public void init() {
        try (Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            s.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to init db", e);
        }
    }

    public EmbeddedPostgres getPg() {
        return pg;
    }
}
