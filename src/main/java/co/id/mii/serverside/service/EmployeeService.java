/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.User;
import co.id.mii.serverside.model.dto.EmployeeDto;
import co.id.mii.serverside.repository.EmployeeRepository;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;
    
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto create(EmployeeDto employeeDto) throws ParseException {
        Employee employee = convertToEntity(employeeDto);
        User user = employee.getUser();
        user.setEmployee(employee);
        user.setIsAccountLocked(Boolean.FALSE);
        employee.setUser(user);
        return convertToDto(employeeRepository.save(employee));
    }
    
    public List<EmployeeDto> getEmployeesList() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not Found")
        );
    }

    public void delete(Long id) {
        Employee emp = getById(id);
        employeeRepository.delete(emp);
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
