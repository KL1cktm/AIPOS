package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Region;
import by.yurhilevich.WebApp.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<String> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        List<String> regionNames = regions.stream()
                .map(Region::getName)
                .collect(Collectors.toList());

        return regionNames;
    }
}
