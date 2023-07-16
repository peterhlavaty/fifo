package sk.hapeter.queue.commands;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import sk.hapeter.db.UserField;
import sk.hapeter.queue.Command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Add implements Command {
    private int userId;
    private String userGuid;
    private String userName;

    public Add(int userId, String userGuid, String userName) {
        this.userId = userId;
        this.userGuid = userGuid;
        this.userName = userName;
    }

    @Override
    public void execute(EmbeddedPostgres pg) {
        System.out.println("Add");
        try (Connection c = pg.getPostgresDatabase().getConnection()) {
            PreparedStatement ps = c.prepareStatement("INSERT INTO susers (%s, %s, %s) VALUES (?, ?, ?)"
                    .formatted(UserField.user_id.name(), UserField.user_guid.name(), UserField.username.name()));
            ps.setInt(1, userId);
            ps.setString(2, userGuid);
            ps.setString(3, userName);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("ERROR: Unable to execute add command\n" + e.getMessage());
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
