package ch.zhaw.gpi.veka.repositories;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD-Repository-Klasse für InsurerEntity-Objekte
 * 
 * Erweitert die Standard Spring-CrudRepository
 * 
 * @author scep
 */
public interface InsurerRepository extends CrudRepository<InsurerEntity, Long>{
    
    // Benutzerdefinierte Methode, um Versicherer über ihren Namen zu finden.
    // Im Hintergrund wird von JPA eine entsprechende Methode erstellt aus dem Namen 
    // dieser Methode, welcher einer klaren Syntax folgt: findBy, dann das Attribut
    // und der Hinweis, dass Gross-/Kleinschreibung keine Rolle spielt
    List<InsurerEntity> findByNameIgnoreCase(String name);
    
    List<InsurerEntity> findAllByOrderByName();
}
