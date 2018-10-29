package ch.zhaw.gpi.veka.restcontroller;

import ch.zhaw.gpi.veka.entities.CardEntity;
import ch.zhaw.gpi.veka.repositories.CardRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CardRestController {
    
    // Verdrahten der Repository-Klasse, um Karten in der Datenbank zu finden
    @Autowired
    private CardRepository cardRepository;
    
    /**
     * REST-Ressource für URL /vekaapi/v1/cards/{cardNumber} (GET)
     * 
     * @param cardNumber        Versicherten-Kartennummer
     * @return                  zu einer Kartennummer passende Versicherten-Karten-Entität im JSON-Format
     */
    @RequestMapping(value = "/vekaapi/v1/cards/{cardNumber}", method = RequestMethod.GET)
    public CardEntity getCard(@PathVariable Long cardNumber){        
        // Zur Kartennummer passende Karte suchen
        Optional<CardEntity> card = cardRepository.findById(cardNumber);
        
        // Falls Karte gefunden wurde, dann card zurück geben
        if(card.isPresent()) {
            return card.get();
        } else {
            // ansonsten null zurückgeben (in der Realität wäre HTTP 404 sinnvoller, denn nun kommt 200)
            return null;
        }        
    }
    
    /**
     * REST-Ressource für URL /vekaapi/v1/cards (GET)
     * 
     * @return      Liste aller Versichertenkarten-Entitäten im JSON-Format
     */
    @RequestMapping(value = "/vekaapi/v1/cards", method = RequestMethod.GET)
    public List<CardEntity> getCards(){
        // Alle Karten aus dem Repository laden und der cards-Variable zuweisen
        List<CardEntity> cards = cardRepository.findAll();
        
        // Liste zurück geben an Client (wird automatisch in JSON serialisiert)
        return cards;
    }
}
