package ch.zhaw.gpi.veka.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity-Klasse für Versicherer
 * 
 * @Entity, um die Klasse als JPA Entität zu kennzeichnen, damit automatisch
 * im Hintergrund über Hibernate eine Tabelle in der H2-Datenbank erstellt
 * und verwaltet werden kann
 * 
 * @author scep
 */
@Entity(name = "Insurer")
public class InsurerEntity implements Serializable {
    // @Id, um JPA mitzuteilen, dass dieses Attribut der Primärschlüssel ist
    @Id
    // und dabei automatisch generiert werden soll (+1 für jeden neuen Eintrag)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    // Unidirektionale 1:1-Beziehung herstellen mit einer Adressen-Entität
    @OneToOne(targetEntity = AddressEntity.class)
    private AddressEntity address;

    public String getName() {
        return name;
    }

    public InsurerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public InsurerEntity setAddress(AddressEntity address) {
        this.address = address;
        return this;
    }
    
    
}
