package ch.zhaw.gpi.veka.controller;

import ch.zhaw.gpi.veka.entities.AddressEntity;
import ch.zhaw.gpi.veka.entities.CardEntity;
import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.AddressRepository;
import ch.zhaw.gpi.veka.repositories.CardRepository;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller-Klasse, welche die Business-Logik im Zusammenhang mit der Versicherer-
 * Entity im VeKa-Center-Auskunftsdienst enthält
 * 
 * Für Demo-Zwecke sind zwei Methoden implementiert, beide für den eUmzug nicht relevant
 * 
 * @author scep
 */
@Component
public class InsurerController {
    
    // Verdrahten der Repository-Klassen
    @Autowired
    private InsurerRepository insurerRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private AddressRepository addressRepository;
    
    /**
     * Gibt ein Versicherer-Objekt zu einer bestimmten Kartennummer zurück
     * @param baseInsuranceNumber
     * @return
     */
    public InsurerEntity getInsurerOfCard(Long baseInsuranceNumber){
        // Zur Kartennummer passende Karte suchen
        Optional<CardEntity> card = cardRepository.findById(baseInsuranceNumber);
        
        // Falls keine Karte gefunden wurde, dann entsprechende Antwort zurück geben
        if(!card.isPresent()) {
            return null;
        }
        
        // Ansonsten die Versicherung auslesen und zurückgeben
        return card.get().getInsurer();
    }
    
    /**
     * Legt ein neues Versicherer-Objekt an
     * 
     * Gibt ein bestehendes zurück, wenn es bereits existiert oder legt ein neues
     * an (inklusive Adress-Entität falls erforderlich) und gibt dieses zurück
     * 
     * @param name          Name des Versicherers
     * @param street        Strasse
     * @param houseNumber   Hausnummer
     * @param plz           Postleitzahl
     * @param town          Ort
     * @return              Ein Versicherer-Objekt
     */
    public InsurerEntity addInsurer(String name, String street, String houseNumber, int plz, String town){
        // Prüfen, ob Insurer bereits erfasst ist
        List<InsurerEntity> insurerList = insurerRepository.findByNameIgnoreCase(name);
        
        // Wenn Liste nicht leer, dann das erste Objekt zurück geben, da kein Neues anzulegen ist
        if(!insurerList.isEmpty()){
            return insurerList.get(0);
        }
        
        // Prüfen, ob Adresse bereits erfasst ist
        List<AddressEntity> addresses = addressRepository.findByStreetAndHouseNumberAndTownAndPlzAllIgnoreCase(street, houseNumber, town, plz);
        
        AddressEntity address;
        if(addresses.isEmpty()){
            // Falls nicht, dann ein neues Objekt anlegen und persistieren
            address = new AddressEntity();
            address
                    .setStreet(street)
                    .setHouseNumber(houseNumber)
                    .setPlz(plz)
                    .setTown(town);
            addressRepository.save(address);
        } else {
            // Falls doch, dann dieses verwenden
            address = addresses.get(0);
        }
        
        // Neue InsurerEntity anlegen mit den entsprechenden Attributen
        InsurerEntity insurer = new InsurerEntity();
        insurer
                .setName(name)
                .setAddress(address);
        
        // Insurer-Entity persistieren
        insurerRepository.save(insurer);
        
        // Insurer-Entity zurückgeben
        return insurer;
    }
}
