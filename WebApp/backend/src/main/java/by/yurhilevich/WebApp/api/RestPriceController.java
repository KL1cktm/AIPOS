package by.yurhilevich.WebApp.api;

import by.yurhilevich.WebApp.dto.PriceDTO;
import by.yurhilevich.WebApp.models.Price;
import by.yurhilevich.WebApp.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestPriceController {

    @Autowired
    PriceService priceService;

    @PostMapping("/add_prices")
    public ResponseEntity<String> createPrice(@RequestBody PriceDTO priceDTO) {
        log.info("Received request to create price: {}", priceDTO);

        // Сохраняем продукт в базе данных
        boolean flag = priceService.addPrice(priceDTO.getPurchasePrice(),priceDTO.getSellingPrice(),priceDTO.getProduct(),priceDTO.getEmployee(),priceDTO.getDate());

        if (!flag) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Price doesn't create");
        }


        log.info("Price created successfully");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Price created successfully");
    }

    @PutMapping("/updatePrices")
    public ResponseEntity<String> updatePrice(@RequestBody PriceDTO priceDTO) {
        log.info("Received request to update price: {}", priceDTO);

        boolean flag = priceService.updatePrice(priceDTO.getPriceId(),priceDTO.getPurchasePrice(),priceDTO.getSellingPrice(),priceDTO.getDate(),priceDTO.getProduct(),priceDTO.getEmployee());
        if (!flag) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Price created successfully");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("Price created successfully");
    }

    @GetMapping("/get_prices_objects")
    public ResponseEntity<List<PriceDTO>> getPricesObjects() {
        try {
            List<Price> prices = priceService.getAllPrices();
            List<PriceDTO> priceDTOList = new ArrayList<>();
            for (Price price : prices) {
                PriceDTO priceDTO = new PriceDTO();
                priceDTO.setPriceId(price.getPriceId());
                priceDTO.setPurchasePrice(price.getPurchasePrice());
                priceDTO.setSellingPrice(price.getSellingPrice());
                priceDTO.setDate(price.getDate());
                priceDTO.setEmployee(price.getEmployee().getFio());
                priceDTO.setProduct(price.getProduct().getName());
                priceDTOList.add(priceDTO);
            }
            if (prices.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(priceDTOList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // В случае ошибки возвращаем 500
        }
    }

    @DeleteMapping("/deletePrice")
    public ResponseEntity<String> deletePrice(@RequestBody PriceDTO priceDTO) {
        log.info("Received request to delete price: {}", priceDTO);
        try {
            priceService.deletePrice(priceDTO.getPriceId());
            return ResponseEntity.status(HttpStatus.OK).body("Price deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
