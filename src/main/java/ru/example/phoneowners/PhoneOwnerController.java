package ru.example.phoneowners;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhoneOwnerController {
    private final PhoneOwnerRepository repository;

    public PhoneOwnerController(PhoneOwnerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() { return "redirect:/owners"; }

    @GetMapping("/owners")
    public String list(Model model) {
        model.addAttribute("owners", repository.findAll());
        return "owners";
    }

    @GetMapping("/owners/new")
    public String newOwner(Model model) {
        model.addAttribute("owner", new PhoneOwner());
        return "owner-form";
    }

    @PostMapping("/owners")
    public String create(@ModelAttribute PhoneOwner owner) {
        owner.setId(null);
        return "redirect:/owners/" + repository.save(owner).getId();
    }
}
