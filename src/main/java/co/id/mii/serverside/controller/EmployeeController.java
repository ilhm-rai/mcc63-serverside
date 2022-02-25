/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.dto.EmployeeDto;
import co.id.mii.serverside.service.EmployeeService;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MSI-JO
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public List<EmployeeDto> getEmployees() {
        List<Employee> employee = employeeService.getEmployeesList();
        return employee.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto) throws ParseException {
        if (employeeDto.getId() != null) {
            employeeDto.setId(null);
        }
        Employee employee = convertToEntity(employeeDto);
        Employee employeeCreated = employeeService.create(employee);
        return convertToDto(employeeCreated);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return "Employee deleted.";
    }

    private EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        return employeeDto;
    }

    private Employee convertToEntity(EmployeeDto employeeDto) throws ParseException {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return employee;
    }
}
