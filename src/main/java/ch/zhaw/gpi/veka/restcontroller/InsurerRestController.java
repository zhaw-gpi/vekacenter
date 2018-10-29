package ch.zhaw.gpi.veka.restcontroller;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<InsurerEntity> getInsurerer() {
        // Liste aller Versicherer über Repository laden und der Variable insurerer zuweisen
        List<InsurerEntity> insurerer = insurerRepository.findAllByOrderByName();
        
        // Liste zurück geben an Client (wird automatisch in JSON serialisiert)
        return insurerer;
    }
    
    /**
     * REST-Ressource für URL /vekaapi/v1/insurerer/{bagNumber} (GET)
     * 
     * @param bagNumber     Versicherer-Nummer gemäss BAG
     * @return              Versicherer-Angaben im JSON-Format
     */
    @RequestMapping(value = "/vekaapi/v1/insurerer/{bagNumber}", method = RequestMethod.GET)
    public InsurerEntity getInsurer(@PathVariable Long bagNumber){
        // Zur BAG-Nummer passenden Versicherer suchen
        Optional<InsurerEntity> insurer = insurerRepository.findById(bagNumber);
        
        // Falls Versicherer gefunden wurde, dann diesen zurück geben
        if(insurer.isPresent()) {
            return insurer.get();
        } else {
            // ansonsten null zurückgeben (in der Realität wäre HTTP 404 sinnvoller, denn nun kommt 200)
            return null;
        }        
    }
    
    /**
     * REST-Ressource für URL /vekaapi/v1/insurerer (POST)
     * 
     * @param newInsurer        Ein Insurer-Objekt im JSON-Format
     * @return                  Das neu angelegte Insurer-Objekt
     */
    @RequestMapping(value = "/vekaapi/v1/insurerer", method = RequestMethod.POST)
    public InsurerEntity addInsurer(@RequestBody InsurerEntity newInsurer){
        return insurerRepository.save(newInsurer);
    }
}
