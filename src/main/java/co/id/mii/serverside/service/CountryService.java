/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Country;
import co.id.mii.serverside.model.dto.CountryData;
import co.id.mii.serverside.repository.CountryRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author RAI
 */
@Service
public class CountryService {
    
    private CountryRepository countryRepository;
    private RegionService regionService;
    private ModelMapper modelMapper;

    @Autowired
    public CountryService(CountryRepository countryRepository, RegionService regionService, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }
    
    public List<Country> getAll() {
        return countryRepository.findAll();
    }
    
    public Country getById(Long id) {
        return countryRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not Found")
        );
    }
    
    public Country create(CountryData countryData) {
//        if (country.getId() != null) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country id already exist");
//        }
        if (countryRepository.findByCode(countryData.getCode()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country code already exist");
        }
        if (countryRepository.findByName(countryData.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country Name already exist");
        }
        
//        Country country = new Country();
//        country.setCode(countryData.getCode());
//        country.setName(countryData.getName());
        
        Country country = modelMapper.map(countryData, Country.class);
        country.setRegion(regionService.getById(countryData.getRegionId()));
        
        return countryRepository.save(country);
    }

    public Country update(Long id, Country country) {
        Country getCountry = getById(id);
        if (!getCountry.getCode().equalsIgnoreCase(country.getCode())) {
            if (countryRepository.findByCode(country.getCode()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Update Failed: Country code already exist!");
            }
        }

        if (getCountry.getName().equalsIgnoreCase(country.getCode())) {
            if (countryRepository.findByName(country.getName()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Update Failed: Country name already exist!");
            }
        }
        country.setId(id);
        return countryRepository.save(country);
    }

    public Country delete(Long id) {
        Country country = getById(id);
        countryRepository.delete(country);
        return country;
    }
}
