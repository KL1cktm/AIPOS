package by.yurhilevich.WebApp.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DAORepository {
    List<Object[]> getAvgPrices(Long productId, Date targetDate);
    List<Object[]> getDynamicPrices(String productName, LocalDate date1, LocalDate date2);
    List<Object[]> getPricesWithCombines(LocalDate date);
    List<Object[]> getDiffPrices();
}
