package command;

import java.util.Stack;

/**
 * Gestionnaire de commandes pour le pattern Command
 */
public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    // Vérifie si une commande peut être annulée
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    // Vérifie si une commande peut être réexécutée
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}