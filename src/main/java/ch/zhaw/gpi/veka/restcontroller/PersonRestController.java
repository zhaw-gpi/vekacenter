package ch.zhaw.gpi.veka.restcontroller;

import ch.zhaw.gpi.veka.entities.AddressEntity;
import ch.zhaw.gpi.veka.entities.PersonEntity;
import ch.zhaw.gpi.veka.repositories.AddressRepository;
import ch.zhaw.gpi.veka.repositories.PersonRepository;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Geschäftslogik und REST-Schnittstelle im Zusammenhang mit versicherten Personen
 * 
 * @author scep und Manuel Weiss
 */
@RestController
// Zugriff via JavaScript auf diese Methode soll von allen Clients möglich sein
@CrossOrigin
public class PersonRestController {
    
    // Verdrahten benötigter JPA-Repositories
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;
    
    /**
     * Einer Person mit einer übergebenen PersonenId die im JSON-Format übergebene Adresse zuweisen
     * 
     * @param newAddress        Die neue Adresse
     * @param id                Id der Person
     * @return ResponseEntity   Nicht-Gefunden-Status (404), falls Person nicht vorhanden, ansonsten OK-Status (200)
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/vekaapi/v1/persons/{id}/address")
    // @Valid: Stellt sicher, dass newAddress die Einschränkungen in AdressEntity einhalten muss (z.B. NotNull)
    public ResponseEntity<HttpStatus> updateAddress(@Valid @RequestBody AddressEntity newAddress, @PathVariable Long id){
        // Person über die Id im Repository suchen
        Optional<PersonEntity> person = personRepository.findById(id);
        
        // Falls Person mit dieser Id nicht vorhanden, ...
        if(!person.isPresent()){
            // ... dann 404 (Nicht gefunden)-Status zurückgeben
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
        
        // Die bisher zugewiesene Adresse in Variable speichern
        AddressEntity oldAddress = person.get().getAddressPostal();
        
        // Prüfen, ob die übergebene Adresse gar nicht neu ist, also mit der bereits der Person zugewiesenen übereinstimmt
        if(newAddress.equals(oldAddress)){
            // Falls ja, dann OK-Status zurückgeben
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }

        // Im Repository nach der neuen Adresse suchen
        Optional<AddressEntity> searchedAddress = addressRepository.findOne(Example.of(newAddress));
        
        // Falls die neue Adresse noch nicht erfasst ist, ...
        if(!searchedAddress.isPresent()){
            // ... dann diese zum Speichern vormerken
            addressRepository.save(newAddress);
        } else {
            // ... ansonsten die bereits vorhandene als neue Adresse festlegen
            newAddress = searchedAddress.get();
        }
        
        // Die neue Adresse der Person zuweisen
        person.get().setAddressPostal(newAddress);
        
        // Person mit neuer Adresse zum Speichern vormerken
        personRepository.save(person.get());
        
        // Falls die bisherige Adresse keiner Person mehr zugewiesen ist, also die Liste der Personen an einer Adresse leer ist, ...
        if(personRepository.findAllByAddressPostal(oldAddress).isEmpty()){
            // ... dann die Adresse zum Löschen vormerken
            addressRepository.delete(oldAddress);
        }
        
        // OK-Status zurück geben
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
