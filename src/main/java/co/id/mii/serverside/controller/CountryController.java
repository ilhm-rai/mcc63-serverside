/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import co.id.mii.serverside.model.Country;
import co.id.mii.serverside.model.dto.CountryDto;
import co.id.mii.serverside.service.CountryService;
import co.id.mii.serverside.service.RegionService;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author RAI
 */
@RestController
@RequestMapping("/country")
public class CountryController {
    
    private final CountryService countryService;
    
    private final RegionService regionService;
    
    private final ModelMapper modelMapper;

    @Autowired
    public CountryController(CountryService countryService, RegionService regionService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAll() {
        List<Country> countries = countryService.getAll();
        return new ResponseEntity(countries.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable Long id) {
        return new ResponseEntity(countryService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody CountryDto countryDto) throws ParseException {
        if (countryDto.getId() != null) {
            countryDto.setId(null);
        }
        
        Country country = convertToEntity(countryDto);
        country.setRegion(regionService.getById(countryDto.getRegionId()));
        return new ResponseEntity(countryService.create(country), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody CountryDto countryDto) throws ParseException {
        if(!Objects.equals(id, countryDto.getId())){
            throw new IllegalArgumentException("IDs don't match");
        }
        
        Country country = convertToEntity(countryDto);
        country.setRegion(regionService.getById(countryDto.getRegionId()));
        return new ResponseEntity(countryService.update(id, country), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> delete(@PathVariable Long id) {
        return new ResponseEntity(countryService.getById(id), HttpStatus.OK);
    }
    
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Country> getCountriesByRegionName(@RequestParam(name = "region") String regionName) {
        List<Country> countries = countryService.getCountriesByRegionName(regionName);
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
