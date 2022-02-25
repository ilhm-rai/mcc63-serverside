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
 * @author MSI-JO
 */
@Entity
@Table(name = "tb_role")
@Data
@AllArgsConstructor
@NoArgsConstructor  
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<User> users;

}
