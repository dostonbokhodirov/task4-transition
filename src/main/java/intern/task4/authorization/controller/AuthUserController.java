package intern.task4.authorization.controller;

import intern.task4.authorization.dto.AuthUserCreateDto;
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

    @GetMapping({"/","", "/login"})
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping( "/login")
    public String login(@ModelAttribute LoginDto dto) {
        authUserService.login(dto);
        return "auth/index";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    @GetMapping("/register")
    public String registrationPage() {
        return "auth/register";
    }

    @PostMapping(value = "/register")
    public String create(@ModelAttribute AuthUser authUser) {
        authUserService.create(authUser);
        return "auth/login";
    }

    @GetMapping("/detail/{id}/")
    public String detail(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", authUserService.get(id));
        return "user/detail";
    }

    @GetMapping(value = "/contacts")
    public String listPage(Model model) {
        model.addAttribute("users", authUserService.getAll(1L));
        return "auth/users";
    }

    @GetMapping(value = "/users/{organization_id}")
    public String listPage(@PathVariable(name = "organization_id")Long id, Model model) {
        model.addAttribute("users", authUserService.getAll(id));
        return "auth/users";
    }


    @GetMapping(value = "/update/{id}/")
    public String updatePage(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", authUserService.get(id));
        return "user/update";
    }

    @PatchMapping(value = "/update/{id}/")
    public String update(@ModelAttribute AuthUserUpdateDto dto) {
        authUserService.update(dto);
        return "redirect:/";
    }

    @GetMapping(value = "/delete/{id}")
    public String deletePage(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("user", authUserService.get(id));
        return "user/delete";
    }


}
