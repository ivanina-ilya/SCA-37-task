package org.ivanina.course.sca.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class TestController {

    @RequestMapping(value = "/testUpload", method = RequestMethod.POST)
    public String testUpload() {
        return "Ok";
    }

    @RequestMapping(value = "/testUploadFile", method = RequestMethod.POST)
    public String testUploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        return "Ok";
    }

    @RequestMapping(value = "/testUpload", method = RequestMethod.GET)
    public String uploadUsers() {
        return "testUpload";
    }
}
