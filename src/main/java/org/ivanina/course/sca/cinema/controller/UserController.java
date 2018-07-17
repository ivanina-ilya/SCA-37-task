package org.ivanina.course.sca.cinema.controller;

import org.ivanina.course.sca.cinema.domain.Ticket;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.BookingService;
import org.ivanina.course.sca.cinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/list")
    public String getIndex(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("usersList", userService.getAll());
        return "user/userList";
    }

    @GetMapping("/view/{id}")
    public ModelAndView getUser(
            @PathVariable Long id) {
        ModelAndView model = new ModelAndView("user/userView");
        model.addObject("user", userService.get(id));
        return model;
    }

    @RequestMapping(value = "/tickets/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getUserTickets(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "preBookTicketId", required = false) Long preBookTicketId)
    {
        ModelAndView model = new ModelAndView("user/userTickets");

        if(preBookTicketId != null){
            bookingService.convertPreBookingToTicket(preBookTicketId);
        }

        User user = userService.get(userId);
        model.addObject("tickets", bookingService.getTicketsByUser( user ));
        model.addObject("user", user );

        return model;
    }
}
