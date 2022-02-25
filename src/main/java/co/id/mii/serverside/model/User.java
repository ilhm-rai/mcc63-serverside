/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.model;

import java.util.List;
import javax.persistence.*;
import lombok.*;

/**
 *
 * @author RAI
 */
@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    @Id
    private Long id;
    
    private String username;
    
    private String password;
    
    private Boolean isAccountLocked;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "id" )
    private Employee employee;
    
    
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
