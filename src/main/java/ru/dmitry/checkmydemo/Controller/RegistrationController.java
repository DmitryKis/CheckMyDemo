package ru.dmitry.checkmydemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dmitry.checkmydemo.Entity.Role;
import ru.dmitry.checkmydemo.Entity.User;
import ru.dmitry.checkmydemo.Repository.UserRepository;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/signup")
    private String signUp(){
        return "registration";
    }

    @PostMapping("/signup")
    private String addUser(User user, Model model){
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if(userFromDB != null){
            model.addAttribute("registered","User with this username already registered");
            return "registration";
        }
        user.setActive(true);
        user.setRoleSet(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
