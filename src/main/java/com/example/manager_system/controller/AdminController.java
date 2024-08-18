package com.example.manager_system.controller;

import com.example.manager_system.constant.Constant;
import com.example.manager_system.pojo.Resource;
import com.example.manager_system.pojo.UserAccess;
import com.example.manager_system.service.AdminService;
import com.example.manager_system.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/admin", method = POST)
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    Util util;

    @RequestMapping(value = "/addUser")
    public ResponseEntity<Object> addUser(@RequestBody UserAccess userAccess, @RequestHeader("Authorization") String base64Header) throws IOException {
        String decodedHeader = util.decoder(base64Header);
        String userRole = util.retrieveUserRole(decodedHeader);
        String userId = userAccess.getUserId();

        List<String> resources = userAccess.getEndpoint();
        HttpHeaders headers = new HttpHeaders();
        if(!Constant.ADMIN.equals(userRole))
        {
            headers.setLocation(URI.create("/exception/permission_denied"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        String res = adminService.addUser(userId, resources);
        if(res.equals(Constant.PERMISSON_ALREADY_EXISTS))
        {
            headers.setLocation(URI.create("/exception/permission_already_exists"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        if(res.equals(Constant.FAILURE))
        {
            headers.setLocation(URI.create("/exception/system_error"));
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }

        else
            return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
    }
}
