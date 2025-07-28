package uz.pdp.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.daos.AuthUserDao;
import uz.pdp.dto.UserRegisterDto;
import uz.pdp.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserDao authUserDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public AuthController(AuthUserDao authUserDao, PasswordEncoder passwordEncoder, AuthService authService) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

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
    public String registerPage(){
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDto dto){

        authService.register(dto);
        return "redirect:/auth/login";
    }
}
