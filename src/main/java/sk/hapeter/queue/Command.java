package sk.hapeter.queue;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

public interface Command {
    void execute(EmbeddedPostgres pg);
}
