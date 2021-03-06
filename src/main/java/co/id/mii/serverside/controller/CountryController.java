/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

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

import co.id.mii.serverside.model.Country;
import co.id.mii.serverside.model.dto.CountryDto;
import co.id.mii.serverside.service.CountryService;

/**
 *
 * @author RAI
 */
@RestController
@RequestMapping("/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getAll() {
        return new ResponseEntity<List<Country>>(countryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable Long id) {
        return new ResponseEntity<Country>(countryService.getById(id), HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Country> create(@RequestBody CountryDto countryDto) throws ParseException {
        if (countryDto.getId() != null) {
            countryDto.setId(null);
        }

        return new ResponseEntity<Country>(countryService.create(countryDto), HttpStatus.CREATED);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody CountryDto countryDto)
            throws ParseException {
        if (!Objects.equals(id, countryDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }

        return new ResponseEntity<Country>(countryService.update(id, countryDto), HttpStatus.CREATED);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Country> delete(@PathVariable Long id) {
        return new ResponseEntity<Country>(countryService.delete(id), HttpStatus.OK);
    }

    @GetMapping("?region")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Country> getCountriesByRegionName(@RequestParam(name = "region") String regionName) {
        List<Country> countries = countryService.getCountriesByRegionName(regionName);
        return countries;
    }

    @GetMapping("?name")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Country> findByName(@RequestParam(name = "name") String name) {
        List<Country> countries = countryService.findCountriesByName(name);
        return countries;
    }
}
