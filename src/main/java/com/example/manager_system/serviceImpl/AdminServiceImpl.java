package com.example.manager_system.serviceImpl;

import com.example.manager_system.constant.Constant;
import com.example.manager_system.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class AdminServiceImpl implements AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
    @Value("${app.accessfile.path}")
    Resource accessFilePath;

    @Override
    public String addUser(String userId, List<String> resources) throws IOException {
        RandomAccessFile file = null;
        try {
            File f = new File(Objects.requireNonNull(accessFilePath.getFilename()));
            if (!f.exists()) {
                f.createNewFile();
            }
            file = new RandomAccessFile(accessFilePath.getFilename(), "rw");
            String line;
            while ((line = file.readLine()) != null) {
                if(!line.isEmpty()) {
                    String currentUid = line.substring(0, line.indexOf("->"));
                    if (currentUid.equals(userId)) {
                        return Constant.PERMISSON_ALREADY_EXISTS;
                    }
                }
            }
            // User not exists in current file
            StringBuilder newUser = new StringBuilder(userId + "->user:");
            for (String s : resources) {
                newUser.append(s).append(",");
            }
            newUser.append("\n");
            file.writeBytes(newUser.toString());
            file.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            return Constant.FAILURE;
        } finally {
            if(file != null)
                file.close();
        }
        return Constant.SUCCESS;
    }
}
