package com.itechart.cargotrucking.core.message.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageInfoDto {
    private long id;
    private String message;
    private String author;
    private LocalDateTime date;
    private Boolean isEdited;
}
