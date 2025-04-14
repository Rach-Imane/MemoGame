package strategy;

import model.ShapeModel;
import java.util.List;

/**
 * Interface définissant la stratégie de génération des formes
 */
public interface ShapeGenerationStrategy {
    /**
     * Génère un ensemble de formes selon la stratégie implémentée
     * @return Liste des formes générées
     */
    List<ShapeModel> generateShapes();
    
    /**
     * Sauvegarde les formes générées
     * @param shapes Les formes à sauvegarder
     */
    void saveShapes(List<ShapeModel> shapes);
} 