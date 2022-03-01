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
public class MultipleRecipients {
    
    private List<String> toEmail;
    private String subject;
    private String body;
    private String attachment;
}
