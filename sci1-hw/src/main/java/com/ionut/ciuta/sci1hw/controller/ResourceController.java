package com.ionut.ciuta.sci1hw.controller;

import com.ionut.ciuta.sci1hw.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Controller
@RequestMapping("sci/hw/resource/")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("{user}/create")
    public ResponseEntity<Void> createResource(@PathVariable String user,
                                               @RequestBody String pass,
                                               @RequestParam String name,
                                               @RequestParam(required = false) String value,
                                               @RequestParam Integer type
    ) {
        return null;
    }

    @PostMapping("{user}/read")
    public ResponseEntity<Void> readResource(@PathVariable String user,
                                             @RequestBody String pass,
                                             @RequestParam String name
    ) {
        return null;
    }

    @PostMapping("{user}/write")
    public ResponseEntity<Void> readResource(@PathVariable String user,
                                             @RequestBody String pass,
                                             @RequestParam String name,
                                             @RequestParam String value
    ) {
        return null;
    }

    @PostMapping("{user}/rights")
    public ResponseEntity<Void> changeRights(@PathVariable String user,
                                             @RequestBody String pass,
                                             @RequestParam String name,
                                             @RequestParam String rights
    ) {
        return null;
    }
}
