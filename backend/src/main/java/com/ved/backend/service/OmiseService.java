package com.ved.backend.service;

import com.ved.backend.storeClass.Finance;

public interface OmiseService {
    String createRecipient(Finance finance);
    String verifyRecipient(String recipientId);
}