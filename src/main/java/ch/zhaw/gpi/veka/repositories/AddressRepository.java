package ch.zhaw.gpi.veka.repositories;

import ch.zhaw.gpi.veka.entities.AddressEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository-Klasse für Adresse-Entität, welche CRUD-Operationen auf die dahinterliegende Datenbank kapselt
 * 
 * @author scep
 */
public interface AddressRepository extends JpaRepository<AddressEntity, Long>{
    
    // Benutzerdefinierte Methode, um Adressen zu finden, bei denen alle
    // Eigenschaften von Strasse bis Ort zutreffen. Im Hintergrund wird von JPA
    // eine entsprechende Methode erstellt aus dem Namen dieser Methode, welcher
    // einer klaren Syntax folgt: findBy, dann die Attribute verbunden mit dem
    // And-Operator und dem Hinweis, dass Gross-/Kleinschreibung keine Rolle spielt
    List<AddressEntity> findByStreetAndHouseNumberAndTownAndPlzAllIgnoreCase(String street, String houseNumber, String town, int plz);
}
