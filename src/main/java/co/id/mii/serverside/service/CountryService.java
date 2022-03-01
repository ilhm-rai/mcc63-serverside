/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Country;
import co.id.mii.serverside.model.dto.CountryDto;
import co.id.mii.serverside.repository.CountryRepository;
import java.text.ParseException;
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

    private final CountryRepository countryRepository;
    
    private final RegionService regionService;

    private final ModelMapper modelMapper;

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

    public Country create(CountryDto countryDto) throws ParseException {
        if (countryRepository.findByCode(countryDto.getCode()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country code already exist");
        }

        if (countryRepository.findByName(countryDto.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Country name already exist");
        }
        
        Country country = convertToEntity(countryDto);
        country.setRegion(regionService.getById(countryDto.getRegionId()));

//        Country country = new Country();
//        country.setCode(countryData.getCode());
//        country.setName(countryData.getName());
//        Country country = modelMapper.map(country, Country.class);
//        country.setRegion(regionService.getById(country.getRegionId()));
        return countryRepository.save(country);
    }

    public Country update(Long id, CountryDto countryDto) throws ParseException {
        Country getCountry = getById(id);
        if (!getCountry.getCode().equalsIgnoreCase(countryDto.getCode())) {
            if (countryRepository.findByCode(countryDto.getCode()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Update Failed: Country code already exist!");
            }
        }

        if (getCountry.getName().equalsIgnoreCase(countryDto.getCode())) {
            if (countryRepository.findByName(countryDto.getName()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Update Failed: Country name already exist!");
            }
        }
        
        Country country = convertToEntity(countryDto);
        country.setRegion(country.getRegion());

        country.setId(id);
        return countryRepository.save(country);
    }

    public Country delete(Long id) {
        Country country = getById(id);
        countryRepository.delete(country);
        return country;
    }

    public List<Country> getCountriesByRegionName(String regionName) {
        List<Country> countries = countryRepository.getCountriesByRegionName(regionName);
        return countries;
    }
    
    public List<Country> findCountriesByName(String name) {
        List<Country> countries = countryRepository.findCountriesByName(name);
        return countries;
    }

    private CountryDto convertToDto(Country country) {
        CountryDto countryDto = modelMapper.map(country, CountryDto.class);
        return countryDto;
    }

    private Country convertToEntity(CountryDto countryDto) throws ParseException {
        Country country = modelMapper.map(countryDto, Country.class);
        return country;
    }
}
