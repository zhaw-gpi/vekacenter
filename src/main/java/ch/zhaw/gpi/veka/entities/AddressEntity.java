package ch.zhaw.gpi.veka.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity-Klasse für Adressen
 * 
 * @Entity, um die Klasse als JPA Entität zu kennzeichnen, damit automatisch
 * im Hintergrund über Hibernate eine Tabelle in der H2-Datenbank erstellt
 * und verwaltet werden kann
 * 
 * @author scep
 */
@Entity (name = "Address")
public class AddressEntity implements Serializable {
    // @Id, um JPA mitzuteilen, dass dieses Attribut der Primärschlüssel ist
    @Id
    // und dabei automatisch generiert werden soll (+1 für jeden neuen Eintrag)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String street;
    private String houseNumber;
    private int plz;
    private String town;
    
    public String getStreet() {
        return street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public AddressEntity setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public int getPlz() {
        return plz;
    }

    public AddressEntity setPlz(int plz) {
        this.plz = plz;
        return this;
    }

    public String getTown() {
        return town;
    }

    public AddressEntity setTown(String town) {
        this.town = town;
        return this;
    }
    
    
}
