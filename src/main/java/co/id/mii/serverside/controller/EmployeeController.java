/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import co.id.mii.serverside.model.dto.EmployeeDto;
import co.id.mii.serverside.service.EmployeeService;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author RAI
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseBody
    public List<EmployeeDto> getEmployees() {
        return employeeService.getEmployeesList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) throws ParseException {
        if (employeeDto.getId() != null) {
            employeeDto.setId(null);
        }
        
        return employeeService.create(employeeDto);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return "Employee deleted.";
    }
}
