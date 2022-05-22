package com.ved.backend.storeClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Finance {
    private String bankBrand;
    private String bankAccountName;
    private String bankAccountNumber;
    private String recipientName;
    private String type;

}
