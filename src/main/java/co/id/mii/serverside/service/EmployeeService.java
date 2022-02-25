/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.User;
import co.id.mii.serverside.repository.EmployeeRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author MSI-JO
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, RoleService roleService) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    public Employee create(Employee employee) {
        User user = employee.getUser();
        user.setEmployee(employee);
        user.setIsAccountLocked(Boolean.FALSE);
        employee.setUser(user);
        return employeeRepository.save(employee);
    }
    
    public List<Employee> getEmployeesList() {
        return employeeRepository.findAll();
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
}
