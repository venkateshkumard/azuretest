package com.oms.event.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "EventHistory")
public class EventEntity {
    @Id
    private String id;
    private String eventName;
    private String status;
    private String orderId;

    @DateTimeFormat
    private Date createdDate;

    @DateTimeFormat
    private Date lastModifiedDate;

    public String getId() {
        return id;
    }

    public EventEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getEventName() {
        return eventName;
    }

    public EventEntity setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public EventEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public EventEntity setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public EventEntity setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public EventEntity setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }


}
