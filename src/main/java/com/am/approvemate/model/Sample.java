package com.am.approvemate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sample {
    @Id
    private String productId;

    private String productName;
    private String category;
    private String manufacturer;
    private LocalDate manufactureDate;
    private double price;
    private boolean inStock;
}
