package plugin;

public interface Plugin {
    String getName();

    String getDescription();

    void initialize();

    void cleanup();
}