package sk.hapeter.queue.commands;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import sk.hapeter.db.UserField;
import sk.hapeter.queue.Command;

import java.sql.*;

public class PrintAll implements Command {

    @Override
    public void execute(EmbeddedPostgres pg) {
        System.out.println("PrintAll");
        try (Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM susers;");
            while (resultSet.next()) {
                int userId = resultSet.getInt(UserField.user_id.name());
                String userGuid = resultSet.getString(UserField.user_guid.name());
                String userName = resultSet.getString(UserField.username.name());
                System.out.printf("%s, %s, %s%n", userId, userGuid, userName);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Unable to execute print all command\n" + e.getMessage());
        }
    }
}
