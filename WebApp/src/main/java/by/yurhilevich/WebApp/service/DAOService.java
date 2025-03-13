package by.yurhilevich.WebApp.service;


import by.yurhilevich.WebApp.models.Price;
import by.yurhilevich.WebApp.models.Product;
import by.yurhilevich.WebApp.repository.DAORepository;
import by.yurhilevich.WebApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DAOService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DAORepository daoRepository;

    public List<Object[]> getAveragePricesFromRegions() {
        String sql = "SELECT AVG(price.selling_price) AS avg_selling_price, AVG(price.purchase_price) AS avg_purchase_price, r.name " +
                "FROM price " +
                "JOIN combine c ON price.fkcombineid = c.combine_id " +
                "JOIN region r ON c.fkregionid = r.region_id " +
                "GROUP BY r.name";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Object[] {
                rs.getDouble("avg_selling_price"),
                rs.getDouble("avg_purchase_price"),
                rs.getString("name")
        });
    }

    public List<Object[]> getPricesByDate( LocalDate date, String productName ) {
        Long productId = productRepository.findProductByName(productName).get().getProductId();
        List<Object[]> result = daoRepository.getAvgPrices(productId,Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return result;
    }

    public List<Object[]> getDynamicPrices( LocalDate date1, LocalDate date2, String productName ) {
        List<Object[]> result;
        if (date2.compareTo(date1) >= 0) {
            result = daoRepository.getDynamicPrices(productName,date1,date2);
        } else {
            result = daoRepository.getDynamicPrices(productName,date2,date1);
        }
        return result;
    }

    public List<Object[]> getPriceWithCombines(LocalDate date) {
        return daoRepository.getPricesWithCombines(date);
    }

    public List<Object[]> getDiffPrices() {
        return daoRepository.getDiffPrices();
    }
}
