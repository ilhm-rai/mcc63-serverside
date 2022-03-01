/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.model.dto;

import lombok.Data;

/**
 *
 * @author MSI-JO
 */
@Data
public class EmployeeDto {
    
    private Long id;

    private String fullName;
    
    private String address;
    
    private String email;
    
    private UserDto user;
    
    private Long roleId;
}
