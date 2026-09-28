package ru.example.phoneowners;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/owners/{id}")
    public String details(@PathVariable Long id, Model model) {
        return repository.findById(id).map(owner -> {
            model.addAttribute("owner", owner);
            return "owner-details";
        }).orElse("redirect:/owners");
    }

    @GetMapping("/owners/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return repository.findById(id).map(owner -> {
            model.addAttribute("owner", owner);
            return "owner-form";
        }).orElse("redirect:/owners");
    }

    @PostMapping("/owners/{id}")
    public String update(@PathVariable Long id, @ModelAttribute PhoneOwner owner) {
        if (!repository.existsById(id)) {
            return "redirect:/owners";
        }
        owner.setId(id);
        repository.save(owner);
        return "redirect:/owners/" + id;
    }

    @PostMapping("/owners/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
        return "redirect:/owners";
    }
}
