package com.itechart.cargotrucking.core.message.repository;

import com.itechart.cargotrucking.core.common.jpainterface.QuerydslRepository;
import com.itechart.cargotrucking.core.message.Message;
import com.itechart.cargotrucking.core.message.dto.MessageInfoDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends QuerydslRepository<Message, Long> {
    List<Message> findAllByClientId(Long clientId);
}
