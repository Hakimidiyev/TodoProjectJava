package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MessageSource messageSource;

    @GetMapping("/home")
    public ModelAndView home(@CookieValue String language, @RequestParam(required = false)String lang) {
        lang= Objects.requireNonNullElse(lang,language);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("username", "Hakim");
        String message = messageSource.getMessage("welcome2", new Object[]{"Hakim"}, Locale.forLanguageTag(lang));
        System.out.println(message);
        return modelAndView;
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "ADMIN page";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "MANAGER page";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "USER page";
    }

    @PostMapping("/csrf/test")
    public String csrfTest() {
        System.out.println("Request accepted successfully");
        return "redirect:/todo";
    }
}
