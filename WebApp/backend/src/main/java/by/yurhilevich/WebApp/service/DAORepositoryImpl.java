package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.repository.DAORepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DAORepositoryImpl implements DAORepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Object[]> getAvgPrices(Long productId, Date targetDate) {
        String sql = "{CALL price_accounting.get_avg_prices(?, ?)}";

        return jdbcTemplate.query(sql, new Object[]{productId, targetDate}, (rs, rowNum) -> new Object[] {
                rs.getDouble("avg_selling_price"),  // Средняя отпускная цена
                rs.getDouble("avg_purchase_price"), // Средняя закупочная цена
                rs.getString("region_name")         // Название региона
        });
    }

    @Override
    public List<Object[]> getDynamicPrices(String productName, LocalDate date1, LocalDate date2) {
//        String sql = "SELECT " +
//                "    p.name AS product_name, " +
//                "    pr.date AS date, " +
//                "    pr.purchase_price AS purchase_price, " +
//                "    pr.selling_price AS selling_price, " +
//                "    e.fio AS employee, " +
//                "    r.name AS region_name " +
//                "FROM " +
//                "    price pr " +
//                "JOIN " +
//                "    product p ON pr.fkproductid = p.product_id " +
//                "JOIN " +
//                "    employee e ON pr.fkemployeeid = e.employee_id " +
//                "JOIN " +
//                "    combine c ON e.fkcombineid = c.combine_id " +
//                "JOIN " +
//                "    region r ON r.region_id = c.fkregionid " +
//                "WHERE " +
//                "    p.name = ? " +
//                "    AND pr.date BETWEEN ? AND ? " +
//                "ORDER BY " +
//                "    pr.date;";

        String sql = "SELECT " +
                "    p.name AS product_name, " +
                "    pr.date AS date, " +
                "    pr.purchase_price AS purchase_price, " +
                "    pr.selling_price AS selling_price, " +
                "    e.fio AS employee, " +
                "    r.name AS region_name " +
                "FROM " +
                "    price pr " +
                "JOIN " +
                "    product p ON pr.fkproductid = p.product_id " +
                "JOIN " +
                "    employee e ON pr.fkemployeeid = e.employee_id " +
                "JOIN " +
                "    combine c ON e.fkcombineid = c.combine_id " +
                "JOIN " +
                "    region r ON r.region_id = c.fkregionid " +
                "WHERE " +
                "    p.name = ? " +
                "    AND (" +
                "        pr.date BETWEEN ? AND ? " +
                "        OR pr.date = (SELECT MAX(pr2.date) " +
                "                     FROM price pr2 " +
                "                     WHERE pr2.fkproductid = pr.fkproductid " +
                "                       AND pr2.date <= ?) " +
                "    ) " +
                "ORDER BY pr.date;";





        return jdbcTemplate.query(sql, new Object[]{productName, date1, date2, date1}, (rs, rowNum) -> new Object[] {
                rs.getString("product_name"),
                rs.getDate("date"),
                rs.getDouble("purchase_price"),
                rs.getDouble("selling_price"),
                rs.getString("employee"),
                rs.getString("region_name")
        });

    }

    public List<Object[]> getPricesWithCombines(LocalDate date) {
//        String sql = "SELECT " +
//                "    p.name AS product_name, " +
//                "    pr.date AS date, " +
//                "    pr.purchase_price AS purchase_price, " +
//                "    pr.selling_price AS selling_price, " +
//                "    c.name AS combine_name " +
//                "FROM " +
//                "    price pr " +
//                "JOIN " +
//                "    product p ON pr.fkproductid = p.product_id " +
//                "JOIN " +
//                "    employee e ON pr.fkemployeeid = e.employee_id " +
//                "JOIN " +
//                "    combine c ON e.fkcombineid = c.combine_id " +
//                "WHERE " +
//                "    pr.date = ? " +
//                "ORDER BY " +
//                "    pr.date; ";
//
//        return jdbcTemplate.query(sql, new Object[]{date}, (rs, rowNum) -> new Object[] {
//                rs.getString("product_name"),
//                rs.getDate("date"),
//                rs.getDouble("purchase_price"),
//                rs.getDouble("selling_price"),
//                rs.getString("combine_name")
//        });
        String sql = "SELECT GetPricesOnDate(?) AS result";

        return jdbcTemplate.queryForObject(sql, new Object[]{date}, (rs, rowNum) -> {
            String jsonResult = rs.getString("result");

            // Проверка, что JSON результат не пустой или null
            if (jsonResult == null || jsonResult.isEmpty()) {
                return null;
            }

            JSONArray jsonArray = new JSONArray(jsonResult);
            List<Object[]> resultList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Добавляем значения в массив, проверяя каждый ключ на существование и значение на null
                String productName = jsonObject.optString("product_name", "");
                String dateStr = jsonObject.optString("date", "");
                double purchasePrice = jsonObject.has("purchase_price") ? jsonObject.optDouble("purchase_price") : 0.0;
                double sellingPrice = jsonObject.has("selling_price") ? jsonObject.optDouble("selling_price") : 0.0;
                String combineName = jsonObject.optString("combine_name", "");

                resultList.add(new Object[] { productName, dateStr, purchasePrice, sellingPrice, combineName });
            }

            return resultList;
        });


    }

    public List<Object[]> getDiffPrices(){
        String sql = "SELECT " +
                "    p.name AS product_name," +
                "    pr.date AS date, " +
                "    ROUND(pr.selling_price - pr.purchase_price, 2) AS price_difference, " +
                "    e.fio AS employee_name, " +
                "    c.name AS combine_name " +
                "FROM " +
                "    price pr " +
                "JOIN " +
                "    product p ON pr.fkproductid = p.product_id " +
                "JOIN " +
                "    employee e ON pr.fkemployeeid = e.employee_id " +
                "JOIN " +
                "    combine c ON e.fkcombineid = c.combine_id " +
                "ORDER BY " +
                "    p.name;";


        return jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) -> new Object[] {
                rs.getString("product_name"),
                rs.getDate("date"),
                rs.getDouble("price_difference"),
                rs.getString("employee_name"),
                rs.getString("combine_name")
        });

    }
}
