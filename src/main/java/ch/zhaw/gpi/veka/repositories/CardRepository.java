package ch.zhaw.gpi.veka.repositories;

import ch.zhaw.gpi.veka.entities.CardEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * CRUD-Repository-Klasse für CardEntity-Objekte
 * 
 * Erweitert die Standard Spring-CrudRepository, wobei keine Erweiterungen erforderlich sind
 * 
 * @author scep
 */
public interface CardRepository extends CrudRepository<CardEntity, Long>{
    
}
