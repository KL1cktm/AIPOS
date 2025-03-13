package by.yurhilevich.WebApp.dto;

import by.yurhilevich.WebApp.models.Employee;
import by.yurhilevich.WebApp.models.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
