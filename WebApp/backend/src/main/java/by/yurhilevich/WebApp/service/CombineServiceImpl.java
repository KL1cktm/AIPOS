package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Combine;
import by.yurhilevich.WebApp.repository.CombineRepository;
import by.yurhilevich.WebApp.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CombineServiceImpl implements CombineService {

    @Autowired
    CombineRepository combineRepository;
    @Autowired
    RegionRepository regionRepository;

    @Override
    public List<String> getAllCombines() {
        List<Combine> combines = combineRepository.findAll();
        List<String> combineName = combines.stream()
                .map(Combine::getName)
                .collect(Collectors.toList());

        return combineName;
    }

    @Override
    public boolean addCombine(String combineName, String address, String phone, String regionName) {
        if (!combineRepository.existsByName(combineName) && regionRepository.existsByName(regionName)) {
            Combine combine = new Combine();
            combine.setName(combineName);
            combine.setAddress(address);
            combine.setPhone(phone);
            combine.setRegion(regionRepository.findByName(regionName).get());
            combineRepository.save(combine);
            return true;
        }
        return false;
    }

    @Override
    public List<Combine> getAll() {
        return combineRepository.findAll();
    }

    @Override
    public boolean updateCombine(Long id, String combineName, String address, String phone, String regionName) {
        if (combineRepository.existsById(id) && regionRepository.existsByName(regionName)) {
            Combine combine = combineRepository.findById(id).get();
            combine.setName(combineName);
            combine.setAddress(address);
            combine.setPhone(phone);
            combine.setRegion(regionRepository.findByName(regionName).get());
            combineRepository.save(combine);
            return true;
        }
        return false;
    }
    @Override
    public void deleteCombine(Long id) {
        if (combineRepository.existsById(id)) {
            combineRepository.deleteById(id);
        }
    }
}
