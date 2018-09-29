package org.ivanina.course.sca.cinema.controller.pdf;

import org.ivanina.course.sca.cinema.model.pdf.EventsPDFView;
import org.ivanina.course.sca.cinema.model.pdf.UsersPDFView;
import org.ivanina.course.sca.cinema.service.EventService;
import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PDFController {
    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    UsersPDFView usersPDFView;

    @Autowired
    EventsPDFView eventsPDFView;


    @GetMapping("/pdf/list/{type}/")
    public ModelAndView getUserList(
            @PathVariable("type") String type
    ) {
        switch (type) {
            case "user":
            case "users":
                return new ModelAndView(usersPDFView, "data", userService.getAll());
            case "event":
            case "events":
                return new ModelAndView(eventsPDFView, "data", eventService.getAll());

            default:
                throw new IllegalArgumentException("Undefined type");
        }
    }

    @GetMapping("/pdf/downloads/")
    public String indexPage() {
        return "pdf/index";
    }
}
