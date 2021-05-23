package com.itechart.cargotrucking.core.message.service;

import com.itechart.cargotrucking.core.message.Message;
import com.itechart.cargotrucking.core.message.dto.MessageAddDto;
import com.itechart.cargotrucking.core.message.dto.MessageFilterDto;
import com.itechart.cargotrucking.core.message.dto.MessageInfoDto;
import com.itechart.cargotrucking.core.message.dto.MessageUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;


public interface MessageService {
    @Transactional
    long add(MessageAddDto message);

    List<Message> find(String message);

    @Transactional
    void update(long id, MessageUpdateDto message);

    @Transactional
    void delete(long id);
}
