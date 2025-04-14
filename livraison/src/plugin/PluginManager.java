package plugin;

import java.io.File;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<Plugin> plugins;
    private static PluginManager instance;

    private PluginManager() {
        plugins = new ArrayList<>();
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    public void loadPlugins(String pluginDirectory) {
        File dir = new File(pluginDirectory);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".jar"));
        if (files == null) {
            return;
        }

        for (File file : files) {
            try {
                URLClassLoader classLoader = new URLClassLoader(
                        new URL[] { file.toURI().toURL() },
                        getClass().getClassLoader());

                // Chercher les classes qui implémentent l'interface Plugin
                // Note: Cette implémentation est simplifiée, il faudrait utiliser
                // une bibliothèque comme Reflections pour une implémentation plus robuste
                Class<?> pluginClass = classLoader.loadClass("PluginImpl");
                Plugin plugin = (Plugin) pluginClass.newInstance();
                plugins.add(plugin);
                plugin.initialize();
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement du plugin: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public void unloadPlugins() {
        for (Plugin plugin : plugins) {
            try {
                plugin.cleanup();
            } catch (Exception e) {
                System.err.println("Erreur lors du déchargement du plugin: " + plugin.getName());
                e.printStackTrace();
            }
        }
        plugins.clear();
    }

    public List<Plugin> getPlugins() {
        return new ArrayList<>(plugins);
    }
}