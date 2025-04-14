package view.factory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LookAndFeelPlugin {
    private static final Map<String, UIFactory> factories = new HashMap<>();
    private static UIFactory currentFactory;

    static {
        // Enregistrer les factories disponibles
        registerFactory("Nimbus", new NimbusUIFactory());
        registerFactory("Metal", new MetalUIFactory());
        registerFactory("Dark", new DarkUIFactory());
        // On peut ajouter d'autres factories ici plus tard
    }

    public static void registerFactory(String name, UIFactory factory) {
        factories.put(name, factory);
    }

    public static void setCurrentFactory(String name) {
        UIFactory factory = factories.get(name);
        if (factory != null) {
            currentFactory = factory;
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if (name.equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du changement de Look & Feel: " + e.getMessage());
            }
        }
    }

    public static UIFactory getCurrentFactory() {
        if (currentFactory == null) {
            currentFactory = factories.get("Nimbus"); // Factory par défaut
        }
        return currentFactory;
    }

    public static String[] getAvailableLookAndFeels() {
        return factories.keySet().toArray(new String[0]);
    }
}