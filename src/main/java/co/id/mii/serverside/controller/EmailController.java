/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import co.id.mii.serverside.model.dto.MultipleRecipients;
import co.id.mii.serverside.model.dto.SendEmail;
import co.id.mii.serverside.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author RAI
 */
@RestController
@RequestMapping("/email")
public class EmailController {
    
    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<SendEmail> sendSimpleEmail(@RequestBody SendEmail emailData) {
        return new ResponseEntity(emailService.sendSimpleEmail(emailData), HttpStatus.OK);
    }

    @PostMapping("/sendAll")
    public ResponseEntity<SendEmail> sendSimpleEmailAll(@RequestBody MultipleRecipients multipleRecipients) {
        return new ResponseEntity(emailService.sendSimpleEmailToMultipleRecipients(multipleRecipients), HttpStatus.OK);
    }
    
    @PostMapping("/sendAttachment")
    public ResponseEntity<SendEmail> sendMimeEmailWithAttachment(@RequestBody SendEmail emailData) {
        return new ResponseEntity(emailService.sendEmailWithAttachment(emailData), HttpStatus.OK);
    }
    
    @PostMapping("/sendHtml")
    public ResponseEntity<SendEmail> sendEmailWithHtml(@RequestBody SendEmail emailData) {
        return new ResponseEntity(emailService.sendEmailWithHtml(emailData), HttpStatus.OK);
    }
}
