package sk.hapeter.queue.commands;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import sk.hapeter.queue.Command;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteAll implements Command {

    @Override
    public void execute(EmbeddedPostgres pg) {
        System.out.println("DeleteAll");
        try (Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            s.executeUpdate("DELETE FROM susers");
        } catch (SQLException e) {
            System.err.println("ERROR: Unable to execute delete all command\n" + e.getMessage());
        }
    }
}
