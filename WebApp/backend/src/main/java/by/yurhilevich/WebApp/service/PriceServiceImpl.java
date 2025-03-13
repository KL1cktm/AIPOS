package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Price;
import by.yurhilevich.WebApp.repository.EmployeeRepository;
import by.yurhilevich.WebApp.repository.PriceRepository;
import by.yurhilevich.WebApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private PriceRepository priceRepository;

    @Override
    public boolean addPrice(Double purchasePrice, Double sellingPrice, String productName, String employeeName, LocalDate date) {
        if (productRepository.existsProductByName(productName) && employeeRepository.existsByFio(employeeName)) {
            Price price = new Price();
            price.setPurchasePrice(purchasePrice);
            price.setSellingPrice(sellingPrice);
            price.setProduct(productRepository.findProductByName(productName).get());
            price.setEmployee(employeeRepository.findByFio(employeeName).get());
            if (date != null) {
                price.setDate(date);
                System.out.println("Date added");
            }
            List<Price> prices = priceRepository.findAll();
            if (date == null) {
                date = LocalDate.now();
            }
            System.out.println(date);
            for (Price p : prices) {
                if (p.getDate().toString().equals(date.toString()) && p.getProduct().getName().equals(productName) &&
                        p.getEmployee().getFio().equals(employeeName)) {
                    return false;
                }
            }
            priceRepository.save(price);
            return true;
        }
        return false;
    }

    @Override
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    @Override
    public boolean updatePrice(Long id, Double purchasePrice, Double sellingPrice, LocalDate date, String productName, String employeeFio) {
        if (productRepository.existsProductByName(productName) && employeeRepository.existsByFio(employeeFio) && priceRepository.existsById(id)) {
            List<Price> prices = priceRepository.findAll();
            for (Price p : prices) {
                if (p.getDate().toString().equals(date.toString()) && p.getProduct().getName().equals(productName) &&
                        p.getEmployee().getFio().equals(employeeFio)) {
                    System.out.println(p.getDate().toString());
                    System.out.println(date.toString());
                    System.out.println(productName);
                    System.out.println(employeeFio);
                    return false;
                }
            }
            Price price = priceRepository.findById(id).get();
            price.setPurchasePrice(purchasePrice);
            price.setSellingPrice(sellingPrice);
            price.setProduct(productRepository.findProductByName(productName).get());
            price.setEmployee(employeeRepository.findByFio(employeeFio).get());
            price.setDate(date);
            priceRepository.save(price);
            return true;
        }
        return false;
    }

    @Override
    public void deletePrice(Long id) {
        priceRepository.deleteById(id);
    }
}
