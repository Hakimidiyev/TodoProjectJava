package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.daos.AuthUserDao;
import uz.pdp.dto.UserRegisterDto;
import uz.pdp.service.AuthService;

import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final MessageSource messageSource;


    @GetMapping("/login")
    public ModelAndView loginPage(@RequestParam(required = false) String error){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("errorMessage",error);
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logoutPage(Model model){
        return "auth/logout";
    }

    @GetMapping("/register")
    public String registerPage(@CookieValue String language,@RequestParam(required = false)String lang){
        lang= Objects.requireNonNullElse(lang,language);
        String message=messageSource.getMessage("welcome",new Object[]{"USER"}, Locale.forLanguageTag(lang));
        System.out.println("message = " + message);
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDto dto){
        authService.register(dto);
        return "redirect:/auth/login";
    }
}
