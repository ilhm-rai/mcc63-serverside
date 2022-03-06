/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author RAI
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    
    private String username;
    private String email;
    private String address;
    private List<String> authorities;
}
