package com.itechart.cargotrucking.webapp.chat;

import com.itechart.cargotrucking.core.message.Message;
import com.itechart.cargotrucking.core.message.dto.MessageAddDto;
import com.itechart.cargotrucking.core.message.dto.MessageFilterDto;
import com.itechart.cargotrucking.core.message.dto.MessageInfoDto;
import com.itechart.cargotrucking.core.message.dto.MessageUpdateDto;
import com.itechart.cargotrucking.core.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/driverChat")
public class DriverChatController {
    private final MessageService messageService;

    @Autowired
    public DriverChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<String> addMessage(@RequestBody MessageAddDto message) {
        long id = messageService.add(message);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public List<Message> getMessages(@RequestParam String username) {
        return messageService.find(username);
    }

    @PatchMapping
    public void updateMessage(@RequestBody MessageUpdateDto message) {
        messageService.update(message.getId(), message);
    }

    @DeleteMapping
    public void deleteMessage(@RequestBody long id) {
        messageService.delete(id);
    }
}
