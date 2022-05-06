package com.ved.backend.storeClass;

import com.ved.backend.model.Category;
import com.ved.backend.model.Chapter;
import com.ved.backend.model.CourseState;
import com.ved.backend.model.Instructor;

import java.util.List;

public class Finance {
    private String bankBrand;
    private String bankAccountName;
    private String bankAccountNumber;
    private String recipientName;
    private String type;

    public String getBankBrand() {return bankBrand;}
    public void setBankBrand(String bankBrand) {this.bankBrand = bankBrand;}

    public String getBankAccountName() {return bankAccountName;}
    public void setBankAccountName(String bankAccountName) {this.bankAccountName = bankAccountName;}

    public String getBankAccountNumber() {return bankAccountNumber;}
    public void setBankAccountNumber(String bankAccountNumber) {this.bankAccountNumber = bankAccountNumber;}

    public String getRecipientName() {return recipientName;}
    public void setRecipientName(String recipientName) {this.recipientName = recipientName;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public Finance() {
    }

    public Finance(String bankBrand, String bankAccountName, String bankAccountNumber, String recipientName, String type) {
        this.bankBrand = bankBrand;
        this.bankAccountName = bankAccountName;
        this.bankAccountNumber = bankAccountNumber;
        this.recipientName = recipientName;
        this.type = type;

    }
}
