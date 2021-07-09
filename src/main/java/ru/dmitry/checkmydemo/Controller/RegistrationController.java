package ru.dmitry.checkmydemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dmitry.checkmydemo.Entity.Status;
import ru.dmitry.checkmydemo.Entity.User;
import ru.dmitry.checkmydemo.Repository.UserRepository;
import ru.dmitry.checkmydemo.Service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    private String signUp(){
        return "registration";
    }

    @PostMapping("/signup")
    private String addUser(User user, Model model){
        if(!userService.addUserInDB(user)){
            model.addAttribute("registered","User with this Username or Email already registered");
            return "registration";
        } else{
            return "redirect:/activate/email";
        }
    }

    @GetMapping("/activate/email")
    public String sendTokenPage(){
        return "send_activate";
    }

    @GetMapping(value="/activate/{token}")
    public String verificationToken(@PathVariable("token") String token){
        if (userService.activateUserFromEmail(token)){
            return "redirect:/activate/success";
        }
        return "redirect:/activate/success";

        //Добавить обработку неподходящего токена User

    }

    @GetMapping("/activate/success")
    public String successActivatePage(){
        return "success_activate";
    }
}
