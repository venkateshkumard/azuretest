package controller

import com.oms.mail.controller.OMSMailController
import com.oms.mail.model.OMSMailRequest
import com.oms.mail.service.OMSMailService
import spock.lang.Specification

class OMSMailControllerSpec extends Specification{
    OMSMailService omsMailService
    OMSMailRequest omsMailRequest

    def setup() {
        omsMailRequest = Mock OMSMailRequest
        omsMailService = Mock OMSMailService
    }

    def '/oms/mail - invoke the mail controller'() {
        given:
        OMSMailController mailController = new OMSMailController(omsMailService)

        when:
        mailController.sendMail(omsMailRequest)

        then:
        1 * omsMailService.sendOMSEmail(omsMailRequest)
    }
}
