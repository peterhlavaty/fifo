package sk.hapeter;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import sk.hapeter.db.DbInitializer;
import sk.hapeter.queue.CommandExecutor;
import sk.hapeter.queue.CommandQueue;
import sk.hapeter.queue.commands.Add;
import sk.hapeter.queue.commands.DeleteAll;
import sk.hapeter.queue.commands.PrintAll;

public class Main {
    public static void main(String[] args) {
        //init db
        DbInitializer dbInitializer = new DbInitializer();
        dbInitializer.init();
        EmbeddedPostgres pg = dbInitializer.getPg();

        //init queue
        CommandQueue commandQueue = new CommandQueue();

        //init executor
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.start(commandQueue, pg);

        //usage test
        commandQueue.addCommand(new Add(1, "a1", "Robert"));
        commandQueue.addCommand(new Add(2, "a2", "Martin"));
        commandQueue.addCommand(new PrintAll());
        commandQueue.addCommand(new DeleteAll());
        commandQueue.addCommand(new PrintAll());
    }
}