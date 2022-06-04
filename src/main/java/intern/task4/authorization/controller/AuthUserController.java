package intern.task4.authorization.controller;

import intern.task4.authorization.dto.AuthUserUpdateDto;
import intern.task4.authorization.dto.LoginDto;
import intern.task4.authorization.entity.AuthUser;
import intern.task4.authorization.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;

    @GetMapping({"/", "", "/login"})
    public String loginPage() {
        return "auth/login";
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute LoginDto dto) {
//        return authUserService.login(dto) ? "/auth/home" : "redirect:/auth/login";
//    }

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
    public String deletePage(Model model, @PathVariable(name = "id") Long id) {
        authUserService.delete(id);
        model.addAttribute("user", authUserService.get(id));
        return "redirect:auth/index";
    }

    @GetMapping(value = "/block/{id}")
    public String blockPage(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", authUserService.get(id));
        return "auth/block";
    }

}
