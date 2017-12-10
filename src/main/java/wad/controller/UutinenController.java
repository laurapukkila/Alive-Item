package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Kategoria;
import wad.domain.Kirjoittaja;
import wad.domain.Uutinen;
import wad.repository.KategoriaRepository;
import wad.repository.KirjoittajaRepository;
import wad.repository.UutinenRepository;

@Controller
public class UutinenController {

    @Autowired
    private UutinenRepository uutinenRepository;
    
    @Autowired
    private KirjoittajaRepository kirjoittajaRepository;
    
    @Autowired
    private KategoriaRepository kategoriaRepository;

    @GetMapping("/uutiset")
    public String list(Model model) {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.ASC, "julkaisuaika");
        model.addAttribute("uutisetJulkaisuaika", uutinenRepository.findAll(pageable));
//        PageRequest pageable1 = PageRequest.of(0,5, Sort.Direction.ASC, "lukukertoja");
//        model.addAttribute("uutisetSuosituimmat", uutinenRepository.findAll(pageable1));
        return "uutiset";
    }
    
    @GetMapping("/lisaaUutinen")
    public String lisaaUutinen(Model model) {
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        return "lisaaUutinen";
    }

    @PostMapping("/lisaaUutinen")
    public String create(@RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String teksti, @RequestParam String kirjoittaja, @RequestParam String kategoria) {
        Uutinen u = new Uutinen();
        u.setOtsikko(otsikko);
        u.setIngressi(ingressi);
        u.setTeksti(teksti);
        Kirjoittaja k = new Kirjoittaja();
        k.setNimi(kirjoittaja);
        kirjoittajaRepository.save(k);
        u.lisaaKirjoittaja(k);
        Kategoria kat = kategoriaRepository.findByNimi(kategoria);
        u.lisaaKategoria(kat);
        
        uutinenRepository.save(u);
        return "redirect:/uutiset";
    }
    
    @GetMapping("/uutiset/{id}")
    public String uutinen(Model model, @PathVariable Long id) {
        model.addAttribute("uutinen", uutinenRepository.getOne(id));
        return "uutinen";
    }
}
