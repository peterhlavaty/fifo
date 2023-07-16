import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sk.hapeter.db.DbInitializer;
import sk.hapeter.db.UserField;
import sk.hapeter.queue.commands.Add;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTest {

    private static EmbeddedPostgres pg;

    @BeforeAll
    static void setup() {
        DbInitializer dbInitializer = new DbInitializer();
        dbInitializer.init();
        pg = dbInitializer.getPg();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            s.executeUpdate("TRUNCATE TABLE susers");
        }
    }

    @Test
    public void testAdd() throws SQLException {
        int userId = 1;
        String userGuid = "1";
        String userName = "name";
        Add add = new Add(userId, userGuid, userName);
        assertEquals(userId, add.getUserId());
        assertEquals(userGuid, add.getUserGuid());
        assertEquals(userName, add.getUserName());

        add.execute(pg);
        try (Connection c = pg.getPostgresDatabase().getConnection()) {
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery("SELECT * FROM susers;");
            int size = 0;
            while (resultSet.next()) {
                size++;
                int userIdDb = resultSet.getInt(UserField.user_id.name());
                String userGuidDb = resultSet.getString(UserField.user_guid.name());
                String userNameDb = resultSet.getString(UserField.username.name());
                assertEquals(userId, userIdDb);
                assertEquals(userGuid, userGuidDb);
                assertEquals(userName, userNameDb);
            }
            assertEquals(1, size);
        }
    }
}
