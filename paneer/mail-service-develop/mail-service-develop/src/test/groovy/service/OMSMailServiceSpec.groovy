package service

import com.oms.mail.controller.OMSMailController
import com.oms.mail.model.OMSMailRequest
import com.oms.mail.service.OMSMailService
import org.springframework.mail.javamail.JavaMailSender
import spock.lang.Specification

import javax.mail.internet.MimeMessage

class OMSMailServiceSpec extends Specification {
    OMSMailService omsMailService
    MimeMessage mimeMessage
    JavaMailSender javaMailSender

    def setup() {
        javaMailSender = Mock JavaMailSender
        mimeMessage = Mock MimeMessage
        omsMailService = new OMSMailService(javaMailSender)
    }

    def '/oms/mail - invoke the mail service'() {
        given:
        OMSMailController mailController = new OMSMailController(omsMailService)
        OMSMailRequest omsMailRequest = new OMSMailRequest(productId: 'prodId',from: 'mockId@mrrll.com',replyTo: 'mockReplyTo',
        to: 'mockTo@mrll.com', bcc: 'mockBcc@mrll.com',subject: 'Mock Subject', message: 'Will be delivered soon')

        when:
        mailController.sendMail(omsMailRequest)

        then:
        1 * javaMailSender.createMimeMessage() >> mimeMessage
        1 * javaMailSender.send(mimeMessage);
    }
}
