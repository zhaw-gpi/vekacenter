package ch.zhaw.gpi.veka.resourcedeclarations;

import ch.zhaw.gpi.veka.controller.InsurerController;
import ch.zhaw.gpi.veka.entities.InsurerEntity;
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
public class InsurerResourceDeclarations {
    
    // Verdrahten der Controller-Klassen, welche die eigentliche Business-Logik umfassen
    @Autowired
    private InsurerController insurerController;
    
    /**
     * REST-Resource für http.../vekapi/addinsurer, welche eine Methode
     * aufruft, welche einen neuen Versicherer in der VeKa-Center-Datebank anlegt
     * und zurückgibt.
     * 
     * In der Realität wäre so eine Resource sicher gut über 
     * Authentifizierung und Authorisierung geschützt
     * 
     * @param name          Name des Versicherers
     * @param street        Strasse
     * @param housenumber   Hausnummer
     * @param plz           Postleitzahl
     * @param town          Ort
     * @return              Ein Versicherer-Objekt im JSON-Format
     */
    @RequestMapping("/vekaapi/addinsurer")
    public InsurerEntity getInsurerOfCard (
            @RequestParam(name="name") String name,
            @RequestParam(name="street") String street,
            @RequestParam(name="housenumber") String housenumber,
            @RequestParam(name="plz") int plz,
            @RequestParam(name="town") String town){
        
        return insurerController.addInsurer(name, street, housenumber, plz, town);
    } 
}
