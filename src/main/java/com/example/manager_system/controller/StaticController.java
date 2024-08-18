package com.example.manager_system.controller;

import com.example.manager_system.constant.Constant;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/exception")
public class StaticController {

    @RequestMapping(value = "/permission_denied")
    public String permissionDenied() {
        return Constant.PERMISSON_DENIED;
    }

    @RequestMapping(value = "/permission_already_exists")
    public String permissionExists() {
        return Constant.PERMISSON_ALREADY_EXISTS;
    }

    @RequestMapping(value = "/system_error")
    public String systemError(@RequestParam("code") String errorCode) {
        String errMessage;
        if ("404".equals(errorCode)) {
            errMessage = Constant.NOT_FOUND;
        } else if ("400".equals(errorCode)) {
            errMessage = Constant.BAD_REQUEST;
        } else {
            errMessage = Constant.SERVER_ERROR;
        }
        return errMessage;
    }

}
