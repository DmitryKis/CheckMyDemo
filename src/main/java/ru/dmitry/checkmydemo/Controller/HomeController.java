package ru.dmitry.checkmydemo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Model model){
        return "home";
    }


    @GetMapping("/hello")
    public String helloPage(Model model){
        return "hello";
    }
}
