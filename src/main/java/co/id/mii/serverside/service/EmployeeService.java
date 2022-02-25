/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.Role;
import co.id.mii.serverside.model.User;
import co.id.mii.serverside.model.dto.EmployeeData;
import co.id.mii.serverside.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Employee create(EmployeeData employeeData) {
//        Employee employee = new Employee();
//        employee.setAddress(employeeData.getAddress());
//        employee.setEmail(employeeData.getEmail());
//        employee.setFullName(employeeData.getFullName());
//
//        User user = new User();
//        user.setIsAccountLocked(false);
//        user.setPassword(employeeData.getPassword());
//        user.setUsername(employeeData.getUsername());
//        user.setEmployee(employee);
        Employee employee = modelMapper.map(employeeData, Employee.class);
        User user = modelMapper.map(employeeData, User.class);
        user.setEmployee(employee);
        user.setIsAccountLocked(false);

        List<Role> role = new ArrayList<>();
        role.add(roleService.getById(employeeData.getRoleId()));
        user.setRoles(role);
        employee.setUser(user);

        return employeeRepository.save(employee);
    }

}
