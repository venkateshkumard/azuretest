import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class OMSMailAccSpec extends Specification{
    static final AcceptanceSpecConfiguration SETUP = new AcceptanceSpecConfiguration()
    static final String MAILINATOR_TOKEN = '82a889ee5fc542da89863c12fc1a1170'

    @Shared
    RESTClient mailClient

    @Shared
    RESTClient serviceClient

    def setupSpec() {
        mailClient = new RESTClient('https://api.mailinator.com')
        serviceClient = new RESTClient(SETUP.targetHost)
    }

    def 'POST /oms/email results in sending email to the configured mail Id'() {
        given:
        def fromEmail = 'noreply-oms@chnmrll.com'
        def inboxResponse
        def matchingMessage
        def conditions = new PollingConditions(initialDelay: 1, timeout: 120, factor: 1.2)
        String toEmail = 'customermail@mailinator.com'
        String replyToEmail = 'replyToOMS@mailinator.com'
        String expectedSubject = "Acceptance test for core email endpoint - OMS"
        String expectedMessage = 'Making sure an admin can send an email.'
        Map requestBody = [
                to     : toEmail,
                replyTo: replyToEmail,
                from   : fromEmail,
                subject: expectedSubject,
                message: expectedMessage
        ]

        when:
        def response = serviceClient.post(path: '/oms/mail',
                contentType: "application/json;charset=UTF-8",
                body: requestBody
        )

        then:
        response.status == 200
    }
}
