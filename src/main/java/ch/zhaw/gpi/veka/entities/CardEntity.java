package ch.zhaw.gpi.veka.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity-Klasse für Versichertenkarte
 * 
 * Attribute angelehnt an https://de.wikipedia.org/wiki/Versichertenkarte_(Schweiz)
 * 
 * @Entity, um die Klasse als JPA Entität zu kennzeichnen, damit automatisch
 * im Hintergrund über Hibernate eine Tabelle in der H2-Datenbank erstellt
 * und verwaltet werden kann
 * 
 * @author scep
 */
@Entity(name = "Card")
public class CardEntity implements Serializable {
    // @Id, um JPA mitzuteilen, dass dieses Attribut der Primärschlüssel ist
    @Id
    private Long cardNumber;
    
    private Date expiryDate;
    
    // Unidirektionale 1:1-Beziehung herstellen mit einer Versicherer-Entität
    @OneToOne(targetEntity = InsurerEntity.class)
    private InsurerEntity insurer;
    
    // Unidirektionale 1:1-Beziehung herstellen mit einer Versicherten-Entität
    @OneToOne(targetEntity = PersonEntity.class)
    private PersonEntity insuredPerson;

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public InsurerEntity getInsurer() {
        return insurer;
    }

    public void setInsurer(InsurerEntity insurer) {
        this.insurer = insurer;
    }

    public PersonEntity getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(PersonEntity insuredPerson) {
        this.insuredPerson = insuredPerson;
    }
    
    
}
