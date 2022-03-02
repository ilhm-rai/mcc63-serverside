/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.dto.MultipleRecipients;
import co.id.mii.serverside.model.dto.SendEmail;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author RAI
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SendEmail sendSimpleEmail(SendEmail sendEmail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setText(sendEmail.getToEmail());
            message.setSubject(sendEmail.getSubject());
            message.setText(sendEmail.getBody());

            mailSender.send(message);
            System.out.println("Mail sent...");
        } catch (MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return sendEmail;
    }

    public SendEmail sendSimpleEmailToMultipleRecipients(MultipleRecipients mr) {
        SendEmail sendEmail = new SendEmail();
        sendEmail.setSubject(mr.getSubject());
        sendEmail.setBody(mr.getBody());

        mr.getToEmail().stream()
                .forEach(email -> {
                    sendEmail.setToEmail(email);
                    sendSimpleEmail(sendEmail);
                });

        return sendEmail;
    }

    public SendEmail sendEmailWithAttachment(SendEmail sendEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

            mimeMessageHelper.setTo(sendEmail.getToEmail());
            mimeMessageHelper.setSubject(sendEmail.getSubject());
            mimeMessageHelper.setText(sendEmail.getBody());

            FileSystemResource fileSystemResource = new FileSystemResource(new File(sendEmail.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            mailSender.send(message);
            System.out.println("Mail sent...");
        } catch (MessagingException | MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        
        return sendEmail;
    }
    
    public SendEmail sendEmailWithHtml(SendEmail sendEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, "utf-8");

            mimeMessageHelper.setTo(sendEmail.getToEmail());
            mimeMessageHelper.setSubject(sendEmail.getSubject());
            mimeMessageHelper.setText(sendEmail.getBody(), true);

            mailSender.send(message);
            System.out.println("Mail sent...");
        } catch (MessagingException | MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        
        return sendEmail;
    }
    
    public SendEmail sendEmailHtmlWithAttachment(SendEmail sendEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(sendEmail.getToEmail());
            helper.setText(sendEmail.getBody(), true);
            helper.setSubject(sendEmail.getSubject());
            
            FileSystemResource res = new FileSystemResource(new File(sendEmail.getAttachment()));
            helper.addInline(res.getFilename(), res);
            
            mailSender.send(message);
            System.out.println("Mail succesfuly sent...");
        } catch (MessagingException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sendEmail;
    }
}
