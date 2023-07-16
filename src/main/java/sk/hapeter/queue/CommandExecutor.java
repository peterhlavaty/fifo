package sk.hapeter.queue;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandExecutor {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void start(CommandQueue commandQueue, EmbeddedPostgres ep) {
        executorService.submit(() -> executeCommands(commandQueue, ep));
    }

    private void executeCommands(CommandQueue commandQueue, EmbeddedPostgres ep) {
        while (true) {
            commandQueue.getNextCommand().execute(ep);
        }
    }
}
