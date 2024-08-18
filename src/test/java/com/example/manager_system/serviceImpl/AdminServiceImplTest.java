package com.example.manager_system.serviceImpl;

import com.example.manager_system.constant.Constant;
import com.example.manager_system.serviceImpl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private Resource accessFilePath;

    @Mock
    private File mockFile;

    private File tempFile;
    @Mock
    private RandomAccessFile mockRandomAccessFile;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService.accessFilePath = accessFilePath;
    }

    @Test
    public void testAddUser_UserAlreadyExists() throws IOException {
        String userId = "user1";
        List<String> resources = Arrays.asList("resource1", "resource2");

        tempFile = File.createTempFile("testfile", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("user1->user:resource1,resource2,resource3\n");
        }
        when(accessFilePath.getFilename()).thenReturn(tempFile.getAbsolutePath());
        String result = adminService.addUser(userId, resources);

        assertEquals(Constant.PERMISSON_ALREADY_EXISTS, result);

    }

    @Test
    public void testAddUser_UserAddedSuccessfully() throws IOException {
        String userId = "user2";
        List<String> resources = Arrays.asList("resource1", "resource2");

        tempFile = File.createTempFile("testfile", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("user1->user:resource1,resource2,resource3\n");
        }
        when(accessFilePath.getFilename()).thenReturn(tempFile.getAbsolutePath());
        String result = adminService.addUser(userId, resources);

        assertEquals(Constant.SUCCESS, result);
        verify(mockRandomAccessFile, times(0)).writeBytes(eq("user2->user:resource1,resource2,\n"));
        verify(mockRandomAccessFile, times(0)).close();
    }

    @Test
    public void testAddUser_FileIOException() throws IOException {
        String userId = "user3";
        List<String> resources = Arrays.asList("resource1", "resource2");

        when(accessFilePath.getFilename()).thenReturn("nonexistentfile.txt");

        String result = adminService.addUser(userId, resources);

        assertEquals(Constant.SUCCESS, result);
        }
}

