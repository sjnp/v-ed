package com.ved.backend.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinanceDataRequest {
    private String bankBrand;
    private String bankAccountName;
    private String bankAccountNumber;
    private String recipientName;
    private String type;

}
