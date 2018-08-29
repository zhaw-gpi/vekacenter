package ch.zhaw.gpi.veka.webcontroller;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
public class InsurerListController {
    
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
    @GetMapping("/registeredinsurerer")
    public String insurerListView(Model model){
        // Liste aller Versicherer erhalten
        List<InsurerEntity> insurerList = insurerRepository.findAllByOrderByName();
        
        // Diese Liste einem Attribut im Model übergeben
        model.addAttribute("insurerList", insurerList);
        
        // Den Namen des zu rendernden Templates (src/main/resources/templates) zurückgeben
        return "InsurerListTemplate";
    }
}
