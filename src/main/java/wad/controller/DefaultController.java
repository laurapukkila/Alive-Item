package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wad.domain.Kategoria;
import wad.repository.KategoriaRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private KategoriaRepository kategoriaRepository;

    @GetMapping("*")
    public String handleDefault() {
        luoKategoriat();
        return "redirect:/uutiset";
    }
    
    public void luoKategoriat() {
        Kategoria Urheilu = new Kategoria();
        Urheilu.setNimi("Urheilu");
        Kategoria Kotimaa = new Kategoria();
        Kotimaa.setNimi("Kotimaa");
        Kategoria Ulkomaat = new Kategoria();
        Ulkomaat.setNimi("Ulkomaat");
        Kategoria Viihde = new Kategoria();
        Viihde.setNimi("Viihde");
        Kategoria Tiede = new Kategoria();
        Tiede.setNimi("Tiede");
        Kategoria Talous = new Kategoria();
        Talous.setNimi("Talous");
        Kategoria Terveys = new Kategoria();
        Terveys.setNimi("Terveys");
        Kategoria Kulttuuri = new Kategoria();
        Kulttuuri.setNimi("Kulttuuri");
        kategoriaRepository.save(Urheilu);
        kategoriaRepository.save(Kotimaa);
        kategoriaRepository.save(Ulkomaat);
        kategoriaRepository.save(Viihde);
        kategoriaRepository.save(Tiede);
        kategoriaRepository.save(Talous);
        kategoriaRepository.save(Terveys);
        kategoriaRepository.save(Kulttuuri);
        kategoriaRepository.flush();
    }
}