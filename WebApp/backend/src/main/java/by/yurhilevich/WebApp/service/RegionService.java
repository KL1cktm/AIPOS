package by.yurhilevich.WebApp.service;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionService {
    List<String> getAllRegions();
}
