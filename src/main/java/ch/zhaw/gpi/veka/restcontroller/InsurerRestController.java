package ch.zhaw.gpi.veka.restcontroller;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-Controller für die Ressource Versicherer
 * 
 * @author scep
 */
@RestController
public class InsurerRestController {
    // Verdrahten des Versicherer-Repositories
    @Autowired
    private InsurerRepository insurerRepository;
    
    /**
     * REST-Ressource für URL /vekaapi/v1/insurerer (GET)
     * 
     * @return      Liste aller Versicherer im JSON-Format
     */
    @RequestMapping(value = "/vekaapi/v1/insurerer", method = RequestMethod.GET)
    public ResponseEntity<List<InsurerEntity>> getInsurerer() {
        // Liste aller Versicherer über Repository laden und der Variable insurerer zuweisen
        List<InsurerEntity> insurerer = insurerRepository.findAllByOrderByName();
        
        // Wenn die Liste Einträge enthält...
        if(insurerer != null && !insurerer.isEmpty()){
            // ... dann diese als Body zurückgeben
            return new ResponseEntity(insurerer, HttpStatus.OK);
        } else {
            // ... ansonsten ResourceNotFoundException (404)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * REST-Ressource für URL /vekaapi/v1/insurerer/{bagNumber} (GET)
     * 
     * @param bagNumber     Versicherer-Nummer gemäss BAG
     * @return              Versicherer-Angaben im JSON-Format
     */
    @RequestMapping(value = "/vekaapi/v1/insurerer/{bagNumber}", method = RequestMethod.GET)
    public ResponseEntity<InsurerEntity> getInsurer(@PathVariable Long bagNumber){
        // Zur BAG-Nummer passenden Versicherer suchen
        Optional<InsurerEntity> insurer = insurerRepository.findById(bagNumber);
        
        // Falls Versicherer gefunden wurde, dann diesen zurück geben
        if(insurer.isPresent()) {
            return new ResponseEntity(insurer.get(), HttpStatus.OK);
        } else {
            // Ansonsten ResourceNotFoundException (404)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }      
    }
    
    /**
     * REST-Ressource für URL /vekaapi/v1/insurerer (POST)
     * 
     * @param newInsurer        Ein Insurer-Objekt im JSON-Format
     * @return                  Das neu angelegte Insurer-Objekt
     */
    @RequestMapping(value = "/vekaapi/v1/insurerer", method = RequestMethod.POST)
    public ResponseEntity<InsurerEntity> addInsurer(@RequestBody InsurerEntity newInsurer){
        // Prüfen, ob nicht bereits eine Krankenkasse mit dieser Id erfasst ist
        Optional<InsurerEntity> searchedInsurer = insurerRepository.findById(newInsurer.getId());
        if(searchedInsurer.isPresent()){
            // Falls ja, dann Konflikt-Meldung zurückgeben
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            try {
                // Versuchen, den neuen Versicherer zu persistieren
                InsurerEntity persistedInsurer = insurerRepository.save(newInsurer);

                // Erfolgreiche Response zurück geben
                return new ResponseEntity(persistedInsurer, HttpStatus.CREATED);
            } catch(Exception e) {
                // Ansonsten allgemeine Fehlermeldung zurückgeben
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
