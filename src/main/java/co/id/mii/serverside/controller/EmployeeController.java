/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.dto.EmployeeDto;
import co.id.mii.serverside.model.dto.Register;
import co.id.mii.serverside.service.EmployeeService;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @author RAI
 */
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasAuthority('READ_DATA')")
    @GetMapping
    @ResponseBody
    public List<Employee> getEmployees() {
        return employeeService.getEmployeesList();
    }

    @PreAuthorize("hasAuthority('CREATE_DATA')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Employee create(@RequestBody EmployeeDto employeeDto) throws ParseException {
        if (employeeDto.getId() != null) {
            employeeDto.setId(null);
        }
        
        return employeeService.create(employeeDto);
    }
    
    @PreAuthorize("hasAuthority('CREATE_DATA')")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Employee register(@RequestBody Register register) throws MessagingException, UnsupportedEncodingException {
        return employeeService.register(register);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_DATA')")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Employee update(@PathVariable("id") Long id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }
    
    @PreAuthorize("hasAuthority('DETELE_DATA')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
        return "Employee deleted.";
    }
}
