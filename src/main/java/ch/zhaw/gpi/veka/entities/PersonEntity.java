package ch.zhaw.gpi.veka.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity-Klasse für (versicherte) Person
 * 
 * @Entity, um die Klasse als JPA Entität zu kennzeichnen, damit automatisch
 * im Hintergrund über Hibernate eine Tabelle in der H2-Datenbank erstellt
 * und verwaltet werden kann
 * 
 * @author scep
 */
@Entity(name = "Person")
public class PersonEntity implements Serializable {
    // @Id, um JPA mitzuteilen, dass dieses Attribut der Primärschlüssel ist
    @Id
    // und dabei automatisch generiert werden soll (+1 für jeden neuen Eintrag)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String officialName;
    private Integer sex;
    private Date dateOfBirth;
    private Long vn;
    
    // 1:1-Beziehung herstellen mit einer Adress-Entität
    @OneToOne(targetEntity = AddressEntity.class)
    private AddressEntity addressPostal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getVn() {
        return vn;
    }

    public void setVn(Long vn) {
        this.vn = vn;
    }

    public AddressEntity getAddressPostal() {
        return addressPostal;
    }

    public void setAddressPostal(AddressEntity addressPostal) {
        this.addressPostal = addressPostal;
    }
    
    
}
