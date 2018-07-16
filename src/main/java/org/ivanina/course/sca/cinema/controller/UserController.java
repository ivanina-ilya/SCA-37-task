package org.ivanina.course.sca.cinema.controller;

import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String index(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("usersList", userService.getAll());
        return "user/list";
    }

    @GetMapping("/view/{id}")
    public String index(
            @ModelAttribute("model") ModelMap model,
            @PathVariable Long id) {
        model.addAttribute("user", userService.get(id));
        return "user/view";
    }
}
