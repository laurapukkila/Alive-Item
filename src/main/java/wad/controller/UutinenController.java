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
    public String listaaUutiset(Model model) {
        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "julkaisuaika");
        model.addAttribute("uutisetJulkaisuaika", uutinenRepository.findAll(pageable));
        PageRequest pageable1 = PageRequest.of(0, 5, Sort.Direction.DESC, "lukukertoja");
        model.addAttribute("uutisetSuosituimmat", uutinenRepository.findAll(pageable1));
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        return "uutiset";
    }

    @GetMapping("/lisaaUutinen")
    public String lisaaUutinen(Model model) {
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        model.addAttribute("uutiset", uutinenRepository.findAll());
        model.addAttribute("kirjoittajat", kirjoittajaRepository.findAll());
        return "lisaaUutinen";
    }

    @GetMapping("/muokkaaUutista")
    public String muokkaaUutista(Model model) {
        model.addAttribute("kategoriat", kategoriaRepository.findAll());
        model.addAttribute("uutiset", uutinenRepository.findAll());
        model.addAttribute("kirjoittajat", kirjoittajaRepository.findAll());
        return "muokkaaUutista";
    }

    @PostMapping("/muokkaaUutista")
    public String muokkaaUutista(@RequestParam Long uutinen, @RequestParam String otsikko, @RequestParam String ingressi, @RequestParam String teksti, @RequestParam String kirjoittaja) {
        Uutinen u = uutinenRepository.getOne(uutinen);
        if (!otsikko.isEmpty()) {
            u.setOtsikko(otsikko);
        }
        if(!ingressi.isEmpty()) {
            u.setIngressi(ingressi);
        }
        if(!teksti.isEmpty()) {
            u.setTeksti(teksti);
        }
        Kirjoittaja k = kirjoittajaRepository.findByNimi(kirjoittaja);
        if (k == null) {
            k = new Kirjoittaja();
            k.setNimi(kirjoittaja);
            kirjoittajaRepository.save(k);
        }
        u.lisaaKirjoittaja(k);
        uutinenRepository.save(u);
        return "redirect:/muokkaaUutista";
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

        Kategoria kategori = kategoriaRepository.findByNimi(kategoria);
        kategori.lisaaUutinen(u);
        u.lisaaKategoria(kategori);
        
        kategoriaRepository.save(kategori);
        uutinenRepository.save(u);
        return "redirect:/lisaaUutinen";
    }

    @PostMapping("/lisaaKategoria")
    public String lisaaKategoria(@RequestParam Long uutinen, @RequestParam Long kategoria) {
        Uutinen u = uutinenRepository.getOne(uutinen);
        Kategoria k = kategoriaRepository.getOne(kategoria);
        u.lisaaKategoria(k);
        k.lisaaUutinen(u);
        kategoriaRepository.save(k);
        uutinenRepository.save(u);
        return "redirect:/lisaaUutinen";
    }

    @PostMapping("/lisaaKirjoittaja")
    public String lisaaKirjoittaja(@RequestParam Long uutinen, @RequestParam Long kirjoittaja) {
        Uutinen u = uutinenRepository.getOne(uutinen);
        u.lisaaKirjoittaja(kirjoittajaRepository.getOne(kirjoittaja));
        uutinenRepository.save(u);
        return "redirect:/lisaaUutinen";
    }

    @GetMapping("/uutiset/{id}")
    public String uutinen(Model model, @PathVariable Long id) {
        Uutinen u = uutinenRepository.getOne(id);
        u.setLukukertoja(u.getLukukertoja() + 1);
        uutinenRepository.save(u);
        model.addAttribute("uutinen", uutinenRepository.getOne(id));
        model.addAttribute("kirjoittajat", uutinenRepository.getOne(id).getKirjoittajat());
        model.addAttribute("kategoriat", uutinenRepository.getOne(id).getKategoriat());
        model.addAttribute("julkaisuaika", uutinenRepository.getOne(id).getJulkaisuaika());
        model.addAttribute("listaakategoriat", kategoriaRepository.findAll());
        return "uutinen";
    }
    
    @GetMapping("/kategoria/{id}")
    public String kategoria(Model model, @PathVariable Long id) {
        Kategoria k = kategoriaRepository.getOne(id);
        model.addAttribute("kategoria", k);
        model.addAttribute("uutiset", k.getUutiset());
        return "kategoria";
    }
}
