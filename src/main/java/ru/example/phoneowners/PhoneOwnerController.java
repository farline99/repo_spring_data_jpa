package ru.example.phoneowners;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhoneOwnerController {
    private final PhoneOwnerRepository repository;

    public PhoneOwnerController(PhoneOwnerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/owners";
    }

    @GetMapping("/owners")
    public String list(Model model) {
        model.addAttribute("owners", repository.findAll());
        return "owners";
    }
}
