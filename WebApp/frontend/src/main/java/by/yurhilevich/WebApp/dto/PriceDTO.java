package by.yurhilevich.WebApp.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PriceDTO {
    private Long priceId;
    private Double purchasePrice;
    private Double sellingPrice;
    private LocalDate date;
    private String product;
    private String employee;
}
