package ch.zhaw.gpi.veka.controller;

import ch.zhaw.gpi.veka.entities.CardEntity;
import ch.zhaw.gpi.veka.entities.PersonEntity;
import ch.zhaw.gpi.veka.repositories.CardRepository;
import ch.zhaw.gpi.veka.results.CheckBaseInsuranceResult;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller-Klasse, welche prüft, ob eine Person grundversichert ist
 * 
 * @Component, damit dieser Controller wiederverwendbare Bean instanziert wird
 * 
 * @author scep
 */
@Component
public class CheckBaseInsuranceController {
    
    // Verdrahten der Repository-Klasse, um Karten in der Datenbank zu finden
    @Autowired
    private CardRepository cardRepository;
    
    /**
     * Methode, welche prüft, ob eine Person grundversichert ist, indirekt über
     * die Kartennummer, deren Vorhandensein und Gültigkeit und über die
     * Übereinstimmung der Personalien
     * 
     * @param baseInsuranceNumber   Grundversicherungskartennummer
     * @param firstName             Vorname
     * @param officialName          Nachname
     * @param dateOfBirth           Geburtsdatum
     * @return                      Objekt der CheckBaseInsuranceResult-Klasse
     */
    public CheckBaseInsuranceResult checkBaseInsuranceValidity(Long baseInsuranceNumber, String firstName, String officialName, Date dateOfBirth){
        // Result-Objekt instanzieren
        CheckBaseInsuranceResult checkBaseInsuranceResult = new CheckBaseInsuranceResult();
        
        // Zur Kartennummer passende Karte suchen
        Optional<CardEntity> card = cardRepository.findById(baseInsuranceNumber);
        
        // Falls keine Karte gefunden wurde, dann entsprechende Antwort zurück geben
        if(!card.isPresent()) {
            checkBaseInsuranceResult
                    .setCheckResult("Unknown")
                    .setCheckResultDetails("Keine Karte zu dieser Nummer gefunden. Mögliche Gründe: (Un)bewusste Falscheingabe oder der Kartenaussteller ist nicht bei der VeKa registriert.");
            return checkBaseInsuranceResult;
        }
        
        // Falls Karte abgelaufen ist, entsprechende Antwort zurück geben
        if(card.get().getExpiryDate().before(new Date())){
            checkBaseInsuranceResult
                    .setCheckResult("No")
                    .setCheckResultDetails("Karte abgelaufen");
            return checkBaseInsuranceResult;
        }
        
        /**
         * Falls Personendaten zur Karte nicht mit übergebenen Personendaten 
         * übereinstimmen, entsprechende Antwort zurück geben
         */
        // Person aus der Karte auslesen
        PersonEntity insuredPerson = card.get().getInsuredPerson();
        
        // Hilfsvariable, um die allenfalls abweichenden Typen von Personalien aufzunehmen
        String abweichendePersonalien = "";
        
        // Das Geburtsdatum in der Datenbank wird als Timestamp gespeichert, übergeben
        // hingegen ein Date. Daher beide umwandeln in ein Long für den Vergleich
        Long birthDateDb = insuredPerson.getDateOfBirth().getTime();
        Long birthDateParam = dateOfBirth.getTime();
        if(!birthDateParam.equals(birthDateDb)){
            // Falls abweichend, dann die Hilfsvariable um den Typ der Personalie erweitern
            abweichendePersonalien += "Geburtsdatum";
        }
        
        // Vornamen auf Abweichung prüfen
        if(!insuredPerson.getFirstName().equals(firstName)){
            // Falls abweichend, dann die Hilfsvariable um den Typ der Personalie erweitern
            abweichendePersonalien += (abweichendePersonalien.isEmpty() ? "" : ", ") + "Vorname";
        }
        
        // Nachname auf Abweichung prüfen
        if(!insuredPerson.getOfficialName().equals(officialName)){
            // Falls abweichend, dann die Hilfsvariable um den Typ der Personalie erweitern
            abweichendePersonalien += (abweichendePersonalien.isEmpty() ? "" : ", ") + "Nachname";
        }
        
        // Falls es Abweichungen gibt (also die Hilfsvariable nicht leer ist)
        if(!abweichendePersonalien.isEmpty()){
            // Entsprechendes Resultat zurückgeben
            checkBaseInsuranceResult
                    .setCheckResult("No")
                    .setCheckResultDetails("Karte gültig, aber Personalien nicht passend (" + abweichendePersonalien + ")");
            return checkBaseInsuranceResult;
        }
        
        // Falls alles in Ordnung ist, dann ein positives Resultat zurück geben
        return checkBaseInsuranceResult.setCheckResult("Yes");
    }
    
}
