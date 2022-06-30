package com.ved.backend.integration.student.report;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ved.backend.model.ReasonReport;
import com.ved.backend.repo.ReasonReportRepo;
import com.ved.backend.util.MockDatabase;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class GetReasonReportsIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockDatabase mockDatabase;

    @Autowired
    private ReasonReportRepo reasonReportRepo;

    @Test
    @Order(1)
    public void init() throws Exception {
        mockDatabase.clear();
        mockDatabase.mock_reason_report();
    }

    @Test
    @Order(2)
    public void given_whenSuccess_thenReturnOkStatusAndListReasonReportResponse() throws Exception {
        // given
        List<ReasonReport> reasonReports = reasonReportRepo.findAll();

        // when
        ResultActions resultActions = mockMvc.perform(
            get("/api/students/reason-reports")
        );

        // then
        for (int i = 0; i < reasonReports.size(); ++i) {
            resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(reasonReports.size())))
                .andExpect(jsonPath("$[" + i + "].id").value(reasonReports.get(i).getId()))
                .andExpect(jsonPath("$[" + i + "].description").value(reasonReports.get(i).getDescription()));
        }
    }

    @Test
    @Order(3)
    public void clear() {
        mockDatabase.clear();
    }

}