package command;

/**
 * Interface définissant le pattern Command
 */
public interface Command {

    void execute();

    void undo();
}