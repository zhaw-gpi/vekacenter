package ch.zhaw.gpi.veka.webcontroller;

import ch.zhaw.gpi.veka.entities.InsurerEntity;
import ch.zhaw.gpi.veka.repositories.InsurerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author scep
 */
@Controller
public class InsurerListController {
    
    @Autowired
    private InsurerRepository insurerRepository;
    
    @GetMapping("/registeredinsurerer")
    public String insurerListView(Model model){
        // Liste aller Versicherer erhalten
        List<InsurerEntity> insurerList = insurerRepository.findAllByOrderByName();
        
        model.addAttribute("insurerList", insurerList);
        return "InsurerListTemplate";
    }
}
