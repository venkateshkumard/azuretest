package com.oms.event.repository;

import com.oms.event.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository  extends MongoRepository<EventEntity,String> {
    public List<EventEntity> findByOrderId(String orderId);
    public void deleteByOrderId(String orderId);
}
