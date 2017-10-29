package com.ionut.ciuta.sci1hw.controller;

import com.ionut.ciuta.sci1hw.exception.ExceptionWithStatusCode;
import com.ionut.ciuta.sci1hw.service.ResourceAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ionutciuta24@gmail.com on 26.10.2017.
 */
@Controller
@RequestMapping("sci/hw/resource/")
public class ResourceController {
    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceAccessService resourceAccessService;

    @PostMapping("{user}/create")
    @ResponseBody
    public ResponseEntity<Void> createResource(@PathVariable String user,
                                               @RequestBody String pass,
                                               @RequestParam String name,
                                               @RequestParam(required = false) String value,
                                               @RequestParam Integer type) {
        return null;
    }

    @PostMapping("{user}/read")
    @ResponseBody
    public ResponseEntity<String> readResource(@PathVariable String user,
                                               @RequestBody String pass,
                                               @RequestParam String name) {
        try {
            return ResponseEntity.ok(resourceAccessService.read(user, pass, name));
        } catch (ExceptionWithStatusCode e) {
            log.error(
                    "readResource failed with {} for {} {} {}",
                    e.getClass().getSimpleName(), user, pass, name);
            return new ResponseEntity<>(e.status);
        }
    }

    @PostMapping("{user}/write")
    @ResponseBody
    public ResponseEntity<Void> writeResource(@PathVariable String user,
                                              @RequestBody String pass,
                                              @RequestParam String name,
                                              @RequestParam String value) {
        try {
            resourceAccessService.write(user, pass, name, value);
            return ResponseEntity.ok().build();
        } catch (ExceptionWithStatusCode e) {
            log.error(
                    "writeResource failed with {} for {} {} {}",
                    e.getClass().getSimpleName(), user, pass, name, value);
            return new ResponseEntity<>(e.status);
        }
    }

    @PostMapping("{user}/rights")
    @ResponseBody
    public ResponseEntity<Void> changeRights(@PathVariable String user,
                                             @RequestBody String pass,
                                             @RequestParam String name,
                                             @RequestParam String rights) {
        try {
            resourceAccessService.changeRights(user, pass, name, rights);
            return ResponseEntity.ok().build();
        } catch (ExceptionWithStatusCode e) {
            log.error(
                    "changeRights failed with {} for {} {} {} {}",
                    e.getClass().getSimpleName(), user, pass, name, rights);
            return new ResponseEntity<>(e.status);
        }
    }
}
