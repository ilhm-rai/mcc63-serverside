/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Employee;
import co.id.mii.serverside.model.User;
import co.id.mii.serverside.model.dto.EmployeeDto;
import co.id.mii.serverside.repository.UserRepository;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author RAI
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final EmployeeService employeeService;

    @Autowired
    public UserService(UserRepository userRepository, EmployeeService employeeService) {
        this.userRepository = userRepository;
        this.employeeService = employeeService;
    }

    public Employee create(EmployeeDto employeeDto) throws ParseException {
        return employeeService.create(employeeDto);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Not Found"));
    }

    public User update(Long id, User user) {
        User u = getById(id);
        user.setId(id);
        user.setEmployee(u.getEmployee());
        user.setRoles(u.getRoles());
        return userRepository.save(user);
    }

    public User delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
        return user;
    }

    public Boolean verify(String code) {
        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.getIsEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setIsEnabled(true);
            userRepository.save(user);
            return true;
        }
    }
}
