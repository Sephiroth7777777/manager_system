package com.example.manager_system.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAccess {
    private String userId;
    private List<String> endpoint;
}
