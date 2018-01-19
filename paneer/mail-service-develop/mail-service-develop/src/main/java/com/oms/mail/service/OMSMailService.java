package com.oms.mail.service;

import com.oms.mail.exception.OMSMailServerError;
import com.oms.mail.model.OMSMailRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class OMSMailService {
    protected Logger logger = LoggerFactory.getLogger(OMSMailService.class);

    private JavaMailSender javaMailSender;

    @Value("${oms.email.admin.defaultEmailAddress}")
    private String defaultEmailAddress;

    @Autowired
    public OMSMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendOMSEmail(OMSMailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = buildMailMessage(emailRequest);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException ex) {
            String message = "message=errorCreatingAndSendingSmtpEmail";
            logger.error(message, ex);
            throw new OMSMailServerError(message, ex);
        }
    }

    private MimeMessage buildMailMessage(OMSMailRequest emailRequest) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper email = new MimeMessageHelper(mimeMessage);

        email.setFrom(getFromOrDefaultValue(emailRequest));
        email.setText(emailRequest.getProductId() + " - " + emailRequest.getMessage(), true);
        email.setTo(emailRequest.getTo());
        email.setSubject(emailRequest.getSubject());

        if (StringUtils.isNotBlank(emailRequest.getCc())) {
            email.setCc(emailRequest.getCc());
        }
        if (StringUtils.isNotBlank(emailRequest.getBcc())) {
            email.setBcc(emailRequest.getBcc());
        }
        if (StringUtils.isNotBlank(emailRequest.getReplyTo())) {
            email.setReplyTo(emailRequest.getReplyTo());
        }
        return mimeMessage;
    }

    private String getFromOrDefaultValue(OMSMailRequest emailRequest) {
        if (StringUtils.isNotBlank(emailRequest.getFrom())) {
            return emailRequest.getFrom();
        }
        return defaultEmailAddress;
    }
}
