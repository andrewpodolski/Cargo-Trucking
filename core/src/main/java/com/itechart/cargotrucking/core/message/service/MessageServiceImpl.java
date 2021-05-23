package com.itechart.cargotrucking.core.message.service;

import com.itechart.cargotrucking.core.message.Message;
import com.itechart.cargotrucking.core.message.QMessage;
import com.itechart.cargotrucking.core.message.dto.MessageAddDto;
import com.itechart.cargotrucking.core.message.dto.MessageFilterDto;
import com.itechart.cargotrucking.core.message.dto.MessageInfoDto;
import com.itechart.cargotrucking.core.message.dto.MessageUpdateDto;
import com.itechart.cargotrucking.core.message.repository.MessageRepository;
import com.itechart.cargotrucking.core.user.service.UserService;
import com.querydsl.core.BooleanBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private int defaultPageSize = 1000;

    private int minPageSize = 1;

    private int maxPageSize = 1000;

    private MessageRepository messageRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper, UserService userService) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public long add(MessageAddDto messageAddDto) {
        Message message = modelMapper.map(messageAddDto, Message.class);
        long clientId = userService.findByLogin(messageAddDto.getAuthor()).getClientId();
        message.setDate(LocalDateTime.now());
        message.setIsEdited(false);
        message.setClientId(clientId);
        messageRepository.save(message);

        return message.getId();
    }

    @Override
    public List<Message> find(String username) {
        long clientId = userService.findByLogin(username).getClientId();
        List<Message> page = messageRepository.findAllByClientId(clientId);
        return page;
    }

    @Override
    public void update(long id, MessageUpdateDto messageUpdateDto) {
        Message message = modelMapper.map(messageUpdateDto, Message.class);
        long clientId = userService.findByLogin(messageUpdateDto.getAuthor()).getClientId();
        message.setDate(LocalDateTime.now());
        message.setIsEdited(true);
        message.setClientId(clientId);
        messageRepository.save(message);
    }

    @Override
    public void delete(long id) {
        messageRepository.deleteById(id);
    }

    private BooleanBuilder whereBuilder(MessageFilterDto filterDto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(filterDto.getMessage())) {
            builder.and(QMessage.message1.message.startsWithIgnoreCase(filterDto.getMessage()));
        }

        return builder;
    }
}
