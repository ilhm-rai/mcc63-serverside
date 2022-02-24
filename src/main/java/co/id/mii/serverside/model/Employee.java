/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.model;

import javax.persistence.*;
import lombok.*;

/**
 *
 * @author MSI-JO
 */
@Entity
@Table(name = "tb_employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fullName;
    
    private String address;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;
    
}
