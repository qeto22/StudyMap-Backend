package com.kbach19.studymap.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyRequest {

    private List<Long> courseIds;

    private List<Long> mentorshipRequestIds;

    private String cardHolder;

    private String cardNumber;

    private String expirationDate;

    private String cvv;

}
