/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.User;
import co.id.mii.serverside.model.dto.LoginData;
import co.id.mii.serverside.model.dto.LoginResponse;
import co.id.mii.serverside.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAI
 */
@Service
public class LoginService {
    
    private final AppUserDetailService appUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public LoginService(AppUserDetailService appUserDetailService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.appUserDetailService = appUserDetailService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }
    
    public LoginResponse login(LoginData loginData) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginData.getUsernameOrEmail(), loginData.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        
        UserDetails userDetails = appUserDetailService.loadUserByUsername(loginData.getUsernameOrEmail());
        
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        
        User user = userRepository.findByUsernameOrEmployee_Email(loginData.getUsernameOrEmail(), loginData.getUsernameOrEmail()).get();
        
        return new LoginResponse(user.getUsername(), user.getEmployee().getEmail(), user.getEmployee().getAddress(), authorities);
    }
}
