package org.ivanina.course.sca.cinema.controller.admin;

import org.ivanina.course.sca.cinema.service.EventService;
import org.ivanina.course.sca.cinema.service.UserService;
import org.ivanina.course.sca.cinema.utils.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/upload")
public class UploadDataController {
    private String baseVewPath = "admin";

    @Autowired
    private Serializer serializer;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String uploadUsers(){
        return baseVewPath+"/upload";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String uploadUsers(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", required = true) String type
    ){
        if(file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        } else {
            try {
                switch (type){
                    case "user":
                    case "users":
                        userService.updateOrInsertUsers(serializer.deSerializeUsers(file.getInputStream()));
                        break;
                    case "event":
                    case "events":
                        eventService.updateOrInsertEvents(serializer.deSerializeEvent(file.getInputStream()));
                        break;

                    default: throw new IllegalArgumentException("The type of uploaded data have not specified");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baseVewPath+"/uploadSuccess";
    }
}
