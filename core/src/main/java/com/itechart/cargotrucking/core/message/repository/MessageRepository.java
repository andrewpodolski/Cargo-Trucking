package com.itechart.cargotrucking.core.message.repository;

import com.itechart.cargotrucking.core.common.jpainterface.QuerydslRepository;
import com.itechart.cargotrucking.core.message.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends QuerydslRepository<Message, Long> {
}
