package com.ved.backend.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ChargeResponse {
    private String id;
    private String authorizeUri;
    private boolean payState;
}
