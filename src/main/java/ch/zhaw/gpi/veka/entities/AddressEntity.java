package ch.zhaw.gpi.veka.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

/**
 * Entity-Klasse für Adressen
 * 
 * @author scep und Markus Cristelotti, Angela Keller, Tim Schrödel, Dominik Straub, Matthias Urech und Philipp Winter (Equals-Methode für Adress-Entity)

 */
@Entity (name = "Address")
public class AddressEntity implements Serializable {
    // Automatisch generierte Adress-Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Strasse
    @NotNull
    private String street;
    
    // Hausnummer
    private String houseNumber;
    
    // PLZ
    @NotNull
    @Range(min = 1, max = 9999)
    private int plz;
    
    // Stadt/Ort
    @NotNull
    private String town;
    
    // GETTER und SETTER
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
    
    /**
     * Überprüfen ob ein Objekt gleich ist wie diese Adresse
     * (vergleichen)
     *
     * @param      obj   Eine beliebige Adresse (kann auch ein beliebiges Objekt
     *                   sein. in dem Fall sind sie natürlich nicht gleich)
     *
     * @return     wahr/falsch ob beide Objekte gleich sind
     */
    @Override
    public boolean equals(Object obj) {
        // Haben beide Objekte die gleiche Referenz
        if (this == obj) {
            return true;
        }
        
        // ist das zu vergleichebare Objekt null
        if (obj == null) {
            return false;
        }
        
        // sind beide Objekte nicht von der gleichen Klasse
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        // vergleichbare Objekt in Typ Adresse machen
        final AddressEntity other = (AddressEntity) obj;
        
        // PLZ vergleichen
        if (this.plz != other.plz) {
            return false;
        }
        
        // Strasse vergleichen
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        
        // Hausnummer vergleichen
        if (!Objects.equals(this.houseNumber, other.houseNumber)) {
            return false;
        }
        
        // Ort vergleichen
        if (!Objects.equals(this.town, other.town)) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.street, this.plz, this.houseNumber);
    }
}
