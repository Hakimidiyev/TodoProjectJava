package uz.pdp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.pdp.config.security.CustomUserDetails;
import uz.pdp.config.security.SessionUser;
import uz.pdp.domains.AuthUser;

@Controller
@ResponseBody
public class AuthenticationUserInfoController {


    private final SessionUser sessionUser;

    public AuthenticationUserInfoController(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    @GetMapping("/create/blog")
    public String createBlog(@AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthUser user = sessionUser.getUser();
        System.out.println(user.getId());
        return "Authenticated User info";
    }

}
