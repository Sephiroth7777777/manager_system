package com.example.manager_system.controller;

import com.example.manager_system.pojo.Resource;
import com.example.manager_system.service.UserService;
import com.example.manager_system.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/user", method = POST)
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    Util util;

    @RequestMapping(value = "{resource}")
    public ResponseEntity<Object> getResources(@PathVariable String resource,
                                               @RequestHeader("Authorization") String base64Header) throws IOException {

        String decodedHeader = util.decoder(base64Header);

        String userId = util.retrieveUserId(decodedHeader);
        List<String> res = userService.getResources(userId);

        if(res.isEmpty() || !res.contains(resource))
        {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/exception/permission_denied"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        else
            return new ResponseEntity<>(new Resource(resource), HttpStatus.OK);
    }
}
