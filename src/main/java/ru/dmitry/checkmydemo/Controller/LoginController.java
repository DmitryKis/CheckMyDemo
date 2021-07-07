package ru.dmitry.checkmydemo.Controller;



import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dmitry.checkmydemo.Entity.User;
import ru.dmitry.checkmydemo.Repository.UserRepository;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String signIn(User user, Model model, HttpServletResponse response) {
        User userRepositoryByUsernameOrId = userRepository.findByUsernameOrId(user.getUsername(), null);
        if (userRepositoryByUsernameOrId == null || !passwordEncoder.matches(user.getPassword(), userRepositoryByUsernameOrId.getPassword())) {
            return "login";
        }
        Cookie cookie = new Cookie("userId", userRepositoryByUsernameOrId.getId().toString());
        response.addCookie(cookie);
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
