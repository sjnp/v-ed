package com.ved.backend.controller;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.transaction.annotation.Transactional;

// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-it.properties")
public class OverviewControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void name() {
        
    }

    // @Test
    // // @Order(1)
    // @Transactional
    // public void givenCategoryName_whenFoundAndHaveData_thenReturnCourseCardResponseList() {
    //     // given
    //     String categoryName = "ART";
    //     // when
    //     // ResultActions resultActions = mockMvc.perform( 
    //     //     get("/api/overview/category/{categoryName}", categoryName)
    //     //         .accept(MediaType.APPLICATION_JSON)
    //     // )
    //     //     .andDo(print())
    //     //     .andExpect(status().isOk());
    //     // then
    //     // resultActions.andExpect(status().isCreated());


    //     // ResultActions resultActions = mockMvc.perform(
    //     //     get("/api/overview/category/{categoryName}", categoryName)
    //     // ).andExpect(MockMvcResultMatchers.status().isOk());


    //     // ResultActions resultActions = mockMvc.perform(
    //     //     get("/api/overview/category/{categoryName}" + categoryName)
    //     // ).andDo(print())
    //     //     .andExpect(status()
    //     //     .isOk());
    //     // .andExpect(content().string(containsString("Hello, Mock"))

    //             // get("/greeting")).andDo(print()).andExpect(status().isOk())
	// 			// .andExpect(content().string(containsString("Hello, Mock"))
    //     // );
    // }

    // @Test
    // // @Order(2)
    // public void givenCategoryName_whenFoundButNoHaveData_thenReturnEmptyCourseCardResponseList() {
        
    // }

    // @Test
    // // @Order(3)
    // public void givenCategoryOther_whenNotFound_thenReturnNotFoundException() {
        
    // }

}