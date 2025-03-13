package by.yurhilevich.WebApp.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    @Column(name = "purchase_price", nullable = true)
    private Double purchasePrice;

    @Column(name = "selling_price", nullable = true)
    private Double sellingPrice;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "FKProductID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "FKEmployeeID")
    private Employee employee;
}