package com.ved.backend.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChargeDataRequest {
    private String amount;
    private String currency;
    private String type;
    private Long courseId;
    private String returnUri;
}
