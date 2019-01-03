package ch.zhaw.gpi.veka.restcontroller;

import ch.zhaw.gpi.veka.entities.AddressEntity;
import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.AddressRepository;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
// Zugriff via JavaScript auf diese Methode soll von allen Clients möglich sein
@CrossOrigin
public class InsurerRestController {
    // Verdrahten der Repositories
    @Autowired
    private InsurerRepository insurerRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
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
     * @return                  HTTP-Status (409, 200 oder 500)
     */
    @RequestMapping(value = "/vekaapi/v1/insurerer", method = RequestMethod.POST)
    public ResponseEntity addInsurer(@RequestBody @Valid InsurerEntity newInsurer){
        // Nach einem Versicherer mit der Id des anzulegenden Versicherers suchen
        Optional<InsurerEntity> searchedInsurer = insurerRepository.findById(newInsurer.getId());
        
        // Prüfen, ob bereits eine Krankenkasse mit dieser Id erfasst ist
        if(searchedInsurer.isPresent()){
            // Falls ja, dann OK-Status zurückgeben
            return new ResponseEntity(HttpStatus.OK);
        } else {
            // Ansonsten den neuen Versicherer anlegen versuchen
            try {
                // Adresse des Versicherers im Repository suchen
                Optional<AddressEntity> addressEntity = addressRepository.findByPlzAndStreetAndHouseNumber(
                        newInsurer.getAddress().getPlz(), 
                        newInsurer.getAddress().getStreet(),
                        newInsurer.getAddress().getHouseNumber());
                
                // Falls Adresse schon existiert, ...
                if(addressEntity.isPresent()){
                    // ...diese als Adresse verwenden
                    newInsurer.setAddress(addressEntity.get());
                } else {
                    // ... ansonsten diese als neue Adresse speichern und zuweisen
                    newInsurer.setAddress(addressRepository.save(newInsurer.getAddress()));
                }
                
                // Den neuen Versicherer persistieren
                insurerRepository.save(newInsurer);

                // Erfolgreiche Response zurück geben
                return new ResponseEntity(HttpStatus.OK);
            } catch(Exception e) {
                // Ansonsten allgemeine Fehlermeldung zurückgeben
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
