package com.ved.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ved.backend.repo.ReasonReportRepo;
import com.ved.backend.request.ReportRequest;
import com.ved.backend.response.ReasonReportResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {

    private final ReasonReportRepo reasonReportRepo;

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    public List<ReasonReportResponse> getReasonReports() {
        return reasonReportRepo
            .findAll()
            .stream()
            .map(reasonReport -> new ReasonReportResponse(reasonReport))
            .collect(Collectors.toList());
    }

    public void createReport(String username, ReportRequest reportRequest) {
        log.info("username: {} report {} id {}",username, reportRequest.getReportType(), reportRequest.getContentId());
    }

    public void reportReview(String username, ReportRequest reportRequest) {

    }

    public void reportPost(String username, ReportRequest reportRequest) {

    }

    public void reportComment(String username, ReportRequest reportRequest) {

    }

}