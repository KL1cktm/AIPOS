package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Price;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PriceService {
    boolean addPrice(Double purchasePrice, Double sellingPrice, String productName, String employeeName, LocalDate date);
    List<Price> getAllPrices();
    boolean updatePrice(Long id, Double purchasePrice, Double sellingPrice,  LocalDate date, String productName, String employeeName);
    void deletePrice(Long id);
}
