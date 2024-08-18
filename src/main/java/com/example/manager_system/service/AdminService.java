package com.example.manager_system.service;

import com.example.manager_system.pojo.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public interface AdminService {
    String addUser(String userId, List<String> resources) throws IOException;
}
