package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Combine;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombineService {
    List<String> getAllCombines();

    boolean addCombine(String combineName, String address, String phone, String regionName);

    List<Combine> getAll();

    boolean updateCombine(Long id, String combineName, String address, String phone, String regionName);
    void deleteCombine(Long id);
}
