package ch.zhaw.gpi.veka.resourcedeclarations;

import ch.zhaw.gpi.veka.controller.CheckBaseInsuranceController;
import ch.zhaw.gpi.veka.controller.InsurerController;
import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.results.CheckBaseInsuranceResult;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Klasse, welche alle REST-Resourcen deklariert in Bezug zu den Karten
 * 
 * Für Demo-Zwecke sind zwei davon aufgeführt, eine davon für den eUmzug relevant
 * 
 * @author scep
 */
@RestController
public class BaseInsuranceCardResourceDeclarations {
    
    // Verdrahten der Controller-Klassen, welche die eigentliche Business-Logik umfassen
    @Autowired
    private CheckBaseInsuranceController checkBaseInsuranceController;
    @Autowired
    private InsurerController insurerController;
    
    /**
     * REST-Resource für http.../vekaapi/checkbaseinsurance, welche eine Methode
     * aufruft, die prüft, ob eine Person grundversichert ist
     * 
     * @RequestMapping mapped die entsprechende Request-URL auf die annotierte Methode
     * @RequestParam definiert einen erwarteten Parameter für den Request -> beim
     * Date-Feld ist in application.properties definiert, in welchem String-Format
     * dieser zu übergeben ist
     * 
     * @param baseInsuranceNumber   Grundversicherungskartennummer
     * @param firstName             Vorname
     * @param officialName          Nachname
     * @param dateOfBirth           Geburtsdatum
     * @return                      Objekt der CheckBaseInsuranceResult-Klasse
     */
    @RequestMapping("/vekaapi/checkbaseinsurance")
    public CheckBaseInsuranceResult checkBaseInsurance(
            @RequestParam(name="baseInsuranceNumber") Long baseInsuranceNumber,
            @RequestParam(name="firstName") String firstName,
            @RequestParam(name="officialName") String officialName,
            @RequestParam(name="dateOfBirth") Date dateOfBirth){
        
        // Aufrufen der Methode und Zurückgeben des erhaltenen Resultats. Dieses wird automatisch
        // in ein JSON-Objekt übersetzt
        return checkBaseInsuranceController.checkBaseInsuranceValidity(baseInsuranceNumber, firstName, officialName, dateOfBirth);
        
    }
    
    /**
     * REST-Resource für http.../vekapi/getinsurerofcard, welche eine Methode
     * aufruft, welche ein Versicherer-Objekt zu einer bestimmten Karte zurückgibt
     * 
     * @param baseInsuranceNumber   Grundversicherungskartennummer
     * @return                      Ein Versicherer-Objekt im JSON-Format
     */
    @RequestMapping("/vekaapi/getinsurerofcard")
    public InsurerEntity getInsurerOfCard (
            @RequestParam(name="baseInsuranceNumber") Long baseInsuranceNumber){
        
        return insurerController.getInsurerOfCard(baseInsuranceNumber);
        
    } 
}
