package com.itechart.cargotrucking.core.message.service;

import com.itechart.cargotrucking.core.message.dto.MessageAddDto;
import com.itechart.cargotrucking.core.message.dto.MessageFilterDto;
import com.itechart.cargotrucking.core.message.dto.MessageInfoDto;
import com.itechart.cargotrucking.core.message.dto.MessageUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;


public interface MessageService {
    @Transactional
    long add(MessageAddDto message);

    Page<MessageInfoDto> find(MessageFilterDto message, Pageable pageable);

    @Transactional
    void update(long id, MessageUpdateDto message);

    @Transactional
    void delete(long id);
}
