/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.Role;
import co.id.mii.serverside.model.User;
import co.id.mii.serverside.model.dto.EmployeeDto;
import co.id.mii.serverside.model.dto.Register;
import co.id.mii.serverside.repository.EmployeeRepository;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final RoleService roleService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(
            EmployeeRepository employeeRepository, ModelMapper modelMapper,
            RoleService roleService, PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public Employee create(EmployeeDto employeeDto) {
        User user = modelMapper.map(employeeDto.getUser(), User.class);
        Employee employee = convertToEntity(employeeDto);
        user.setEmployee(employee);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsAccountLocked(Boolean.FALSE);

        List<Role> roles = employeeDto.getRoleIds()
                .stream()
                .map(id -> roleService.getById(id))
                .collect(Collectors.toList());

        user.setRoles(roles);
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    public Employee register(Register register) throws MessagingException, UnsupportedEncodingException {
        Employee employee = modelMapper.map(register, Employee.class);
        User user = modelMapper.map(register, User.class);
        user.setEmployee(employee);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsAccountLocked(Boolean.FALSE);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setIsEnabled(false);

        List<Role> roles = register.getRoleIds()
                .stream()
                .map(id -> roleService.getById(id))
                .collect(Collectors.toList());

        user.setRoles(roles);
        employee.setUser(user);

        emailService.sendEmail(employee, "Email Verification",
                "localhost:8088/user/verify?code=" + user.getVerificationCode(), "Verify");

        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesList() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not Found"));
    }

    public Employee update(Long id, Employee employee) {
        Employee e = getById(id);
        employee.setId(id);
        employee.setUser(e.getUser());
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee emp = getById(id);
        employeeRepository.delete(emp);
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return employee;
    }
}
