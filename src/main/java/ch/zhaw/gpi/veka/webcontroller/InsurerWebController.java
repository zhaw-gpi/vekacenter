package ch.zhaw.gpi.veka.webcontroller;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Spring-Web-Controller, welcher Ressourcen im Zusammenhang mit Versicherer-Daten bereitstellt
 * 
 * Angelehnt an https://spring.io/guides/gs/serving-web-content/
 * 
 * @Controller kennzeichnet diese Klasse als Spring-Komponente vom Typ (Web)Controller
 * 
 * @author scep
 */
@Controller
public class InsurerWebController {
    
    // Das Repository für die Kommunikation mit der Versicherer-Datenbanktabelle verdrahten
    @Autowired
    private InsurerRepository insurerRepository;
    
    /**
     * Methode, welche eine View mit einer Liste aller registrierten Versicherern zurückgibt
     * 
     * @param model     Die Spring Model-Variable, welche den Model-Teil in einem MVC-Kontext enthält
     * @return          Name des zu rendernden Templates, um letztlich im Hintergrund eine View aufzubauen, welche zurück gegeben wird
     * 
     * @GetMapping      Kennzeichnet diese Methode als Web-Ressource, welche über den Request mit /registeredinsurer zurückgegeben wird
     */
    @RequestMapping(value = "/insurerer", method = RequestMethod.GET)
    public String insurererView(Model model){
        // Liste aller Versicherer erhalten
        List<InsurerEntity> insurerer = insurerRepository.findAllByOrderByName();
        
        // Diese Liste einem Attribut im Model übergeben
        model.addAttribute("insurerer", insurerer);
        
        // Den Namen des zu rendernden Templates (src/main/resources/templates) zurückgeben
        return "InsurererTemplate";
    }
}
