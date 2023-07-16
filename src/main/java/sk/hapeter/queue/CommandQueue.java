package sk.hapeter.queue;

import java.util.concurrent.LinkedBlockingQueue;

public class CommandQueue {

    private final LinkedBlockingQueue<Command> commands = new LinkedBlockingQueue<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    public Command getNextCommand() {
        try {
            return commands.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
