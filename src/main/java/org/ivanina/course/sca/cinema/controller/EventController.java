package org.ivanina.course.sca.cinema.controller;

import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list/")
    public String getList(
            @ModelAttribute("model")ModelMap model
            ){

        Set<EventSchedule> eventScheduleSet =  eventService.getAvailableEventSchedule();

        Map<Event, Set<EventSchedule>> modelMap = eventScheduleSet.stream()
                .collect(Collectors.toMap(
                        EventSchedule::getEvent,p -> p, (p, q) -> p ))
                .entrySet().stream()
                .collect(Collectors.toMap(el -> el.getKey(), el -> eventScheduleSet.stream()
                        .filter(e -> e.getEvent().equals(el.getKey()))
                        .collect(Collectors.toSet())));
        model.addAttribute("evenModelList", modelMap);
        return "event/eventList";
    }

    @GetMapping("/archive")
    public String getArchive(
            @ModelAttribute("model")ModelMap model
    ){
        model.addAttribute("eventList", eventService.getArchiveEvents());
        return "event/eventArchive";
    }

    @GetMapping("/view/{id}")
    public String getEvent(
            @ModelAttribute("model")ModelMap model,
            @PathVariable Long id
    ){
        model.addAttribute("event", eventService.get(id));
        return "event/eventView";
    }
}
