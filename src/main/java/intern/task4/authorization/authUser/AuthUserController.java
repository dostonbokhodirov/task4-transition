package intern.task4.authorization.authUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
    public String homePage(Model model, ModelAndView modelAndView) {
        model.addAttribute("users", authUserService.getAll());
        modelAndView.addObject("requestIds", new ArrayList<Long>());
        return "auth/index";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        authUserService.delete(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping(value = "/delete-list")
    public String delete(HttpServletRequest request, @RequestBody List<Long> id) {
        authUserService.delete(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping(value = {"/block/{id}", "/unblock/{id}"})
    public String block(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        authUserService.blockOrUnblock(id);
       return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping(value = {"/block-users", "/unblock-users"})
    public String blockUsers(HttpServletRequest request, @ModelAttribute("ids") ArrayList<Long> ids) {
        System.out.println(request);
        authUserService.blockOrUnblock(ids);
        return "redirect:" + request.getHeader("Referer");
    }

}
