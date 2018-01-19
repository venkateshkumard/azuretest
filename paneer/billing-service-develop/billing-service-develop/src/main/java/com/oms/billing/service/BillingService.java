package com.oms.billing.service;

import com.oms.billing.model.OrderMessage;

public interface BillingService {
    void updateStatus(OrderMessage orderMessage);

}
