package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Uutinen;
import wad.repository.UutinenRepository;

@Controller
public class UutinenController {

    @Autowired
    private UutinenRepository uutinenRepository;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("uutiset", uutinenRepository.findAll());
        return "index";
    }

    @PostMapping("/")
    public String create(@RequestParam String name) {
        Uutinen u = new Uutinen();
        u.setOtsikko(name);
        uutinenRepository.save(u);
        return "redirect:/";
    }
}
