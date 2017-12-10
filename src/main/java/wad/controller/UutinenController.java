package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Uutinen;
import wad.repository.UutinenRepository;

@Controller
public class UutinenController {

    @Autowired(required = false)
    private UutinenRepository uutinenRepository;

    @GetMapping("/uutiset")
    public String list(Model model) {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "julkaisuaika");
        model.addAttribute("uutisetJulkaisuaika", uutinenRepository.findAll(pageable));
        PageRequest pageable1 = PageRequest.of(0,5, Sort.Direction.ASC, "lukukertoja");
        model.addAttribute("uutisetSuosituimmat", uutinenRepository.findAll(pageable1));
        return "uutiset";
    }

    @PostMapping("/uutiset")
    public String create(@RequestParam String name) {
        Uutinen u = new Uutinen();
        u.setOtsikko(name);
        uutinenRepository.save(u);
        return "redirect:/";
    }
}
