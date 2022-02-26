/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.model.dto;

import lombok.Data;

/**
 *
 * @author RAI
 */
@Data
public class CountryDto {
    private Long id;
    private String code;
    private String name;
    private Long regionId;
}
