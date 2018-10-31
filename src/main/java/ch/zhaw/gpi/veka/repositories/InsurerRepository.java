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

    /**
     * Methode, welche alle Versicherer findet, welche im Namen einen übergebenen String enthalten
     * 
     * @param name  Der Name oder einen Teil davon eines Versicherers
     * @return      Liste der gefundenen Versicherer
     */
    List<InsurerEntity> findByNameIgnoreCaseContaining(String name);
    
    /**
     * Methode, welche alle Versicherer aufsteigend nach Name sortiert zurückgibt
     * @return
     */
    List<InsurerEntity> findAllByOrderByName();
}
