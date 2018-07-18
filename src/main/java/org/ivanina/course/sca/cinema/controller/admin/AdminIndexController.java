package org.ivanina.course.sca.cinema.controller.admin;

import org.ivanina.course.sca.cinema.service.EventService;
import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String index(){
        return "admin/index";
    }


}
