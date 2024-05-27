package com.hello.macaronsbe;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private Flavour flavour;
    private int amount;
}
