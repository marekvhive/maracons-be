package com.hello.macaronsbe;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Flavour {
    private Long id;
    private String name;
    private String description;
    private String image;  // Assuming the image is stored as a Base64 encoded string
}
