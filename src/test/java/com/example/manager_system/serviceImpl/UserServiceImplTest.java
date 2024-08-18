package com.example.manager_system.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.List;


class UserServiceImplTest {

    @Mock
    private Resource accessFilePath;

    @InjectMocks
    private UserServiceImpl userService;

    private File tempFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        userService.accessFilePath = accessFilePath;
    }

    @Test
    public void testGetResources_UserHasAccess() throws IOException {
        String userId = "user1";

        tempFile = File.createTempFile("testfile", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("user1:resource1,resource2,resource3\n");
        }

        when(accessFilePath.getFilename()).thenReturn(tempFile.getAbsolutePath());

        List<String> resources = userService.getResources(userId);

        assertEquals(3, resources.size());
        assertEquals("resource1", resources.get(0));
        assertEquals("resource2", resources.get(1));
        assertEquals("resource3", resources.get(2));
    }

    @Test
    public void testGetResources_UserNoAccess() throws IOException {
        String userId = "user2";
        tempFile = File.createTempFile("testfile", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("user1:resource1,resource2,resource3\n");
        }
        when(accessFilePath.getFilename()).thenReturn(tempFile.getAbsolutePath());
        List<String> resources = userService.getResources(userId);

        assertEquals(0, resources.size());
    }

    @Test
    public void testGetResources_FileNotFound() throws IOException {
        String userId = "user1";

        when(accessFilePath.getFilename()).thenReturn("nonexistentfile.txt");

        List<String> resources = userService.getResources(userId);

        assertEquals(0, resources.size());
    }
}