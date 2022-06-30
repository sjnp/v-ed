package com.ved.backend.response;

import com.ved.backend.model.ReasonReport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReasonReportResponse {

    private Long id;

    private String description;

    public ReasonReportResponse(ReasonReport reasonReport) {
        this.id = reasonReport.getId();
        this.description = reasonReport.getDescription();
    }
    
}