package com.example.manager_system.controller;

import com.example.manager_system.controller.UserController;
import com.example.manager_system.service.UserService;
import com.example.manager_system.util.Util;
import com.example.manager_system.pojo.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private Util util;

    @Test
    public void testGetResources_ResourceExists() throws Exception {
        String userId = "user1";
        String resource = "resource1";
        String base64Header = "encodedHeader";
        List<String> resources = Arrays.asList("resource1", "resource2");

        when(util.decoder(base64Header)).thenReturn("decodedHeader");
        when(util.retrieveUserId("decodedHeader")).thenReturn(userId);
        when(userService.getResources(userId)).thenReturn(resources);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/resource1")
                        .header("Authorization", base64Header))
                .andExpect(MockMvcResultMatchers.status().is(303));
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.content().json("{\"resource\":\"resource1\"}"));
    }

    @Test
    public void testGetResources_ResourceNotFound() throws Exception {
        String userId = "user2";
        String resource = "resource3";
        String base64Header = "encodedHeader";
        List<String> resources = Arrays.asList("resource1", "resource2");

        when(util.decoder(base64Header)).thenReturn("decodedHeader");
        when(util.retrieveUserId("decodedHeader")).thenReturn(userId);
        when(userService.getResources(userId)).thenReturn(resources);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/resource3")
                        .header("Authorization", base64Header))
                .andExpect(MockMvcResultMatchers.status().isSeeOther())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, "/exception/system_error?code=400"));
    }
}

