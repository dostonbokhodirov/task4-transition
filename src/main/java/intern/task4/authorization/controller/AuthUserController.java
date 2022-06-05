package intern.task4.authorization.controller;

import intern.task4.authorization.entity.AuthUser;
import intern.task4.authorization.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @GetMapping({"/", "", "/login"})
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute AuthUser authUser) {
        authUserService.create(authUser);
        return "auth/login";
    }

    @GetMapping(value = "/home")
    public String homePage(Model model) {
        model.addAttribute("users", authUserService.getAll());
        return "auth/index";
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePage(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        authUserService.delete(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping(value = {"/block/{id}", "/unblock/{id}"})
    public String blockPage(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        authUserService.blockOrUnblock(id);
        return "redirect:" + request.getHeader("Referer");
    }

}
