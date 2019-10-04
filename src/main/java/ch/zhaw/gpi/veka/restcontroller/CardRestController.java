package ch.zhaw.gpi.veka.restcontroller;

import ch.zhaw.gpi.veka.entities.CardEntity;
import ch.zhaw.gpi.veka.repositories.CardRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-Controller für die Ressource Versichertenkarten
 * 
 * @author scep
 */
@RestController
// Zugriff via JavaScript auf diese Methode soll von allen Clients möglich sein
@CrossOrigin
public class CardRestController {
    
    // Verdrahten der Repository-Klasse, um Karten in der Datenbank zu finden
    @Autowired
    private CardRepository cardRepository;
    
    /**
     * REST-Ressource für URL /vekaapi/v1/cards/{cardNumber} (GET)
     * 
     * @param cardNumber        Versicherten-Kartennummer
     * @return                  HTTP-Response mit einem Status 200 oder 404, sowie im ersten Fall einer zur Kartennummer passenden Versicherten-Karten-Entität als Body
     */
    @RequestMapping(value = "/vekaapi/v1/cards/{cardNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getCard(@PathVariable Long cardNumber){        
        // Zur Kartennummer passende Karte suchen
        Optional<CardEntity> card = cardRepository.findById(cardNumber);
        
        // Falls Karte gefunden wurde, dann card zurück geben
        if(card.isPresent()) {
            return new ResponseEntity<CardEntity>(card.get(), HttpStatus.OK);
        } else {
            // Ansonsten ResourceNotFoundException (404)
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }        
    }
    
    /**
     * REST-Ressource für URL /vekaapi/v1/cards (GET)
     * 
     * @return                  HTTP-Response mit einem Status 200 oder 404, sowie im ersten Fall einer Liste aller Versichertenkarten-Entitäten im JSON-Format
     */
    @RequestMapping(value = "/vekaapi/v1/cards", method = RequestMethod.GET)
    public ResponseEntity<?> getCards(){
        // Alle Karten aus dem Repository laden und der cards-Variable zuweisen
        List<CardEntity> cards = cardRepository.findAll();
        
        // Wenn die Liste Einträge enthält...
        if(cards != null && !cards.isEmpty()){
            // ... dann diese als Body zurückgeben
            return new ResponseEntity<List<CardEntity>>(cards, HttpStatus.OK);
        } else {
            // ... ansonsten ResourceNotFoundException (404)
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
    }
}
