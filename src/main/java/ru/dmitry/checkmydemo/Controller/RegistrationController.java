package ru.dmitry.checkmydemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dmitry.checkmydemo.Entity.Role;
import ru.dmitry.checkmydemo.Entity.Status;
import ru.dmitry.checkmydemo.Entity.User;
import ru.dmitry.checkmydemo.Repository.UserRepository;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    private String signUp(){
        return "registration";
    }

    @PostMapping("/signup")
    private String addUser(User user, Model model){
        User userFromDB = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if(userFromDB != null){
            model.addAttribute("registered","User with this Username or Email already registered");
            return "registration";
        }
        user.setStatus(Status.UNCONFIRMED);
        user.setRoleSet(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";
    }

//    @GetMapping(value="/varification_token/{token}")
//    public String verificationToken(@PathVariable("token") String token){
//
//        return "redirect:/home";
//    }
}
