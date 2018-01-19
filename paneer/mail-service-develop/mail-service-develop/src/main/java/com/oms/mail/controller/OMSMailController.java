package com.oms.mail.controller;

import com.oms.mail.model.OMSMailRequest;
import com.oms.mail.service.OMSMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oms/mail")
public class OMSMailController {

    private OMSMailService omsMailService;

    @Autowired
    public OMSMailController(OMSMailService omsMailService) {
        this.omsMailService = omsMailService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendMail(@RequestBody OMSMailRequest omsMailRequest){
        omsMailService.sendOMSEmail(omsMailRequest);
    }
}
