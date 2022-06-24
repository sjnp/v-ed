package com.ved.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ved.backend.repo.ReasonReportRepo;
import com.ved.backend.response.ReasonReportResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReportService {

    private final ReasonReportRepo reasonReportRepo;

    public List<ReasonReportResponse> getReasonReports() {
        return reasonReportRepo
            .findAll()
            .stream()
            .map(reasonReport -> new ReasonReportResponse(reasonReport))
            .collect(Collectors.toList());
    }

}