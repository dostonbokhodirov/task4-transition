package intern.task4.authorization.authUser;

import intern.task4.authorization.config.security.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    @PostMapping(value = "/update")
    public String blockUsers(Authentication authentication, HttpServletRequest request, @ModelAttribute DataDTO dto) {
        boolean session = isSession(authentication, dto.getIds());
        if ("submit".equals(dto.getName()) || "🔓".equals(dto.getName())) {
            authUserService.blockOrUnblock(dto.getIds());
        } else if ("❌".equals(dto.getName())) {
            authUserService.delete(dto.getIds());
        }
        return session ? "redirect:login" : "redirect:" + request.getHeader("Referer");
    }

    private boolean isSession(Authentication authentication, List<Long> ids) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String username = details.getUsername();
        Long sessionId = authUserService.getIdByName(username);
        return ids.stream().anyMatch(id -> Objects.equals(id, sessionId));
    }

}
