package com.ved.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
public class OverviewControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void givenCategoryName_whenFoundAndHaveData_thenReturnCourseCardResponseList() {
   
    }

    @Test
    @Transactional
    public void givenCategoryName_whenFoundButNoHaveData_thenReturnEmptyCourseCardResponseList() {
        
    }

    @Test
    @Transactional
    public void givenCategoryOther_whenNotFound_thenReturnNotFoundException() {
        
    }

}