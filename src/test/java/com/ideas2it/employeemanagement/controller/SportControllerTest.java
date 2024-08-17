package com.ideas2it.employeemanagement.controller;

import com.ideas2it.employeemanagement.sport.controller.SportController;
import com.ideas2it.employeemanagement.sport.service.SportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SportController.class)
public class SportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SportService sportService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addSport_InValidName_ThrowsException() throws Exception {
        String invalidSportJson = "{\"id\":2,\"name\":\"123456\"}";

        mockMvc.perform(post("/api/v1/sports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidSportJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addSport_EmptyName_ThrowsException() throws Exception {
        String invalidSportJson = "{\"id\":2,\"name\":\"\"}";

        mockMvc.perform(post("/api/v1/sports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidSportJson))
                .andExpect(status().isBadRequest());
    }
}
