package com.example.manager_system.service;

import com.example.manager_system.pojo.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    public List<String> getResources(String userId) throws IOException;
}
