package com.oms.billing.service;

import com.oms.billing.model.BillingEvent;
import com.oms.billing.model.OMSMailRequest;
import com.oms.billing.model.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class BillingServiceImpl implements BillingService {


    @Autowired
    RestTemplate restTemplate;

    MessageDelegate messageDelegate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public BillingServiceImpl(MessageDelegate messageDelegate ) {
        this.messageDelegate = messageDelegate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingServiceImpl.class);

    @Value("${url.eventServiceURL}")
    private String eventService_url;

    @Value("${url.mailServiceURL}")
    private String mailService_url;

    @Override
    public void updateStatus(OrderMessage orderMessage) {

        orderMessage.setInvoiceNumber(UUID.randomUUID().toString());
        orderMessage.setInvoiceDate(new Date());

        BillingEvent billingEvent = new BillingEvent();
        billingEvent.setCreatedDate(new Date());
        if (orderMessage.getPaymentStatus().equalsIgnoreCase("success")) {
            billingEvent.setStatus("success");
        } else {
            billingEvent.setStatus("failed");
        }
        billingEvent.setOrderId(orderMessage.getOrderId());
        billingEvent.setEventName("BillGeneration");

        //Call to 'event-service' (IPC)
        LOGGER.info("message=Call to event-service{}", orderMessage);
        restTemplate.postForObject(eventService_url+"/addEvent", billingEvent, BillingEvent.class);

        //Call to 'mail-service' (IPC)
        LOGGER.info("message=Call to mail-service{}", orderMessage);
        OMSMailRequest omsMailRequest = buildMail(orderMessage);
        restTemplate.postForObject(mailService_url, omsMailRequest, OMSMailRequest.class);

        //Message to 'order-service' (Rabbitmq)
        LOGGER.info("message=Call to order-service{}", orderMessage);
        messageDelegate.sendMessage(orderMessage);

    }

    private OMSMailRequest buildMail(OrderMessage orderMessage) {
        String mailId = "panneerselvam.nithiyanantham@merrillcorp.com";
        OMSMailRequest omsMailRequest = new OMSMailRequest();
        omsMailRequest.setProductId(orderMessage.getProductId());
        omsMailRequest.setBcc(mailId);
        omsMailRequest.setCc(mailId);
        omsMailRequest.setFrom(mailId);
        omsMailRequest.setTo(orderMessage.getCustomerEmailId());
        String mailContent = "Order Id: " + orderMessage.getOrderId() + " Customer Name: " + orderMessage.getCustomerName() + " InvoiceNumber: " + orderMessage.getInvoiceNumber()
                + " InvoiceDate: " + orderMessage.getInvoiceDate() + " PaymentMode: " + orderMessage.getPaymentMode()
                + " Payment Status: " + orderMessage.getPaymentStatus() + " TotalCost: " + orderMessage.getTotalCost();
        omsMailRequest.setMessage(mailContent);
        omsMailRequest.setReplyTo(mailId);
        omsMailRequest.setSubject("Order Id: " + orderMessage.getOrderId());
        return omsMailRequest;
    }
}
