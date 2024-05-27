package com.hello.macaronsbe;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class FlavourAvailability {
    private Long id;
    private Flavour flavour;
    private int amount;
    private LocalDate date;
    private int expirationDays;
}
