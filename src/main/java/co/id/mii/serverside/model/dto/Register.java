/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.model.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author RAI
 */
@Data
public class Register {
    
    private String fullName;
    private String username;
    private String email;
    private String password;
    private List<Long> roleIds;
}
