package com.example.manager_system.serviceImpl;

import com.example.manager_system.constant.Constant;
import com.example.manager_system.service.UserService;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${app.accessfile.path}")
    Resource accessFilePath;

    @Override
    public List<String> getResources(String userId) throws IOException {
        List<String> res = new ArrayList<>();
        //read access from file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Objects.requireNonNull(accessFilePath.getFilename())));
            String line;
            while((line = reader.readLine()) != null)
            {
                if(line.startsWith(userId))
                {
                    String accessResources = line.substring(line.indexOf(":")+1);
                    String[] resourceArray = accessResources.split(",");
//                    Arrays.stream(resourceArray).forEach(System.out::println);
                    res.addAll(Arrays.asList(resourceArray));
                }
            }
        }
        catch (FileNotFoundException e) {
            log.error("Access file not found!");
              return List.of();
            }

        return res;
        }
    }
