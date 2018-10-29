package ch.zhaw.gpi.veka.repositories;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository-Klasse für Versicherer-Entität, welche CRUD-Operationen auf die dahinterliegende Datenbank kapselt
 * 
 * @author scep
 */
public interface InsurerRepository extends JpaRepository<InsurerEntity, Long>{
    
    // Benutzerdefinierte Methode, um Versicherer über ihren Namen zu finden.
    // Im Hintergrund wird von JPA eine entsprechende Methode erstellt aus dem Namen 
    // dieser Methode, welcher einer klaren Syntax folgt: findBy, dann das Attribut
    // und der Hinweis, dass Gross-/Kleinschreibung keine Rolle spielt
    List<InsurerEntity> findByNameIgnoreCase(String name);
    
    List<InsurerEntity> findAllByOrderByName();
}
